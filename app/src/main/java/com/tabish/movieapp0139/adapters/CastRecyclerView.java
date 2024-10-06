package com.tabish.movieapp0139.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tabish.movieapp0139.R;
import com.tabish.movieapp0139.models.CastModel;


import java.util.List;

public class CastRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<CastModel> mCast;

    public CastRecyclerView() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View view = null;
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item,
                parent, false);
        return new CastViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // ImageView: Using Glide Library
        Glide.with(holder.itemView.getContext())
                .load( "https://image.tmdb.org/t/p/w500/"
                        +mCast.get(position).getProfile_path())
                .into(((CastViewHolder)holder).profile_image);

        ((CastViewHolder)holder).name.setText((mCast.get(position).getName()));
        ((CastViewHolder)holder).character.setText((mCast.get(position).getCharacter()));
        String gen;
        if((mCast).get(position).getGender()== 1){
            gen = "Female";
        }else if((mCast).get(position).getGender() == 2){
            gen = "Male";
        }else{
            gen = "N/A";
        }
       ((CastViewHolder)holder).gender.setText(gen);

    }


    @Override
    public int getItemCount() {
        if (mCast != null){
            return mCast.size();
        }
        return 0;
    }

    public void setmCast(List<CastModel> mCast) {
        this.mCast = mCast;
        notifyDataSetChanged();
    }

}
