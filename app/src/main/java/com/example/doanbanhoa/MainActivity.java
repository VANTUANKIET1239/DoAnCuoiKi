package com.example.doanbanhoa;

import static com.example.doanbanhoa.HoaData.AddHoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager2 mViewpaper;


        private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navmenu);
       // mViewpaper = findViewById(R.id.view_pager);

        fragmentArrayList.add(new TrangChuFragment());
        fragmentArrayList.add(new TaiKhoanFragment());
        fragmentArrayList.add(new GioHangFragment());

        loadFragment(new TrangChuFragment());
       /* mViewpaper.setAdapter(setupviewpager(fragmentArrayList));
        mViewpaper.setOffscreenPageLimit(1);
        mViewpaper.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0: navigationView.setSelectedItemId(R.id.trangchu);break;
                    case 1: navigationView.setSelectedItemId(R.id.taikhoan);break;
                    case 2: navigationView.setSelectedItemId(R.id.giohang); break;
                }


                super.onPageSelected(position);
            }
        });*/
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
//               switch (item.getItemId()){
//                   case R.id.trangchu: mViewpaper.setCurrentItem(0); break;
//                   case R.id.taikhoan: mViewpaper.setCurrentItem(1);break;
//                   case R.id.giohang:mViewpaper.setCurrentItem(2); break;
//               }

                switch (item.getItemId()){
                    case R.id.trangchu: fragment = new TrangChuFragment(); break;
                    case R.id.taikhoan: fragment = new TaiKhoanFragment();break;
                    case R.id.giohang:fragment = new GioHangFragment(); break;
                }

                if(fragment != null){
                    loadFragment(fragment);
                }
               return true;
            }
        });

    }
    private ViewPagerAdapter setupviewpager(ArrayList<Fragment> arr){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,arr);
        return viewPagerAdapter;
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
    }

}
