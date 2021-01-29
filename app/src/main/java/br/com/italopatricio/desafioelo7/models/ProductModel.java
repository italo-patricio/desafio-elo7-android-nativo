package br.com.italopatricio.desafioelo7.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String picture;
    private String title;
    private PriceModel price;

    @SerializedName("_link")
    private String link;

    public String getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public PriceModel getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }
}
