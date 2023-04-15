package com.example.doanbanhoa.Adapter;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Activity.HoaActivity;
import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangViewHolder> {

    private int selectedPosition = -1;

    private final LayoutInflater mInflater;
    private Context context;


    private List<Item> mItemList = new ArrayList<>();

    public List<Item> getmWordList() {
        return mItemList;
    }
    public GioHangAdapter(Context context, List<Item> itemlist) {
        mInflater = LayoutInflater.from(context);
        this.mItemList = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layout_itemgiohang, parent, false);
        return new GioHangViewHolder(mItemView);
    }

    private String convertpricetostring(int a){
        NumberFormat kiet = new DecimalFormat("#,###");
        return kiet.format(a);
    }
    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
//        Hoa mCurrent = mHoaList.get(position);
//        holder..setText(mCurrent.getTitle_photo());
//        loadImageFromUrl(mCurrent.getSource_photo(),holder.img);

        holder.title.setText(mItemList.get(position).getHoa().getTenHoa());
        loadImageFromUrl(mItemList.get(position).getHoa().getImage_Hoa(), holder.iv_photo);
        holder.donGia.setText(convertpricetostring(mItemList.get(position).getHoa().getGia()));
        Item item = mItemList.get(position);
        String moTa = mItemList.get(position).getHoa().getMoTa();
        if (moTa.length() > 25) {
            holder.word.setText(moTa.substring(0, 25) + "...");
        } else {
            holder.word.setText(moTa);
        }
        holder.soLuong.setText(Integer.toString(mItemList.get(position).getSoLuong()));
        holder.tonggia.setText(convertpricetostring(mItemList.get(position).getHoa().getGia()*mItemList.get(position).getSoLuong()) + "Đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to move to the other activity and pass the item information
                Intent intent = new Intent(context, GioHangAdapter.class);
                intent.putExtra("id", item.getHoa().getId());
                startActivity(context,intent,null);

            }
        });
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}


