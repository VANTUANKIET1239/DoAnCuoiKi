package com.example.doanbanhoa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.R;

import java.util.List;

public class ListDiaChiAdapter extends BaseAdapter {


    TextView Hoten,DiaChi,SDT;
    private List<DiaChi> lsdiachi;
    Context context;

    public ListDiaChiAdapter(Context context, List<DiaChi> lsdiachi){
        this.lsdiachi = lsdiachi;
        this.context = context;
    }
    @Override
    public int getCount() {
        return lsdiachi.size();
    }

    @Override
    public Object getItem(int position) {
        return lsdiachi.get(position);
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
            view = inflater.inflate(R.layout.layout_listview_listdiachi, null);
        }
        Hoten = view.findViewById(R.id.HoTen);
        SDT = view.findViewById(R.id.SDT);
        DiaChi = view.findViewById(R.id.DiaChi);

        Hoten.setText(lsdiachi.get(position).getHoTen());
        SDT.setText(lsdiachi.get(position).getSDT());
        DiaChi.setText(lsdiachi.get(position).getDiaChi());
        return view;
    }
}
