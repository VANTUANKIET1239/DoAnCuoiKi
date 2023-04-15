package com.example.doanbanhoa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.doanbanhoa.Adapter.ItemListAdapter;
import com.example.doanbanhoa.Models.Bill;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.R;

import java.util.List;

public class DonHangActivity extends AppCompatActivity {
    GridView gridItem;
    ItemListAdapter mAdapter;
    List<Item> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        Intent intent = getIntent();
        Bundle msg = intent.getExtras();
        Bill b;
        if (msg != null) {
            b = (Bill) intent.getSerializableExtra("bill");
            listItem = b.getItems();
        }

        gridItem = (GridView) findViewById(R.id.gridItem);
        mAdapter = new ItemListAdapter(getApplicationContext(), listItem);
        gridItem.setAdapter(mAdapter);
    }
}