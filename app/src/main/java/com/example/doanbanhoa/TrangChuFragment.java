package com.example.doanbanhoa;

import static com.example.doanbanhoa.HoaData.AddHoa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
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

    RecyclerView recyclerView;
    RecyclerView recyclerViewngang;
    ImageSlider imgslider;
    ArrayAdapter<String> itemthutugia;

    FirebaseFirestore firebaseFirestore;
    String[] itemsthutu = {"Gía từ cao đến thấp", "Gía từ thấp đến cao"};
    AutoCompleteTextView autocomple;
    public TrangChuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgslider = view.findViewById(R.id.image_slider);
        gridView = view.findViewById(R.id.gridview);
        recyclerView = view.findViewById(R.id.recycleviewhoa);
        recyclerViewngang = view.findViewById(R.id.recycleviewhoangang);

        firebaseFirestore = FirebaseFirestore.getInstance();
        readJson();
        List<SlideModel> im = new ArrayList<>();

        im.add(new SlideModel("https://assets.flowerstore.ph/public/tenantVN/app/assets/images/banner/747_HlrlmaWfv61ZGzkibPJUzKJL2.webp?fbclid=IwAR0gkUnD4eT3tANWG4bsZ9EyVQtXYR4Rh8HC-OcM3BGhfowi-W5Mh4_xvvg", ScaleTypes.FIT));
        im.add(new SlideModel("https://assets.flowerstore.ph/public/tenantVN/app/assets/images/banner/747_WLpDyF1fpsD0ZUXy5mIDhkwa0.webp?fbclid=IwAR0bbcD6wZ7TCYtrh_32v9jLfc36TcDVGlm7fS_UN04zGG9B5KwbXUucD_E",ScaleTypes.FIT));
        im.add(new SlideModel("https://assets.flowerstore.ph/public/tenantVN/app/assets/images/banner/747_MIyQNxP2Nuq6vb2b0WLWhelkZ.webp?fbclid=IwAR0gkUnD4eT3tANWG4bsZ9EyVQtXYR4Rh8HC-OcM3BGhfowi-W5Mh4_xvvg",ScaleTypes.FIT));
        im.add(new SlideModel("https://assets.flowerstore.ph/public/tenantVN/app/assets/images/banner/747_qFwRVcHB3U7Qp1jm7cGjcnKtz.webp?fbclid=IwAR0VEm2ilcxw7MuYpYfZZPo1jfKx10zc0L5JlGXfma__yaDKmZnvaE8korQ",ScaleTypes.FIT));
        im.add(new SlideModel("https://picsum.photos/200/300.jpg",ScaleTypes.FIT));
        imgslider.setImageList(im,ScaleTypes.FIT );
        autocomple = view.findViewById(R.id.thutugia);
        itemthutugia = new ArrayAdapter<>(getContext(),R.layout.listitem_thutugia,itemsthutu);
        autocomple.setAdapter(itemthutugia);
        autocomple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // từ cao đến thấp
                if(position == 0){
                   HoaData.xapxepcaothap(true);
                    HoaListAdapter adapter = new HoaListAdapter(getContext(),HoaData.GeneratePhotoData());
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recyclerView.setAdapter(adapter);
                }
                else {
                    HoaData.xapxepcaothap(false);
                    HoaListAdapter adapter = new HoaListAdapter(getContext(),HoaData.GeneratePhotoData());
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        Hoa tam = new Hoa(1,"êrer","êfef","êfe",5000,"êfqfebb",4,10,true,"1");
        firebaseFirestore.collection("Hoa").add(tam);
        List<Hoa> listHoa = new ArrayList<>();
        firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Hoa> listHoa = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : task.getResult()){

                            Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                            listHoa.add(newhoa);


                    }
                    HoaListAdapter adapter = new HoaListAdapter(getContext(),listHoa);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        HoaListAdapter adapterngang = new HoaListAdapter(getContext(),listHoa);
        recyclerViewngang.setAdapter(adapterngang);
        recyclerViewngang.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));




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