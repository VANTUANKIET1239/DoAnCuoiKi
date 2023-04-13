package com.example.doanbanhoa;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class LayHinhAnh {
    public static void loadImageFromUrl(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).resize(150,150).into(imageView);
    }
}
