package com.example.doanbanhoa.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ConditionVariable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doanbanhoa.Adapter.GioHangAdapter;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class GioHangFragment extends Fragment {
    ArrayList<Item> itemArrayList;
    RecyclerView recyclerView;
    TextView tvTotalPrice;
    EditText editText;
    GioHangAdapter gioHangAdapter;
    Button Thanhtoan;

    FirebaseFirestore firebaseFirestore;

    FirebaseDatabase firebaseDatabase;

    FirebaseAuth firebaseAuth;

    private Context context;

    int Totalprice = 0;

    public GioHangFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }


    public static GioHangFragment newInstance() {
        GioHangFragment fragment = new GioHangFragment(newInstance().context);

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleitem);
        itemArrayList = new ArrayList<>();
        gioHangAdapter = new GioHangAdapter(this.context, itemArrayList);
        createItemList();
        recyclerView.setAdapter(gioHangAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        editText = view.findViewById(R.id.soluong);
        tvTotalPrice = view.findViewById(R.id.id_giatamtinh);
        Thanhtoan = view.findViewById(R.id.giohang);
    }
    private void createItemList() {
        String customerId = firebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GioHang").child(customerId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemArrayList.clear();
                for (DataSnapshot cartItemSnapshot : snapshot.getChildren()) {
                    Item cartItem = cartItemSnapshot.getValue(Item.class);
                    itemArrayList.add(cartItem);
                    Totalprice += cartItem.getTongGia();
                    String totalPriceText = Totalprice + " Đ";
                    tvTotalPrice.setText(totalPriceText);
                }
                gioHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
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

                    int position = gioHangAdapter.getSelectedPosition();
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
        /*Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}