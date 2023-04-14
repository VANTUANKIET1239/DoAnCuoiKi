package com.example.doanbanhoa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanbanhoa.Activity.CommentActivity;
import com.example.doanbanhoa.Models.Comment;
import com.example.doanbanhoa.Models.User;
import com.example.doanbanhoa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Comment> mdatacomment;

    public CommentAdapter(Context context, List<Comment> mdatacomment) {
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
        holder.tv_content.setText(mdatacomment.get(position).getContent());
        holder.tv_date.setText(timeStamp((Long) mdatacomment.get(position).getTime()));
        holder.rb_comment.setRating(mdatacomment.get(position).getRating());
        SetUser(mdatacomment.get(position).getId_user(), holder.tv_name, holder.img_user);

    }

    @Override
    public int getItemCount() {
        return mdatacomment.size();
    }

    public  class CommentViewHolder extends  RecyclerView.ViewHolder{
        ImageView img_user;
        TextView tv_name, tv_content, tv_date;
        RatingBar rb_comment;

        public CommentViewHolder(View view){
            super(view);
            img_user = view.findViewById(R.id.img_user);
            tv_content = view.findViewById(R.id.comment_content);
            tv_name = view.findViewById(R.id.comment_username);
            tv_date = view.findViewById(R.id.comment_date);
            rb_comment = view.findViewById(R.id.comment_ratting);
        }
    }
    private  String timeStamp(long time){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }
    private void SetUser(String id_user, TextView tv_name,ImageView img_user){
        FirebaseDatabase.getInstance().getReference("Users").child(id_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User tam = snapshot.getValue(User.class);
                String ten = tam.getHoTen();
                String img = tam.getImagea();
                Picasso.get().load(img).into(img_user);
                tv_name.setText(ten);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
