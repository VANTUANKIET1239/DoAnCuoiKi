package com.example.doanbanhoa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.example.doanbanhoa.fragment.DanhMucFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText matkhau;
    private Button dangnhap;
    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;

    private StorageReference firebaseStorage;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference("uploadsCaNhan");
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
    private String getFileEx(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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