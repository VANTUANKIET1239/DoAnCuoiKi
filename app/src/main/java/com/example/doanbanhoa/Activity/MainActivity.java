package com.example.doanbanhoa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.doanbanhoa.Adapter.ViewPagerAdapter;
import com.example.doanbanhoa.R;
import com.example.doanbanhoa.fragment.GioHangFragment;
import com.example.doanbanhoa.fragment.TaiKhoanFragment;
import com.example.doanbanhoa.fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

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
                    case R.id.trangchu: fragment = fragmentArrayList.get(0); break;
                    case R.id.taikhoan: fragment = fragmentArrayList.get(1);break;
                    case R.id.giohang:fragment = fragmentArrayList.get(2); break;
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
