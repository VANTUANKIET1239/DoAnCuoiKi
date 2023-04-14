package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Adapter.CommentAdapter;
import com.example.doanbanhoa.Models.Comment;
import com.example.doanbanhoa.Models.Hoa;

import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.example.doanbanhoa.fragment.TrangChuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HoaActivity extends AppCompatActivity {

    ImageView img_anh, minus, plus, btn_trove, btn_addcomment;
    TextView txt_ten, txt_gia, txt_mota, txt_comment, txt_soluong,txt_ratting;
    Button btn_order;
    BottomNavigationView themgiohang;
    RecyclerView RVcomment;
    RatingBar ratingBar_comment;
    List<Comment> lscmt;
    List<Float> allrating = new ArrayList<Float>();
    CommentAdapter commentAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    User users;
    Integer soluong;
    String url_hoa;
     Float averageRating = 0f;
    Hoa hoa;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chitietsp);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        btn_order=findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), DonHangActivity.class);
                Bundle bundle = new Bundle();
                Item item = new Item(hoa, 1);
                bundle.putSerializable("item1", item);
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        btn_trove = findViewById(R.id.btn_trove);
        img_anh = findViewById(R.id.image_anh);
        txt_ten = findViewById(R.id.txt_tensanpham);
        txt_gia = findViewById(R.id.txt_giá);
        txt_mota = findViewById(R.id.txt_motasp);
        txt_comment = findViewById(R.id.edittext_comment);
        RVcomment = findViewById(R.id.rv_comment);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        txt_soluong = findViewById(R.id.txt_soluong);
        themgiohang = findViewById(R.id.btn_themgiohang);
        ratingBar_comment = findViewById(R.id.ratting);
        txt_ratting = findViewById(R.id.txt_tongratting);
        btn_addcomment = findViewById(R.id.btn_add_comment);

        Intent intent = getIntent();
        String id_hoa =  intent.getStringExtra("id");
        RVcomment.addItemDecoration(new MyItemDecoration(10));
        btn_trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = txt_soluong.getText().toString();
                int sl = Integer.parseInt(i);
                if(sl >0){
                    soluong = sl -1;
                    txt_soluong.setText(soluong.toString());
                }else {
                    Toast.makeText(HoaActivity.this, "Không thể thêm sản phẩm", Toast.LENGTH_SHORT).show();
                }

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = txt_soluong.getText().toString();
                int sl = Integer.parseInt(i);
                soluong = sl +1;
                txt_soluong.setText(soluong.toString());
            }
        });
        themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        firebaseFirestore.collection("Hoa").whereEqualTo("id",id_hoa)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        hoa = doc.toObject(Hoa.class);
                        txt_ten.setText(hoa.getTenHoa());
                        txt_mota.setText(hoa.getMoTa());
                        Integer gia = hoa.getGia();
                        txt_gia.setText(gia.toString() + "Đ");
                        url_hoa = hoa.getImage_Hoa();
                        Picasso.get().load(url_hoa).into(img_anh);
                    }
                }
            }
        });
        btn_addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HoaActivity.this, CommentActivity.class);
                intent1.putExtra("id_hoa", id_hoa);
                intent1.putExtra("id_user", user.getUid());
                intent1.putExtra("img_hoa",url_hoa);
                startActivity(intent1);
            }
        });
        doSubmit(id_hoa);
        ItemUser();
        iniRvcomment(id_hoa);

    }
    public class MyItemDecoration extends RecyclerView.ItemDecoration {
        private int spacing;

        public MyItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spacing;
            outRect.right = spacing;
            outRect.bottom = spacing;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = spacing;
            } else {
                outRect.top = 5;
            }
        }
    }
    private void iniRvcomment(String id_hoa){
        RVcomment.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").child(id_hoa);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lscmt = new ArrayList<>();
                for(DataSnapshot snap: snapshot.getChildren()){
                    Comment cmt = snap.getValue(Comment.class);
                    lscmt.add(cmt);
                }
                commentAdapter = new CommentAdapter(getApplicationContext(),lscmt);
                RVcomment.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ItemUser(){
        firebaseDatabase.getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User tam = snapshot.getValue(User.class);
                String ten = tam.getHoTen();
                String urlimage = tam.getImagea();
                users = new User(user.getUid(),urlimage,ten);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void doSubmit( String id_hoa){
        DatabaseReference ratingReference = firebaseDatabase.getReference("Comment").child(id_hoa);
        ratingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float sum = 0f;
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comment cmt = snapshot.getValue(Comment.class);
                    Float tam = new Float(cmt.getRating());
                    float rating = tam.floatValue();
                    sum += rating;
                    count++;
                    allrating.add(rating);
                }
                if (count > 0) {
                    averageRating = sum / count;
                    txt_ratting.setText(String.valueOf(averageRating));
                    ratingBar_comment.setRating(averageRating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}