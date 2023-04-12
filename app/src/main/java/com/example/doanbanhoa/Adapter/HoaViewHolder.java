package com.example.doanbanhoa.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.R;

public class HoaViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_photo;
    TextView tv_caption;

    TextView tv_giahoa;
    public HoaViewHolder( View itemView) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.imv_photo);
        tv_caption = itemView.findViewById(R.id.tv_title);
        tv_giahoa = itemView.findViewById(R.id.gia);
    }

}
