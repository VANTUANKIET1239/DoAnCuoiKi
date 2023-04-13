package com.example.doanbanhoa.Adapter;

import android.content.Context;
import android.media.Image;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanbanhoa.LayHinhAnh;
import com.example.doanbanhoa.Models.Commit;
import com.example.doanbanhoa.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Comment;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Commit> mdatacomment;

    public CommentAdapter(Context context, List<Commit> mdatacomment) {
        this.context = context;
        this.mdatacomment = mdatacomment;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_comment, parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.tv_name.setText(mdatacomment.get(position).getUser_name());
        holder.tv_content.setText(mdatacomment.get(position).getContent());
        String url = mdatacomment.get(position).getUser_img();
        Picasso.get().load(url).into(holder.img_user);
        holder.tv_date.setText(timeStamp((Long) mdatacomment.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return mdatacomment.size();
    }

    public  class CommentViewHolder extends  RecyclerView.ViewHolder{
        ImageView img_user;
        TextView tv_name, tv_content, tv_date;

        public CommentViewHolder(View view){
            super(view);
            img_user = view.findViewById(R.id.img_user);
            tv_content = view.findViewById(R.id.comment_content);
            tv_name = view.findViewById(R.id.comment_username);
            tv_date = view.findViewById(R.id.comment_date);
        }
    }
    private  String timeStamp(long time){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }
}
