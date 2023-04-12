package com.example.doanbanhoa.fragment;



import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doanbanhoa.Adapter.HoaListAdapter;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.Models.Slider;
import com.example.doanbanhoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class TrangChuFragment extends Fragment {
    GridView gridView;

    RecyclerView recyclerView;
    RecyclerView recyclerViewngang;
    ImageSlider imgslider;
    private ArrayAdapter<String> itemthutugia;

    EditText timkiemtrangchu;

     ImageButton btntimkiem,btnreset;

    private Context context;
    private FirebaseFirestore firebaseFirestore;

   private String[] itemsthutu = {"Gía từ cao đến thấp", "Gía từ thấp đến cao"};
    AutoCompleteTextView autocomple;

    private String tensp = "";
    public TrangChuFragment(Context context) {
        this.context = context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgslider = view.findViewById(R.id.image_slider);
        gridView = view.findViewById(R.id.gridview);
        recyclerView = view.findViewById(R.id.recycleviewhoa);
        recyclerViewngang = view.findViewById(R.id.recycleviewhoangang);
        timkiemtrangchu = view.findViewById(R.id.timkiemtrangchu);
        btntimkiem = view.findViewById(R.id.btntimkiem);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewngang.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(),2));
        btnreset = view.findViewById(R.id.btnreset);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    lshoa.add(hoa);
                                }
                                HoaListAdapter adapter = new HoaListAdapter(context,lshoa);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                tensp = "";
            }
        });

        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tensp = timkiemtrangchu.getText().toString();
                if(tensp.length() != 0){

                    firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Hoa> lshoa = new ArrayList<>();

                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    Hoa hoa = doc.toObject(Hoa.class);
                                    if (hoa.getTenHoa().toLowerCase().contains(tensp.toLowerCase())){
                                        lshoa.add(hoa);

                                    }
                                }
                                HoaListAdapter adapter = new HoaListAdapter(context, lshoa);
                                recyclerView.setAdapter(adapter);
                            }
                        }

                    });
                }
                else {

                    Toast.makeText(context,"Chưa Nhập Nội Dung Tìm Kiếm",Toast.LENGTH_SHORT).show();

                }
            }
        });
        firebaseFirestore.collection("Sliders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    List<SlideModel> im = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Slider slider = new Slider(doc.getId(), new SlideModel(doc.get("imageSlider").toString(),ScaleTypes.FIT));
                        im.add(slider.getImageSlider());
                    }

                    imgslider.setImageList(im,ScaleTypes.FIT );
                }
            }
        });

        autocomple = view.findViewById(R.id.thutugia);
        itemthutugia = new ArrayAdapter<>(context,R.layout.listitem_thutugia,itemsthutu);
        autocomple.setAdapter(itemthutugia);
        autocomple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Query.Direction direction;
                // từ cao đến thấp
                if(position == 0){
                    direction = Query.Direction.DESCENDING;
                }
                else {
                   direction = Query.Direction.ASCENDING;
                }
                firebaseFirestore.collection("Hoa").orderBy("gia", direction).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Hoa> listHoaa = new ArrayList<>();
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                Hoa newhoa = (Hoa) doc.toObject(Hoa.class);
                                if (newhoa.getTenHoa().toLowerCase().contains(tensp.toLowerCase())){
                                    listHoaa.add(newhoa);
                                }

                            }
                            HoaListAdapter adapter = new HoaListAdapter(context,listHoaa);
//                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(),2));
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
            }
        });



//            firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if(task.isSuccessful()){
//                        List<Hoa> listHoaa = new ArrayList<>();
//                        for(QueryDocumentSnapshot doc : task.getResult()){
//                            Hoa newhoa = doc.toObject(Hoa.class);
//                            listHoaa.add(newhoa);
//                        }
//                        HoaListAdapter adapter = new HoaListAdapter(getActivity().getBaseContext(),listHoaa);
//                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(),2));
//                        recyclerView.setAdapter(adapter);
//
//                    }
//                }
//            });

          //  List<Hoa> listHoaa = new ArrayList<>();



        firebaseFirestore.collection("Hoa").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Hoa> listHoaa = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Hoa newhoa = doc.toObject(Hoa.class);
                        listHoaa.add(newhoa);
                    }

                    HoaListAdapter adapterngang = new HoaListAdapter(context,listHoaa);
                    recyclerViewngang.setAdapter(adapterngang);

                    HoaListAdapter adapter = new HoaListAdapter(context,listHoaa);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    //    listHoaa.add(new Hoa("1","đư","feifnef","ềof",1323,"fwfwf",4,5,"34"));


    }


    public static TrangChuFragment newInstance() {
        TrangChuFragment fragment = new TrangChuFragment(newInstance().context);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

}