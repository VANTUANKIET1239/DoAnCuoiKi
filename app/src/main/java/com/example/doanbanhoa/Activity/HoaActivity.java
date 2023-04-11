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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Adapter.CommentAdapter;
import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Adapter.ListDiaChiAdapter;
import com.example.doanbanhoa.Models.Commit;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.Models.Hoa;

import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.List;

public class HoaActivity extends AppCompatActivity {

    ImageView img_anh, img_user;
    TextView txt_ten, txt_gia, txt_mota, txt_comment;
    Button btn_addcomment;
    RecyclerView RVcomment;
    List<Commit> lscmt;
    CommentAdapter commentAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    User users;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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

        Intent intent = getIntent();
        String tenhoa =  intent.getStringExtra("tenhoa");

        firebaseFirestore.collection("Hoa").whereEqualTo("tenHoa",tenhoa)
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
                    Picasso.get().load(urlimage).into(img_user);
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
                DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").push();
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
        iniRvcomment();

    }
    private  void ShowMessage(String mess){
        Toast.makeText(this,mess, Toast.LENGTH_SHORT).show();
    }
    private void iniRvcomment(){
        RVcomment.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference = firebaseDatabase.getReference("Comment");
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
}