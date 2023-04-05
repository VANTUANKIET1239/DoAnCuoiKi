package com.example.doanbanhoa.fragment;

import static com.example.doanbanhoa.Models.HoaData.AddHoa;

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
import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.Models.HoaData;
import com.example.doanbanhoa.Models.Slider;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        //readJson();


        firebaseFirestore.collection("Sliders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    List<SlideModel> im = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Slider slider = new Slider(new SlideModel(doc.get("imageSlider").toString(),ScaleTypes.FIT));
                        im.add(slider.getImageSlider());
                    }
//
                    imgslider.setImageList(im,ScaleTypes.FIT );
                }
            }
        });


        autocomple = view.findViewById(R.id.thutugia);
        itemthutugia = new ArrayAdapter<>(getContext(),R.layout.listitem_thutugia,itemsthutu);
        autocomple.setAdapter(itemthutugia);
        autocomple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // từ cao đến thấp
                if(position == 0){
                    firebaseFirestore.collection("Hoa").orderBy("gia", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> listHoaa = new ArrayList<>();
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                                    listHoaa.add(newhoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getContext(),listHoaa);
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                                recyclerView.setAdapter(adapter);

                            }
                        }
                    });
                }
                else {
                    firebaseFirestore.collection("Hoa").orderBy("gia", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> listHoaa = new ArrayList<>();
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                                    listHoaa.add(newhoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(getContext(),listHoaa);
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                                recyclerView.setAdapter(adapter);

                            }
                        }
                    });
                }
            }
        });
//


            firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        List<Hoa> listHoaa = new ArrayList<>();
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                            listHoaa.add(newhoa);
                        }
                        HoaListAdapter adapter = new HoaListAdapter(getContext(),listHoaa);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(adapter);

                    }
                }
            });

        firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Hoa> listHoaa = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                        listHoaa.add(newhoa);
                    }
                    HoaListAdapter adapterngang = new HoaListAdapter(getContext(),listHoaa);
                    recyclerViewngang.setAdapter(adapterngang);
                    recyclerViewngang.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

                }
            }
        });







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

}