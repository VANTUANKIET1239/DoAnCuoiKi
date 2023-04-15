package com.example.doanbanhoa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ThemDiaChiActivity extends AppCompatActivity {

    TextView txthoten,txtsdt,txtdiachi,label;

    Button btnthemdichi;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_dia_chi);
        txthoten = findViewById(R.id.HoTen);
        txtsdt = findViewById(R.id.SDT);
        txtdiachi = findViewById(R.id.DiaChi);
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnthemdichi = findViewById(R.id.themdiachi);
        auth = FirebaseAuth.getInstance();
        label = findViewById(R.id.labelthemdiachi);
        FirebaseUser currentuser = auth.getCurrentUser();
        btnthemdichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(txthoten.getText().toString().length() != 0 || txtsdt.getText().toString().length() != 0 || txtdiachi.getText().toString().length() != 0){
                  DatabaseReference databaseReference = firebaseDatabase.getReference("DiaChi").child(currentuser.getUid()).push();
                  DiaChi newdiachi = new DiaChi(databaseReference.getKey(),txthoten.getText().toString(),txtsdt.getText().toString(),txtdiachi.getText().toString());

                  databaseReference.setValue(newdiachi).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          Toast.makeText(getBaseContext(), "Thêm Địa Chỉ Thành Công",Toast.LENGTH_SHORT).show();
                          // startActivity(new Intent(getBaseContext(),DiaChiActivity.class));
                          finish();
                      }
                  });
              }
              else {
                  Toast.makeText(getApplicationContext(),"Thông tin không được để trống!",Toast.LENGTH_SHORT);
              }
            }
        });



    }

}