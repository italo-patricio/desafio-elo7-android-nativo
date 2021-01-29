package br.com.italopatricio.desafioelo7.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.italopatricio.desafioelo7.R;
import br.com.italopatricio.desafioelo7.adapters.ProductRecyclerAdapter;
import br.com.italopatricio.desafioelo7.core.ServiceConfig;
import br.com.italopatricio.desafioelo7.models.ProductModel;
import br.com.italopatricio.desafioelo7.models.ProductResultModel;
import br.com.italopatricio.desafioelo7.services.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    private TextView resultTextView;
    private TextView loadingTextView;

    private RecyclerView recyclerView;
    private ProductService productService;

    private ProductRecyclerAdapter productRecyclerAdapter;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public ProductListFragment(ServiceConfig serviceConfig) {
        super(R.layout.fragment_product_list);
        productService = serviceConfig.createImplementation(ProductService.class);
    }

    public static ProductListFragment newInstance(ServiceConfig serviceConfig) {
        return new ProductListFragment(serviceConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRecyclerAdapter = new ProductRecyclerAdapter(getActivity());
    }

    private boolean loading = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        resultTextView = view.findViewById(R.id.txt_products_found);
        recyclerView = view.findViewById(R.id.products_recycler_view);
        loadingTextView = view.findViewById(R.id.txt_loading);

        GridLayoutManager mLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loadMoreProducts();
                        }
                    }
                }
            }
        });

        this.loadProducts();

        return view;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        loadingTextView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void loadProducts(boolean more) {
        setLoading(true);
        productService.loadProducts().enqueue(new Callback<List<ProductModel>>(){

            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                try{
                    ProductResultModel productResultModel = ProductResultModel.fromList(response.body());
                    if(more){
                        productRecyclerAdapter.addProductResultModel(productResultModel);
                    } else {
                        productRecyclerAdapter.setProductRecyclerAdapter(productResultModel);
                        recyclerView.setAdapter(productRecyclerAdapter);
                    }
                    resultTextView.setText(productRecyclerAdapter.getResults());
                } catch (Exception e){
                    e.printStackTrace();
                }
                setLoading(false);
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), t.toString(),Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        });
    }

    private void loadProducts() {
        loadProducts(false);
    }

    private void loadMoreProducts() {
        loadProducts(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadProducts();
        }
        return true;
    }
}