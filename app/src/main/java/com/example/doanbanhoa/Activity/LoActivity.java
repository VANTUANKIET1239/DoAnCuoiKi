package com.example.doanbanhoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanbanhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lo);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (user != null) {


                    Intent intent = new Intent(LoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Intent intent = new Intent(LoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);

    }
}