package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanbanhoa.Adapter.ItemListAdapter;
import com.example.doanbanhoa.Models.Bill;
import com.example.doanbanhoa.Models.DiaChi;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseDatabase database;
    GridView gridItem;
    ItemListAdapter mAdapter;
    AutoCompleteTextView autodiachi;
    ArrayAdapter<String> diachiadapter;
    String[] itemdiachi;
    TextView txtDay, txtAddress, txtTime;
    Button btnYes,themdiachi;
    ImageButton btnDay, btnTime, btnBack;
    Calendar now;
    List<Item> items;
    String day = "", time="", address ="";

    public static final String SHARE_PRES = "com.example.doanbanhoa";
     public static final String Address = "diachi";

    public static final String Day = "ngay";

    public static final String Time = "gio";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_thanh_toan);
         gridItem = findViewById(R.id.gridItem);
         txtTime = findViewById(R.id.txtTime);
         txtDay = findViewById(R.id.txtDay);
         btnBack = findViewById(R.id.btnBack);
         btnTime = findViewById(R.id.btnTime);
         btnDay = findViewById(R.id.btnDay);
         btnYes = findViewById(R.id.btnYes);
         autodiachi = findViewById(R.id.listdiachi);
         themdiachi = findViewById(R.id.themdiachi);
         auth = FirebaseAuth.getInstance();

         database = FirebaseDatabase.getInstance();

         /*Lay intent list items*/
         Intent intent = getIntent();
         Bundle msg = intent.getExtras();
         if (msg != null) {
             byte i = 0;
             String ite = "item";
             while(msg.getSerializable(ite.concat(String.valueOf(i)))!=null)
             {
                 Item item = (Item) msg.getSerializable(ite);
                 items.add(item);
                 i++;
             }
             if(msg.getSerializable("item1") != null){
                 Item item = (Item) msg.getSerializable("item1");
                 items = new ArrayList<>();
                 items.add(item);
                 mAdapter = new ItemListAdapter(getBaseContext(), items, true);
                 gridItem.setAdapter(mAdapter);
             }
         }
         else {
             database.getReference("GioHang").child(auth.getCurrentUser().getUid().trim()).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     items = new ArrayList<>();
                     for(DataSnapshot dt : snapshot.getChildren()){
                         Item item = dt.getValue(Item.class);
                         items.add(item);
                     }
                     mAdapter = new ItemListAdapter(getBaseContext(), items, true);
                     gridItem.setAdapter(mAdapter);
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
         }
         /*themdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ThemDiaChiActivity.class));
            }
        });*/
        autodiachi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveData();
            }
        });
        database.getReference("DiaChi").child(auth.getCurrentUser().getUid().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> dc = new ArrayList<>();
                for(DataSnapshot doc : snapshot.getChildren()){
                    DiaChi diaChi = doc.getValue(DiaChi.class);
                    dc.add(diaChi.getDiaChi());
                }
                itemdiachi =  dc.toArray(new String[0]);
                diachiadapter = new ArrayAdapter<>(getApplicationContext(),R.layout.listitem_thutugia,itemdiachi);
                autodiachi.setAdapter(diachiadapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //gridItem.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnDay.setOnClickListener(this);
        btnYes.setOnClickListener(this);
        themdiachi.setOnClickListener(this);
         loadData();
         setview();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDay){
            now = Calendar.getInstance();//co can +7 hay ko?
            DatePickerDialog day = new DatePickerDialog(this, (datePicker, y, m, d)
                    -> txtDay.setText("Ngày "+d+" tháng "+(m+1)+" năm "+y),
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
            address = autodiachi.getText().toString();
            if(day.equals("")||time.equals("")||address.equals("")){
                Toast.makeText(getBaseContext(), "Vui lòng nhập đầy đủ thông tin đặt hàng",Toast.LENGTH_SHORT).show();
            }else{
                AlertDialog.Builder alConfirm = new AlertDialog.Builder(this);
                alConfirm.setTitle("Xác nhận đặt hoa");
                alConfirm.setMessage("Nhận hoa lúc "+time+", "+day+" tại "+address);
                alConfirm.setIcon(R.drawable.hoamoi);
                alConfirm.setNeutralButton("Xem lại thông tin", (dialogInterface, i) -> dialogInterface.cancel());
                alConfirm.setNegativeButton("Hủy đặt", (dialogInterface, i) -> finish());
                alConfirm.setPositiveButton("Xác nhận đặt hoa", (dialogInterface, i) -> saveBill());
                alConfirm.show();
            }
        }
        if(view.getId() == R.id.themdiachi){
            startActivity(new Intent(getApplicationContext(),ThemDiaChiActivity.class));
        }

    }
    private void saveBill(){
        database = FirebaseDatabase.getInstance();
        String customerId = auth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("Bill").child(customerId.trim()).push();
        String key = myRef.getKey();
        String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        auth = FirebaseAuth.getInstance();
        Bill bill = new Bill(key,timestamp, day,time,address, items);
        myRef.setValue(bill);

        DatabaseReference db = database.getReference("Bill").child(customerId.trim());
        db.child(key.trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bill b = snapshot.getValue(Bill.class);
                updateCart(auth.getCurrentUser().getUid().trim());
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
    private void updateCart(String uId){
        DatabaseReference gioRef = database.getReference("GioHang").child(uId);
       gioRef.removeValue();

    }

    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Address,autodiachi.getText().toString());
        editor.putString(Day,txtDay.getText().toString());
        editor.putString(Time,txtTime.getText().toString());
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PRES,MODE_PRIVATE);
        address = sharedPreferences.getString(Address,"");
        time = sharedPreferences.getString(Time,"");
        day = sharedPreferences.getString(Day,"");
    }
    public void setview(){
        autodiachi.setText(address);
        txtDay.setText(day);
        txtTime.setText(time);
    }
}