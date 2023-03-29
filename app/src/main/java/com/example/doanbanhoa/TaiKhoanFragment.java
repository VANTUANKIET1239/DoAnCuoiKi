package com.example.doanbanhoa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoanFragment extends Fragment {


    ListView lstoptaikhoan;
    public TaiKhoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] options = {"Địa Chỉ", "Hồ Sơ Người Dùng","Theo Dõi Đơn Hàng","Lịch Sử Mua Hàng"};
        lstoptaikhoan = view.findViewById(R.id.optiontaikhoan);
        ArrayAdapter<String> op = new ArrayAdapter<>(getContext(),R.layout.layout_listview_itemtaikhoan,options);
        lstoptaikhoan.setAdapter(op);
        lstoptaikhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: break;
                    case 1: startActivity(new Intent(getContext(),HoSoNguoiDungActivity.class));
                }
            }
        });
    }

    public static TaiKhoanFragment newInstance() {
        TaiKhoanFragment fragment = new TaiKhoanFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tai_khoan, container, false);
    }
}