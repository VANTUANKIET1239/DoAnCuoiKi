package com.example.doanbanhoa.Adapter;

import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Models.Hoa;
import com.example.doanbanhoa.Models.Item;
import com.example.doanbanhoa.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    private Context context;
    private List<Item> mItemList ;

    public ItemListAdapter(Context context, List<Item> itemList) {
        this.mItemList = itemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Item getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public String getItemID(int i) {
        return mItemList.get(i).getHoa().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.item_view_checkout,null);
            dataitem.pic = convertView.findViewById(R.id.pic);
            dataitem.title = convertView.findViewById(R.id.title);
            dataitem.word = convertView.findViewById(R.id.word);
            dataitem.quantity = convertView.findViewById(R.id.quantity);
            dataitem.price = convertView.findViewById(R.id.price);
            convertView.setTag(dataitem);

        }
        else {
            dataitem = (MyView) convertView.getTag();
        }
        Hoa h = mItemList.get(position).getHoa();
        dataitem.title.setText(h.getTenHoa());
        loadImageFromUrl(h.getImage_Hoa(),dataitem.pic);
        dataitem.price.setText(String.valueOf(mItemList.get(position).getTongGia()) + "Đ");
        dataitem.word.setText(h.getMoTa().substring(0, 25)+"...");
        dataitem.quantity.setText(h.getGia()+" x "+mItemList.get(position).getSoLuong());
        return convertView;
    }

    private static class MyView{
        ImageView pic;
        TextView title, word, quantity;
        TextView price;
    }

}
