package br.com.italopatricio.desafioelo7.services;

import java.util.List;

import br.com.italopatricio.desafioelo7.models.ProductModel;
import br.com.italopatricio.desafioelo7.models.ProductResultModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("elo7/api/1/products")
    Call<List<ProductModel>> loadProducts();
}
