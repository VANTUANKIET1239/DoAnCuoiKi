package com.example.doanbanhoa.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.R;

public class GioHangViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_photo;

    TextView word, title, soLuong, donGia;


    TextView tonggia;
    public GioHangViewHolder( View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.pic);
        donGia = itemView.findViewById(R.id.price);
        word = itemView.findViewById(R.id.word);
        title = itemView.findViewById(R.id.title);
        soLuong = itemView.findViewById(R.id.soluong);
        tonggia= itemView.findViewById(R.id.totalprice);
}

}

