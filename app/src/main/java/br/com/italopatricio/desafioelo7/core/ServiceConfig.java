package br.com.italopatricio.desafioelo7.core;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceConfig {

    private static ServiceConfig instance;

    private Retrofit retrofitInstance;

    ServiceConfig() {
        retrofitInstance = configBuilder();
    }

    public static ServiceConfig getInstance() {
        if(instance != null) return instance;
        return newInstance();
    }

    public static ServiceConfig newInstance() {
        return new ServiceConfig();
    }

    public Retrofit getRetrofitInstance() {
        return retrofitInstance;
    }

    public <T> T createImplementation(final Class<T> service) {
        return retrofitInstance.create(service);
    }

    private Retrofit configBuilder() {
        return new Retrofit.Builder()
                .baseUrl("https://5dc05c0f95f4b90014ddc651.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }
}
