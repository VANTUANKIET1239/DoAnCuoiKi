package com.example.doanbanhoa;



import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HoaAdapter extends BaseAdapter {
    private ArrayList<Hoa> Hoa;
    private Context context;
    public HoaAdapter(ArrayList<Hoa> photo, Context context){
        this.Hoa = photo;
        this.context = context;
    }
    @Override
    public int getCount(){
        return Hoa.size();
    }

    @Override
    public Object getItem(int position) {
        return Hoa.get(position);
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

        dataitem.tv_caption.setText(Hoa.get(position).getTenHoa());
        loadImageFromUrl(Hoa.get(position).getImage_Hoa(),dataitem.iv_photo);
        dataitem.tv_giahoa.setText(String.valueOf(Hoa.get(position).getGia()) + "Đ");
        dataitem.tv_danhgia.setText(Hoa.get(position).getHangDanhGia() + "(" + Hoa.get(position).getSoLuongDanhGia() + ")");
      return convertView;
    }
    private static class MyView{
        ImageView iv_photo;
        TextView tv_caption;

        TextView tv_giahoa;
        TextView tv_danhgia;
    }
}

