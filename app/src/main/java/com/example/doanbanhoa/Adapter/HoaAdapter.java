package com.example.doanbanhoa.Adapter;



import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanbanhoa.Activity.HoaActivity;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HoaAdapter extends BaseAdapter {
    private ArrayList<com.example.doanbanhoa.Models.Hoa> lsHoa;
    private Context context;
    ImageView img_anh;
    TextView txt_ten, txt_gia, txt_mota;
    public HoaAdapter(ArrayList<Hoa> photo, Context context){
        this.lsHoa = photo;
        this.context = context;
    }
    @Override
    public int getCount(){
        return lsHoa.size();
    }

    @Override
    public Object getItem(int position) {
        return lsHoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return Hoa.get(position).getID_Hoa();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.hoa_disp_tpl,null);
            dataitem.iv_photo = convertView.findViewById(R.id.imv_photo);
            dataitem.tv_caption = convertView.findViewById(R.id.tv_title);
            dataitem.tv_danhgia = convertView.findViewById(R.id.danhgia);
            dataitem.tv_giahoa = convertView.findViewById(R.id.gia);
            convertView.setTag(dataitem);

        }
        else {
            dataitem = (MyView) convertView.getTag();
        }

        dataitem.tv_caption.setText(lsHoa.get(position).getTenHoa());
        loadImageFromUrl(lsHoa.get(position).getImage_Hoa(),dataitem.iv_photo);
        dataitem.tv_giahoa.setText(String.valueOf(lsHoa.get(position).getGia()) + "Đ");
        dataitem.tv_danhgia.setText(lsHoa.get(position).getHangDanhGia() + "(" + lsHoa.get(position).getSoLuongDanhGia() + ")");

        Intent intent = new Intent(context, HoaActivity.class);
        intent.putExtra("tenhoa", lsHoa.get(position).getTenHoa());
      return convertView;
    }

    private static class MyView{
        ImageView iv_photo;
        TextView tv_caption;

        TextView tv_giahoa;
        TextView tv_danhgia;
    }
}

