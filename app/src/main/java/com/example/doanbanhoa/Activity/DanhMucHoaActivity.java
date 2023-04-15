package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DanhMucHoaActivity extends AppCompatActivity {

    RecyclerView listhoadanhmuc;

    EditText timkiemdanhmuc;

    ImageButton btntimkiem;
    Button loc;
    AutoCompleteTextView autocompleloaihoa;
    AutoCompleteTextView autocomplethutugia;

    SwipeRefreshLayout swipeRefreshLayout;

    TextView tendanhmuc;

    Query.Direction thutu;
    ArrayAdapter<String> itemthutugiaAD;

    ArrayAdapter<String> itemloaihoaAD;

    String[] itemthutugia = {"Giá Từ Cao Đến Thấp","Giá Từ Thấp Đến Cao"};

    String[] itemloaihoa = {"Cơ Bản","Nâng Cao"};
    FirebaseFirestore firebaseFirestore;


    private void refreshdata(String iddanhmuc){
        firebaseFirestore.collection("Hoa").whereEqualTo("id_DanhMuc",iddanhmuc).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Hoa> lshoa = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Hoa hoa = doc.toObject(Hoa.class);
                        lshoa.add(hoa);
                    }
                    HoaListAdapter adapter = new HoaListAdapter(getApplicationContext(),lshoa);
                    //                   listhoadanhmuc.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    listhoadanhmuc.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_hoa);

        autocompleloaihoa = findViewById(R.id.loaihoa);
        autocomplethutugia = findViewById(R.id.thutugia);
        listhoadanhmuc = findViewById(R.id.listhoadanhmuc);
        firebaseFirestore = FirebaseFirestore.getInstance();
        tendanhmuc = findViewById(R.id.tendanhmuc);
        loc = findViewById(R.id.loc);
        btntimkiem = findViewById(R.id.btntimkiem);
        timkiemdanhmuc = findViewById(R.id.timkiemhoadanhmuc);
        swipeRefreshLayout = findViewById(R.id.refreshdata);



        itemthutugiaAD = new ArrayAdapter<>(getBaseContext(),R.layout.listitem_thutugia,itemthutugia);
        itemloaihoaAD = new ArrayAdapter<>(getBaseContext(),R.layout.listitem_thutugia,itemloaihoa);

        autocomplethutugia.setAdapter(itemthutugiaAD);
        autocompleloaihoa.setAdapter(itemloaihoaAD);

        Intent intent = getIntent();
        String iddm =  intent.getStringExtra("iddanhmuc");
        String tendm = intent.getStringExtra("tendanhmuc");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshdata(iddm);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listhoadanhmuc.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        tendanhmuc.setText(tendm);
        firebaseFirestore.collection("Hoa").whereEqualTo("id_DanhMuc",iddm).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Hoa> lshoa = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Hoa hoa = doc.toObject(Hoa.class);
                        lshoa.add(hoa);
                    }
                    HoaListAdapter adapter = new HoaListAdapter(getApplicationContext(),lshoa);
 //                   listhoadanhmuc.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    listhoadanhmuc.setAdapter(adapter);
                }
            }
        });

        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tensp = timkiemdanhmuc.getText().toString();
                if(tensp.length() != 0){

                    firebaseFirestore.collection("Hoa").whereEqualTo("id_DanhMuc",iddm).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();

                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    if (hoa.getTenHoa().toLowerCase().contains(tensp.toLowerCase())){
                                        lshoa.add(hoa);

                                    }
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getApplicationContext(), lshoa);
 //                               listhoadanhmuc.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                listhoadanhmuc.setAdapter(adapter);
                            }
                        }

                    });
                }
                else {
                    firebaseFirestore.collection("Hoa").whereEqualTo("id_DanhMuc",iddm).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    lshoa.add(hoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getApplicationContext(),lshoa);
                                listhoadanhmuc.setAdapter(adapter);
                            }
                        }
                    });
                   Toast.makeText(getBaseContext(),"Không tìm thấy sản phẩm",Toast.LENGTH_SHORT).show();

                }

            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryloaihoa = autocompleloaihoa.getText().toString();
                String querythutugia = autocomplethutugia.getText().toString();

                if (querythutugia.equals("") || querythutugia.equals("Thứ Tự Giá") || queryloaihoa.equals("") || queryloaihoa.equals("Loại Hoa")) {
                    Toast.makeText(getBaseContext(), "Chưa chọn hoặc để trống điều kiện lọc", Toast.LENGTH_SHORT).show();
                } else {
                    if (querythutugia == "Giá Từ Cao Đến Thấp") {

                      thutu = Query.Direction.DESCENDING;
                        xapxep(thutu,queryloaihoa);
                    } else {
                        thutu = Query.Direction.ASCENDING;
                        xapxep(thutu,queryloaihoa);
                    }

                }
            }
            });


        }
        private void xapxep(Query.Direction k,String queryloaihoa){
            firebaseFirestore.collection("Hoa").orderBy("gia", k).whereEqualTo("loaiHoa", queryloaihoa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Hoa> lshoa = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Hoa hoa = doc.toObject(Hoa.class);
                            lshoa.add(hoa);
                        }
                        HoaListAdapter adapter = new HoaListAdapter(getApplicationContext(), lshoa);
                        listhoadanhmuc.setAdapter(adapter);
                    }
                }
            });
        }

    }

