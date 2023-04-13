package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doanbanhoa.Adapter.CommentAdapter;
import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Adapter.ListDiaChiAdapter;
import com.example.doanbanhoa.LayHinhAnh;
import com.example.doanbanhoa.Models.Commit;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.Models.Hoa;

import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HoaActivity extends AppCompatActivity {

    ImageView img_anh, img_user, minus, plus;
    TextView txt_ten, txt_gia, txt_mota, txt_comment, txt_soluong;
    Button btn_addcomment;
    BottomNavigationView themgiohang;
    RecyclerView RVcomment;
    List<Commit> lscmt;
    CommentAdapter commentAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    User users;
    Integer soluong;
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

        img_anh = findViewById(R.id.image_anh);
        txt_ten = findViewById(R.id.txt_tensanpham);
        txt_gia = findViewById(R.id.txt_giá);
        txt_mota = findViewById(R.id.txt_motasp);
        txt_comment = findViewById(R.id.edittext_comment);
        btn_addcomment = findViewById(R.id.btn_add);
        RVcomment = findViewById(R.id.rv_comment);
        img_user = findViewById(R.id.img_anhdaidien);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        txt_soluong = findViewById(R.id.txt_soluong);
        themgiohang = findViewById(R.id.btn_themgiohang);

        Intent intent = getIntent();
        String id_hoa =  intent.getStringExtra("id");
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
                        Hoa hoa = doc.toObject(Hoa.class);
                        txt_ten.setText(hoa.getTenHoa());
                        txt_mota.setText(hoa.getMoTa());
                        Integer gia = hoa.getGia();
                        txt_gia.setText(gia.toString() + "Đ");
                        String url = hoa.getImage_Hoa();
                        Picasso.get().load(url).into(img_anh);
                    }
                }
            }
        });
        firebaseDatabase.getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User tam = snapshot.getValue(User.class);
                    String ten = tam.getHoTen();
                    String urlimage = tam.getImagea();
                   Picasso.get().load(urlimage).resize(50,50).into(img_user);
                    users = new User(user.getUid(),urlimage,ten);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_addcomment.setVisibility(View.INVISIBLE);
                DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").child(id_hoa).push();
                String comment = txt_comment.getText().toString();
                String id = users.getId();
                String user_name = users.getHoTen();
                String url = users.getImagea();
                Commit commit = new Commit(id, comment, user_name,url);

                databaseReference.setValue(commit).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ShowMessage("comment add");
                        txt_comment.setText("");
                        btn_addcomment.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        iniRvcomment(id_hoa);

    }
    private  void ShowMessage(String mess){
        Toast.makeText(this,mess, Toast.LENGTH_SHORT).show();
    }
    private void iniRvcomment(String id_hoa){
        RVcomment.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").child(id_hoa);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lscmt = new ArrayList<>();
                for(DataSnapshot snap: snapshot.getChildren()){
                    Commit cmt = snap.getValue(Commit.class);
                    lscmt.add(cmt);
                }
                commentAdapter = new CommentAdapter(getApplicationContext(), lscmt);
                RVcomment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  String timeStamp(long time){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }
}