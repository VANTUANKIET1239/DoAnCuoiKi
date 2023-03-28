package com.example.doanbanhoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter {
    private Context context;
    private List list;
    public Adapter(Context context, List list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(HoaData.class.isInstance(list.get(position))){
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(this.getItemViewType(viewType) == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
            HoaHolder holder = new HoaHolder(view);
            return  holder;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listcard,parent,false);
            SliderHolder holder = new SliderHolder(view);
            return holder;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(this.getItemViewType(position) == 0){
            HoaData hoadata = (HoaData) list.get(position);
            ArrayList<Hoa> hoalist = hoadata.GeneratePhotoData();
            HoaHolder sl = (HoaHolder)holder;
            HoaAdapter adapter = new HoaAdapter(hoalist,context);
            sl.lishoa.setAdapter(adapter);

        }
        else {
            Slider slider = (Slider)list.get(position);
            SliderHolder sliderHolder = (SliderHolder) holder;
            sliderHolder.imgslider.setImageList(slider.getImageSlider(),ScaleTypes.FIT);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class HoaHolder extends RecyclerView.ViewHolder{
        private GridView lishoa;


        public HoaHolder(@NonNull View itemView) {
            super(itemView);
            lishoa = itemView.findViewById(R.id.gridview);
        }
    }
    static class SliderHolder extends RecyclerView.ViewHolder{

        ImageSlider imgslider;
        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            imgslider = itemView.findViewById(R.id.image_slider);
        }
    }
}
