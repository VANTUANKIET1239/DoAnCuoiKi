package com.example.doanbanhoa.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Adapter.CommentAdapter;
import com.example.doanbanhoa.Adapter.GioHangAdapter;
import com.example.doanbanhoa.Models.Comment;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCartActivity extends AppCompatActivity {

    TextView soluong;
    GioHangAdapter gioHangAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    ArrayList<Item> itemArrayList;
    TextView tvTotalPrice;
    int Totalprice = 0;


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
        soluong = findViewById(R.id.soluong);
        Intent intent = getIntent();
        int sl =  intent.getIntExtra("soluong",0);
        int position = intent.getIntExtra("position", 0);
        soluong = new TextView(getApplicationContext());
        soluong.setText(String.valueOf(sl));
        if (soluong != null) {
            soluong.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String soluongText = s.toString().trim();
                    if (soluongText.isEmpty()) {
                        return;
                    }
                    int soluong = Integer.parseInt(soluongText);

                    if (position < 0) {
                        return;
                    }
                    Item item = itemArrayList.get(position);
                    item.setSoLuong(soluong);
                    item.setTongGia(soluong * item.getHoa().getGia());

                    Totalprice = 0;
                    for (Item cartItem : itemArrayList) {
                        Totalprice += cartItem.getTongGia();
                    }
                    String totalPriceText = Totalprice + " Đ";
                    tvTotalPrice.setText(totalPriceText);

                    gioHangAdapter.notifyItemChanged(position);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

//        themgiohang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String customerId = firebaseAuth.getInstance().getCurrentUser().getUid();
//                firebaseDatabase = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = firebaseDatabase.getReference("GioHang").child(customerId).push();
//                Item item = new Item(hoa, Integer.valueOf(txt_soluong.getText().toString()));
//                myRef.setValue(item);
//            }
//        });
//        firebaseFirestore.collection("Hoa").whereEqualTo("id",id_hoa)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        hoa = doc.toObject(Hoa.class);
//                        txt_ten.setText(hoa.getTenHoa());
//                        txt_mota.setText(hoa.getMoTa());
//                        Integer gia = hoa.getGia();
//                        txt_gia.setText(gia.toString() + "Đ");
//                        url_hoa = hoa.getImage_Hoa();
//                        Picasso.get().load(url_hoa).into(img_anh);
//                    }
//                }
//            }
//        });
    }
}