package br.com.italopatricio.desafioelo7.helpers;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class PictureHelper {
    public static RequestCreator downloadImageUrl(String url){
        return Picasso.get().load(url).centerCrop();
    }
}
