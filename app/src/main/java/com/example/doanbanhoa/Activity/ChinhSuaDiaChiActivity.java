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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ChinhSuaDiaChiActivity extends AppCompatActivity {

    TextView txthoten,txtsdt,txtdiachi,label;

    Button btnthemdichi;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_dia_chi);
        txthoten = findViewById(R.id.HoTen);
        txtsdt = findViewById(R.id.SDT);
        txtdiachi = findViewById(R.id.DiaChi);
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnthemdichi = findViewById(R.id.chinhsuadiachi);
        auth = FirebaseAuth.getInstance();
        label = findViewById(R.id.labelthemdiachi);
        FirebaseUser currentuser = auth.getCurrentUser();
        intent = getIntent();
        btnthemdichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = intent.getStringExtra("Id");
                DiaChi newdiachi = new DiaChi(id,txthoten.getText().toString(),txtsdt.getText().toString(),txtdiachi.getText().toString());
                Map<String,Object> hehe = newdiachi.toMap();
                firebaseDatabase.getReference("DiaChi").child(currentuser.getUid()).child(id).updateChildren(hehe).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getBaseContext(),"Chỉnh Sửa Địa Chỉ Thành Công",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        ChinhSuaDuLieu();
    }
    private void ChinhSuaDuLieu(){


        String hoten = intent.getStringExtra("Hoten");
        String SDT = intent.getStringExtra("SDT");
        String DiaChi = intent.getStringExtra("DiaChi");

        txthoten.setText(hoten);
        txtsdt.setText(SDT);
        txtdiachi.setText(DiaChi);
    }
}