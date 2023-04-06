package com.example.doanbanhoa.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Activity.ChinhSuaDiaChiActivity;
import com.example.doanbanhoa.Activity.LoginActivity;
import com.example.doanbanhoa.Activity.ThemDiaChiActivity;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListDiaChiAdapter extends BaseAdapter {


    TextView Hoten,DiaChi,SDT;

    Button ChinhSua, XoaBo;
    private List<DiaChi> lsdiachi;
    Context context;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

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
        ChinhSua = view.findViewById(R.id.chinhsua);
        XoaBo = view.findViewById(R.id.xoabo);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Hoten.setText(lsdiachi.get(position).getHoTen());
        SDT.setText(lsdiachi.get(position).getSDT());
        DiaChi.setText(lsdiachi.get(position).getDiaChi());
        auth = FirebaseAuth.getInstance();

        ChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChinhSuaDiaChiActivity.class);
                intent.putExtra("Id",lsdiachi.get(position).getId());
                intent.putExtra("Hoten",lsdiachi.get(position).getHoTen());
                intent.putExtra("SDT",lsdiachi.get(position).getSDT());
                intent.putExtra("DiaChi",lsdiachi.get(position).getDiaChi());
                startActivity(context,intent,null);
            }
        });

        XoaBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.getReference("DiaChi").child(auth.getCurrentUser().getUid()).child(lsdiachi.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Xóa Địa Chỉ Thành Công!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }
}
