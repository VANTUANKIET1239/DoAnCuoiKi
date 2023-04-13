package com.example.doanbanhoa.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhauActivity extends AppCompatActivity {

    EditText txtemail, txtmkcu, txtmkmoi, txtxnmkm;

    Button btndoi;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        txtemail = findViewById(R.id.email);

        txtmkmoi = findViewById(R.id.matkhaumoi);
        txtxnmkm = findViewById(R.id.matkhaumoixacnhan);
        auth = FirebaseAuth.getInstance();
        btndoi = findViewById(R.id.btndoimatkhau);
        txtmkcu = findViewById(R.id.matkhaucu);

        txtemail.setEnabled(false);
        txtemail.setText(auth.getCurrentUser().getEmail());
        btndoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtxnmkm.getText().toString().equals(txtmkmoi.getText().toString())) {
                     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), txtmkcu.getText().toString().trim());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(txtmkmoi.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                    auth.signOut();
                                                    finish();
                                                    MainActivity.main.finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Đổi Mật Khẩu Thất Bại", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });
                            }
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu mới xác nhận không khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}