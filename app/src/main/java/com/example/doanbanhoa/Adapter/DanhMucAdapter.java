package com.example.doanbanhoa.Adapter;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.doanbanhoa.Activity.DanhMucHoaActivity;
import com.example.doanbanhoa.Activity.HoaActivity;
import com.example.doanbanhoa.Models.DanhMuc;
import com.example.doanbanhoa.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhMucAdapter extends BaseAdapter {

    List<DanhMuc> lsdanhmuc;
    Context context;

    ImageView hinhdanhmuc;

    TextView tendanhmuc;

    CardView carddanhmuc;
    public DanhMucAdapter(Context context, List<DanhMuc> lsdanhmuc){
        this.context = context;
        this.lsdanhmuc = lsdanhmuc;
    }
    @Override
    public int getCount() {
        return lsdanhmuc.size();
    }

    @Override
    public Object getItem(int position) {
        return lsdanhmuc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_listview_listdanhmuc, null);
        }
        hinhdanhmuc = view.findViewById(R.id.hinhanhdanhmuc);
        tendanhmuc = view.findViewById(R.id.tendanhmuc);
        carddanhmuc = view.findViewById(R.id.carddanhmuc);

        loadImageFromUrl(lsdanhmuc.get(position).getImageDanhMuc(), hinhdanhmuc);
       tendanhmuc.setText(lsdanhmuc.get(position).getTenDanhMuc());

        carddanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhMucHoaActivity.class);
                intent.putExtra("iddanhmuc",lsdanhmuc.get(position).getId());
                intent.putExtra("tendanhmuc", lsdanhmuc.get(position).getTenDanhMuc());
                startActivity(context,intent,null);
            }
        });
        return view;
    }
}
