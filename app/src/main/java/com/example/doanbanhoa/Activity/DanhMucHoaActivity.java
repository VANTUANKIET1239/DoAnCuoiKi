package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DanhMucHoaActivity extends AppCompatActivity {

    RecyclerView listhoadanhmuc;

    Button loc;
    AutoCompleteTextView autocompleloaihoa;
    AutoCompleteTextView autocomplethutugia;

    TextView tendanhmuc;
    ArrayAdapter<String> itemthutugiaAD;

    ArrayAdapter<String> itemloaihoaAD;

    String[] itemthutugia = {"Giá Từ Cao Đến Thấp","Giá Từ Thấp Đến Cao"};

    String[] itemloaihoa = {"Cơ Bản","Nâng Cao"};
    FirebaseFirestore firebaseFirestore;



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

        itemthutugiaAD = new ArrayAdapter<>(getBaseContext(),R.layout.listitem_thutugia,itemthutugia);
        itemloaihoaAD = new ArrayAdapter<>(getBaseContext(),R.layout.listitem_thutugia,itemloaihoa);

        autocomplethutugia.setAdapter(itemthutugiaAD);
        autocompleloaihoa.setAdapter(itemloaihoaAD);

        Intent intent = getIntent();
        String iddm =  intent.getStringExtra("iddanhmuc");
        String tendm = intent.getStringExtra("tendanhmuc");

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
                    HoaListAdapter adapter = new HoaListAdapter(getBaseContext(),lshoa);
                    listhoadanhmuc.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                    listhoadanhmuc.setAdapter(adapter);
                }
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryloaihoa = autocompleloaihoa.getText().toString();
                String querythutugia = autocomplethutugia.getText().toString();
                if(querythutugia == "Giá Từ Cao Đến Thấp"){
                    firebaseFirestore.collection("Hoa").orderBy("gia", Query.Direction.DESCENDING).whereEqualTo("loaiHoa",queryloaihoa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    lshoa.add(hoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getBaseContext(),lshoa);
                                listhoadanhmuc.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                                listhoadanhmuc.setAdapter(adapter);
                            }
                        }
                    });
                }
                else {
                    firebaseFirestore.collection("Hoa").orderBy("gia", Query.Direction.ASCENDING).whereEqualTo("loaiHoa",queryloaihoa).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    lshoa.add(hoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getBaseContext(),lshoa);
                                listhoadanhmuc.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                                listhoadanhmuc.setAdapter(adapter);
                            }
                        }
                    });
                }

            }
        });


    }

}