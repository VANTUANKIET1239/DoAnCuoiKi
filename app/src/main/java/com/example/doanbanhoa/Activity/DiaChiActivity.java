package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.doanbanhoa.Adapter.ListDiaChiAdapter;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiaChiActivity extends AppCompatActivity {

    Button themdiachi;

    FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    ListView listdiachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);
        themdiachi = findViewById(R.id.themdiachi);
        listdiachi = findViewById(R.id.listdiachi);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        themdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(),ThemDiaChiActivity.class));
            }
        });
        firebaseDatabase.getReference("DiaChi").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DiaChi> lsdiachi = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DiaChi diaChi = dataSnapshot.getValue(DiaChi.class);
                    lsdiachi.add(diaChi);
                }
                ListDiaChiAdapter diaChiAdapter = new ListDiaChiAdapter(getBaseContext(),lsdiachi);
                listdiachi.setAdapter(diaChiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}