package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    private EditText Email;
    private EditText matkhau;
    private Button register;
    private  EditText xacnhanmatkhau;

    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;

    private StorageReference storageReference;

    TextView quaylaiDN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.Email);
        matkhau = findViewById(R.id.matkhau);
        register = findViewById(R.id.dangky);
        quaylaiDN = findViewById(R.id.quaylaitaikhoan);
        xacnhanmatkhau = findViewById(R.id.xacnhanmatkhau);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploadsCaNhan");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = Email.getText().toString();
                String txt_matkhau = matkhau.getText().toString();
                String txt_xacnhanmk = xacnhanmatkhau.getText().toString();
                if(TextUtils.isEmpty(txt_matkhau) || TextUtils.isEmpty(txt_email)){
                    Toast.makeText(RegisterActivity.this, "không được bỏ trống",Toast.LENGTH_SHORT).show();
                }
                else if(txt_matkhau.length() < 6){
                    Toast.makeText(RegisterActivity.this, "mật khấu phải hơn 6 kí tự",Toast.LENGTH_SHORT).show();
                }
                else if(!txt_matkhau.trim().equals(txt_xacnhanmk.trim())) {
                    Toast.makeText(RegisterActivity.this, txt_matkhau + " " +txt_xacnhanmk, Toast.LENGTH_SHORT).show();
                }
                else dangky(txt_email,txt_matkhau);
            }
        });
        quaylaiDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LoginActivity.class));

            }
        });
    }
    private String getFileEx(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void dangky(String email, String matkhau){
        auth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    StorageReference storageReference1 = FirebaseStorage.getInstance().getReference("defaultiamge/ava.jpg");
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            User newus = new User();
                            newus.setEmail(email);
                            newus.setImagea(uri.toString());
                            newus.setId(auth.getCurrentUser().getUid());
                            firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).setValue(newus);
                            Toast.makeText(RegisterActivity.this, "đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            // finish();
                        }
                    });

                }
                else {
                    Toast.makeText(RegisterActivity.this, "đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}