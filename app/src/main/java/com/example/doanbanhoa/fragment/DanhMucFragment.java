package com.example.doanbanhoa.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doanbanhoa.Adapter.DanhMucAdapter;
import com.example.doanbanhoa.Models.DanhMuc;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class DanhMucFragment extends Fragment {


    ListView listdanhmuc;

    TextView tendanhmuc;

    FirebaseFirestore firebaseFirestore;

    public DanhMucFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listdanhmuc = view.findViewById(R.id.listviewdanhmuc);
        tendanhmuc = view.findViewById(R.id.TenDanhMuc);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("DanhMuc").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    List<DanhMuc> lsdanhmuc = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        DanhMuc newdanhmuc = doc.toObject(DanhMuc.class);
                        lsdanhmuc.add(newdanhmuc);
                    }
                    DanhMucAdapter danhMucAdapter = new DanhMucAdapter(getContext(),lsdanhmuc);
                    listdanhmuc.setAdapter(danhMucAdapter);
                }

            }
        });




    }

    public static DanhMucFragment newInstance() {
        DanhMucFragment fragment = new DanhMucFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_danh_muc, container, false);
    }
}