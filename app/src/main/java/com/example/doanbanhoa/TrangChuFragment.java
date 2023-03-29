package com.example.doanbanhoa;

import static com.example.doanbanhoa.HoaData.AddHoa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChuFragment extends Fragment {
    GridView gridView;
    //
    ImageSlider imgslider;
    public TrangChuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgslider = view.findViewById(R.id.image_slider);
        readJson();
        List<SlideModel> im = new ArrayList<>();
        im.add(new SlideModel("https://picsum.photos/id/237/200/300", ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/seed/picsum/200/300",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300?grayscale",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300/?blur",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300.jpg",ScaleTypes.FIT));
        imgslider.setImageList(im,ScaleTypes.FIT );


        gridView = view.findViewById(R.id.gridview);
    //    List<Hoa> listHoa = new ArrayList<>();
        HoaAdapter adapter = new HoaAdapter(HoaData.GeneratePhotoData(),getContext());
        gridView.setAdapter(adapter);
    }

    public static TrangChuFragment newInstance() {
        TrangChuFragment fragment = new TrangChuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }
    public void readJson(){
        ArrayList<Hoa> listpho  = new ArrayList<>();
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
                // listpho.add(bonghoa);

            }
            //    return  listpho;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return null;
    }
}