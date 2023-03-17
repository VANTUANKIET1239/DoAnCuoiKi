package com.example.doanbanhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText matkhau;
    private Button dangnhap;
    private FirebaseAuth auth;

    private TextView regis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.Email_dn);
        matkhau = findViewById(R.id.matkhau_dn);
        dangnhap = findViewById(R.id.dangnhap);
        regis = findViewById(R.id.regis);
        auth = FirebaseAuth.getInstance();

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_mail = Email.getText().toString();
                String txt_matkhau = matkhau.getText().toString();
                LoginUser(txt_mail,txt_matkhau);
            }
        });
    }
    private void LoginUser(String email, String matkhau){
        auth.signInWithEmailAndPassword(email,matkhau).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}