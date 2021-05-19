package br.com.italopatricio.desafioelo7.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.italopatricio.desafioelo7.R;
import br.com.italopatricio.desafioelo7.adapters.ProductRecyclerAdapter;

public class ProductListFragment extends Fragment {

    private TextView resultTextView;
    private TextView loadingTextView;

    private RecyclerView recyclerView;

    private ProductRecyclerAdapter productRecyclerAdapter;

    private ProductListViewModel productListViewModel;

    public ProductListFragment() {
        super(R.layout.fragment_product_list);
    }

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRecyclerAdapter = new ProductRecyclerAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        resultTextView = view.findViewById(R.id.txt_products_found);
        recyclerView = view.findViewById(R.id.products_recycler_view);
        loadingTextView = view.findViewById(R.id.txt_loading);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListViewModel = new ViewModelProvider(requireActivity()).get(ProductListViewModel.class);
        productListViewModel.getProductResultModelLiveData().observe(getViewLifecycleOwner(), productResultModel -> {
            productRecyclerAdapter.setProductRecyclerAdapter(productResultModel);
            recyclerView.setAdapter(productRecyclerAdapter);
        });
        productListViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            setLoading(isLoading);
        });

        GridLayoutManager mLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    productListViewModel.setPastVisiblesItems(mLayoutManager.getChildCount());
                    productListViewModel.setTotalItemCount(mLayoutManager.getItemCount());
                    productListViewModel.setVisibleItemCount(mLayoutManager.findFirstVisibleItemPosition());

                    if (productListViewModel.canLoadMore()) {
                        productListViewModel.loadMoreProducts();
                    }
                }
            }
        });

        productListViewModel.loadProducts();
    }

    public void setLoading(boolean loading) {
        loadingTextView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

}