package com.example.doanbanhoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import com.example.doanbanhoa.Models.Comment;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CommentActivity extends AppCompatActivity {
    RatingBar ratting_user_comment;
    EditText user_comment;
    Button btn_danhgia;
    ImageView img_hoa;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Float ratting_bar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);

        ratting_user_comment = findViewById(R.id.ratting_comment);
        user_comment = findViewById(R.id.edittext_comment);
        btn_danhgia = findViewById(R.id.btn_danhgia);
        img_hoa = findViewById(R.id.img_comment_anh);

        firebaseDatabase =FirebaseDatabase.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        String id_hoa = intent.getStringExtra("id_hoa");
        String id_user = intent.getStringExtra("id_user");
        String url_hoa = intent.getStringExtra("img_hoa");
        Picasso.get().load(url_hoa).into(img_hoa);

        ratting_user_comment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ratting_user_comment.setRating(rating);
                ratting_bar = rating;
            }
        });
        btn_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_comment.length() != 0 || ratting_bar != 0){
                    btn_danhgia.setVisibility(View.INVISIBLE);
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").child(id_hoa).push();
                    String comment = user_comment.getText().toString();
                    String id = id_user;
                    Comment cmt = new Comment(id, comment,ratting_bar);

                    databaseReference.setValue(cmt).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ShowMessage("Đánh giá thành công");
                            user_comment.setText("");
                            btn_danhgia.setVisibility(View.VISIBLE);
                        }
                    });
                    ratting_user_comment.setRating(0);
                    user_comment.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bạn chưa đánh giá",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private  void ShowMessage(String mess){
        Toast.makeText(this,mess, Toast.LENGTH_SHORT).show();
    }}
