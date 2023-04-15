package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.doanbanhoa.Adapter.BillListAdapter;
import com.example.doanbanhoa.Models.Bill;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LichSuDonHangActivity extends AppCompatActivity {
    RecyclerView reBill;
    BillListAdapter mBillList;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_don_hang);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        reBill = findViewById(R.id.reBill);
        reBill.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        database.getReference("Bill").child(auth.getCurrentUser().getUid().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Bill> b = new ArrayList<>();
                for(DataSnapshot doc : snapshot.getChildren()){
                      Bill bill = doc.getValue(Bill.class);
                    b.add(bill);
                }
                mBillList = new BillListAdapter(getApplicationContext(),b);
                reBill.setAdapter(mBillList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}