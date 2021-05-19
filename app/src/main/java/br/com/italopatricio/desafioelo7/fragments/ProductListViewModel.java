package br.com.italopatricio.desafioelo7.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.italopatricio.desafioelo7.core.ServiceConfig;
import br.com.italopatricio.desafioelo7.models.ProductModel;
import br.com.italopatricio.desafioelo7.models.ProductResultModel;
import br.com.italopatricio.desafioelo7.services.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListViewModel extends ViewModel {
    private final MutableLiveData<ProductResultModel> productResultModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private ProductService productService = ServiceConfig.getInstance().createImplementation(ProductService.class);;

    private final MutableLiveData<Integer> pastVisiblesItems = new MutableLiveData<>();
    private final MutableLiveData<Integer> visibleItemCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalItemCount = new MutableLiveData<>();

    public ProductListViewModel() {}

    public void setProductResultModel(ProductResultModel productResultModel) {
        this.productResultModelLiveData.setValue(productResultModel);
    }

    public MutableLiveData<ProductResultModel> getProductResultModelLiveData() {
        return productResultModelLiveData;
    }

    public void setLoading(boolean loading) {
        this.isLoading.setValue(loading);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setPastVisiblesItems(int value) {
        pastVisiblesItems.setValue(value);
    }

    public MutableLiveData<Integer> getPastVisiblesItems() {
        return pastVisiblesItems;
    }

    public void setVisibleItemCount(int value) {
        visibleItemCount.setValue(value);
    }

    public MutableLiveData<Integer> getVisibleItemCount() {
        return visibleItemCount;
    }

    public void setTotalItemCount(int value) {
        totalItemCount.setValue(value);
    }

    public MutableLiveData<Integer> getTotalItemCount() {
        return totalItemCount;
    }

    private void loadProducts(boolean more) {
        setLoading(true);
        productService.loadProducts().enqueue(new Callback<List<ProductModel>>(){

            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                try{
                    ProductResultModel productResultModel = ProductResultModel.fromList(response.body());
                    if(more) {
                        productResultModelLiveData.getValue().addProductsModel(productResultModel.getProductModelList());
                    } else {
                        productResultModelLiveData.setValue(productResultModel);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    setLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                t.printStackTrace();
                setLoading(false);
            }
        });
    }

    public void loadProducts() {
        loadProducts(false);
    }

    public void loadMoreProducts() {
        loadProducts(true);
    }

    public boolean canLoadMore() {
        if(getIsLoading().getValue()) return false;
        return (getVisibleItemCount().getValue() + getPastVisiblesItems().getValue()) >= getTotalItemCount().getValue();
    }
}
