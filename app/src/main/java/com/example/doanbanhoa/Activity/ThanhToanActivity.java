package com.example.doanbanhoa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;
import android.widget.GridView;

import com.example.doanbanhoa.Adapter.ItemListAdapter;
import com.example.doanbanhoa.R;

public class ThanhToanActivity extends AppCompatActivity {
    GridView gridItem;
    ItemListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        gridItem = (GridView) findViewById(R.id.gridItem);


        /*mAdapter = new ItemListAdapter(getApplicationContext(), items);
        gridItem.setAdapter(mAdapter);
        gridItem.setLayoutManager(new LinearLayoutManager(this));*/
    }
}