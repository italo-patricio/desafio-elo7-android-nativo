package br.com.italopatricio.desafioelo7.models;

import java.io.Serializable;
import java.util.List;

public class ProductResultModel implements Serializable {
    private List<ProductModel> productModelList;

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public static ProductResultModel fromList(List<ProductModel> productModelList){
        ProductResultModel productResultModel = new ProductResultModel();
        productResultModel.productModelList = productModelList;
        return productResultModel;
    }

    public void addProductsModel(List<ProductModel> productModelList) {
        this.productModelList.addAll(productModelList);
    }

}
