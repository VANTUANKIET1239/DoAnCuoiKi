package com.example.doanbanhoa.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.R;

public class HoaViewHolder extends RecyclerView.ViewHolder  {
    ImageView iv_photo;
    TextView tv_caption;

    TextView tv_giahoa;
    TextView tv_danhgia;
    private Context mContext;
   // private final HoaListAdapter mAdapter;
    public HoaViewHolder( View itemView, HoaListAdapter adapter,Context context) {
        super(itemView);
        iv_photo = itemView.findViewById(R.id.imv_photo);
        tv_caption = itemView.findViewById(R.id.tv_title);
      //  tv_danhgia = itemView.findViewById(R.id.danhgia);
        tv_giahoa = itemView.findViewById(R.id.gia);
       // this.mAdapter = adapter;
//        Ho.setOnClickListener(this);
       this.mContext = context;
    }


}
