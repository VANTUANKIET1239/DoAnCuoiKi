package com.example.doanbanhoa.Adapter;

import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.R;

import java.util.ArrayList;
import java.util.List;

public class HoaListAdapter extends RecyclerView.Adapter<HoaViewHolder> {

    private final LayoutInflater mInflater;
    private Context context;

    private List<Hoa> mHoaList = new ArrayList<>();

    public List<Hoa> getmWordList() {
        return mHoaList;
    }
    public HoaListAdapter(Context context, List<Hoa> hoalist) {
        mInflater = LayoutInflater.from(context);
        this.mHoaList = hoalist;
        this.context = context;
    }

    @NonNull
    @Override
    public HoaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.hoa_disp_tpl, parent, false);
        return new HoaViewHolder(mItemView, this,context);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaViewHolder holder, int position) {
//        Hoa mCurrent = mHoaList.get(position);
//        holder..setText(mCurrent.getTitle_photo());
//        loadImageFromUrl(mCurrent.getSource_photo(),holder.img);

        holder.tv_caption.setText(mHoaList.get(position).getTenHoa());
        loadImageFromUrl(mHoaList.get(position).getImage_Hoa(), holder.iv_photo);
        holder.tv_giahoa.setText(String.valueOf(mHoaList.get(position).getGia()) + "Đ");
        holder.tv_danhgia.setText(mHoaList.get(position).getHangDanhGia() + "(" + mHoaList.get(position).getSoLuongDanhGia() + ")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Create an intent to move to the other activity and pass the item information
//                Intent intent = new Intent(context, ShowItem.class);
//                intent.putExtra("imageUrl", mCurrent.getSource_photo());
//                intent.putExtra("caption", mCurrent.getTitle_photo());
//                intent.putExtra("description", mCurrent.getDescription_photo());
//                context.startActivity(intent);
            }

        });
    }
    @Override
    public int getItemCount() {
        return mHoaList.size();
    }
}
