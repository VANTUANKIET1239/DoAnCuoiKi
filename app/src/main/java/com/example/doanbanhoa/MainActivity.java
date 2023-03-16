package com.example.doanbanhoa;

import static com.example.doanbanhoa.HoaData.AddHoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

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
    GridView gridView;
    ImageSlider imgslider;
   /* private AdapterView.OnItemClickListener onitemclick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Intent intent = new Intent(getBaseContext(),ViewPhotoActivity.class);
            intent.putExtra("id",position);
            startActivity(intent);
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readJson();
        createSlider();
        gridView = findViewById(R.id.gridview);
        HoaAdapter adapter = new HoaAdapter(HoaData.GeneratePhotoData(),getApplicationContext());
        gridView.setAdapter(adapter);
      //  gridView.setOnItemClickListener(onitemclick);
    }
    public void createSlider(){
        imgslider = findViewById(R.id.image_slider);
        List<SlideModel> im = new ArrayList<>();
        im.add(new SlideModel("https://picsum.photos/id/237/200/300", ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/seed/picsum/200/300",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300?grayscale",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300/?blur",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300.jpg",ScaleTypes.FIT));
        imgslider.setImageList(im,ScaleTypes.FIT );

    }
    public void readJson(){
        //    List<Photo> listpho = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.hoa);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = builder.toString();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int ID_hoa = jsonObject.getInt("ID_Hoa");
                String Image_Hoa = jsonObject.getString("Image_Hoa");
                String TenHoa = jsonObject.getString("TenHoa");
                String LoaiHoa = jsonObject.getString("LoaiHoa");
                int Gia = jsonObject.getInt("Gia");
                String MoTa = jsonObject.getString("MoTa");
                int HangDanhGia = jsonObject.getInt("HangDanhGia");
                int SoLuongDanhGia = jsonObject.getInt("SoLuongDanhGia");
                boolean IsDelete = jsonObject.getBoolean("IsDelete");
                String ID_DanhMuc = jsonObject.getString("ID_DanhMuc");

                Hoa bonghoa = new Hoa(ID_hoa,Image_Hoa,TenHoa,LoaiHoa,Gia,MoTa,HangDanhGia,SoLuongDanhGia,IsDelete,ID_DanhMuc);
                AddHoa(bonghoa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
