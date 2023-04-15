package com.example.doanbanhoa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
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

    CheckBox checknhomk;

    public static final String SHARE_PRES = "com.example.doanbanhoa";
    public static final String MK = "matkhau";

    public static final String TK = "taikhoan";
    public static final String check = "check";

    private String MKsh;

    private boolean checkbox;
    private String TKsh;
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
        checknhomk = findViewById(R.id.checknho);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference("uploadsCaNhan");

        loadData();
        setview();

        checknhomk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checknhomk.isChecked()){
                    SaveData();
                }
                else {
                    deletedata();
                }
            }
        });
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
    public void SaveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(MK,matkhau.getText().toString());
        editor.putString(TK,Email.getText().toString());
        editor.putBoolean(check,true);
        editor.apply();
    }
    public void deletedata(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(MK,"");
        editor.putString(TK,"");
        editor.putBoolean(check,false);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRES,MODE_PRIVATE);
        MKsh = sharedPreferences.getString(MK,"");
        TKsh = sharedPreferences.getString(TK,"");
        checkbox = sharedPreferences.getBoolean(check,false);
    }
    public void setview(){
        matkhau.setText(MKsh);
        Email.setText(TKsh);
        checknhomk.setChecked(checkbox);
    }
}