package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Adapter.ListDiaChiAdapter;
import com.example.doanbanhoa.Callback;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.Models.Hoa;

import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HoaActivity extends AppCompatActivity {

    ImageView img_anh;
    TextView txt_ten, txt_gia, txt_mota;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    DanhMucHoaActivity danhMucHoaActivity;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        firebaseDatabase = FirebaseDatabase.getInstance();

        img_anh = findViewById(R.id.image_anh);
        txt_ten = findViewById(R.id.txt_tensanpham);
        txt_gia = findViewById(R.id.txt_giá);
        txt_mota = findViewById(R.id.txt_motasp);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String tenhoa =  intent.getStringExtra("tenhoa");


//        getFlowerData(id, new Callback<Hoa>() {
//            @Override
//            public void onSuccess(Hoa data) {
//                System.out.println(data+"ddddddddddddđ");
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//
//    }
////    public void getFlowerData(final String id, final Callback<Hoa> callback) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference flowersRef = db.collection("Hoa");
//        Query query = flowersRef.whereEqualTo("id", id);
//        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
//                    Hoa flower = documentSnapshot.toObject(Hoa.class);
//                    callback.onSuccess(flower);
//                } else {
//                    callback.onError("No flower found with id " + id);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                callback.onError(e.getMessage());
//            }
//        });
        firebaseFirestore.collection("Hoa").whereEqualTo("tenHoa",tenhoa)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Hoa hoa = doc.toObject(Hoa.class);
                        txt_ten.setText(hoa.getTenHoa());
                        txt_mota.setText(hoa.getMoTa());
                        Integer gia = hoa.getGia();
                        txt_gia.setText(gia.toString());
                    }
                }
            }
        });
    }
}