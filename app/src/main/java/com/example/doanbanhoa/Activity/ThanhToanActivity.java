package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Adapter.ItemListAdapter;
import com.example.doanbanhoa.Models.Bill;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseDatabase database;
    GridView gridItem;
    ItemListAdapter mAdapter;
    TextView txtDay, txtAddress, txtTime;
    Button btnBack, btnYes;
    ImageButton btnDay, btnTime;
    Calendar now;
    List<Item> items;
    String day = "", time="", address ="222";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        gridItem = (GridView) findViewById(R.id.gridItem);
        txtTime.findViewById(R.id.txtTime);
        txtDay.findViewById(R.id.txtDay);
        txtAddress.findViewById(R.id.txtAddress);
        btnBack.findViewById(R.id.btnBack);
        btnTime.findViewById(R.id.btnTime);
        btnDay.findViewById(R.id.btnDay);
        btnYes.findViewById(R.id.btnYes);

        /*Lay intent list items*/
        Intent intent = getIntent();
        Bundle msg = intent.getExtras();
        if (msg != null) {
            byte i = 0;
            while(msg.getSerializable("item")!=null)
            {
                Item item = (Item) msg.getSerializable("item"+i);
                items.add(item);
            }
        }

        mAdapter = new ItemListAdapter(getApplicationContext(), items);
        gridItem.setAdapter(mAdapter);

        //gridItem.setLayoutManager(new LinearLayoutManager(this));

        /*Intent in = getIntent();
        if(in.getStringExtra("day")!=null)
            {day = in.getStringExtra("day");}
        if(in.getStringExtra("address")!=null)
            {address = in.getStringExtra("address");}*/

        btnBack.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnDay.setOnClickListener(this);

        txtAddress.setText(address);
        txtDay.setText(day);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDay){
            now = Calendar.getInstance();//co can +7 hay ko?
            DatePickerDialog day = new DatePickerDialog(this, (datePicker, y, m, d)
                    -> txtDay.setText("Ngày "+d+" tháng "+m+" năm "+y),
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            day.show();
        }
        if(view.getId() == R.id.btnTime){
            TimePickerDialog time = new TimePickerDialog(this, (timePicker, h, m)
                    -> txtTime.setText(h+" : "+m),10,10,true);
            time.show();
        }
        if(view.getId() == R.id.btnBack){

        }
        if(view.getId() == R.id.btnYes){
            day = txtDay.getText().toString();
            time = txtTime.getText().toString();
            address = txtAddress.getText().toString();
            if(day==null||time==null||address==null){
                Toast.makeText(getBaseContext(), "Vui lòng nhập đầy đủ thông tin đặt hàng",Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder alConfirm = new AlertDialog.Builder(this);
                alConfirm.setTitle("Xác nhận đặt hoa");
                alConfirm.setMessage("Nhận hoa lúc "+time+", "+day+" tại "+address);
                alConfirm.setIcon(R.drawable.hoamoi);
                alConfirm.setNeutralButton("Xem lại thông tin", (dialogInterface, i) -> dialogInterface.cancel());
                alConfirm.setNegativeButton("Hủy đặt", (dialogInterface, i) -> finish());
                alConfirm.setPositiveButton("Xác nhận đặt hoa", (dialogInterface, i) -> saveBill());
            }
        }

    }
    private void saveBill(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Bills");

        String customerId = auth.getInstance().getCurrentUser().getUid();
        Bill bill = new Bill(customerId, day,time,address, items);
        myRef.push().setValue(bill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bill b = snapshot.getValue(Bill.class);
                updateCart(b.getCustomerId(), b.getItems());
                Intent in = new Intent(getBaseContext(), DonHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bill", bill);
                in.putExtras(bundle);
                startActivity(in);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), "Rất tiếc, đặt hoa thất bại!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateCart(String uId, List<Item> items){
        DatabaseReference gioRef = database.getReference("Giohang").child(uId);
        for(Item i : items){
            gioRef.child(i.getHoa().getId()).removeValue();//Check!!!!!!!!!!!!!!!!!
        }

    }
}