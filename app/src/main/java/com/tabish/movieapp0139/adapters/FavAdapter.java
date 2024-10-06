package com.tabish.movieapp0139.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabish.movieapp0139.R;
import com.tabish.movieapp0139.db.entity.Favourite;
import com.tabish.movieapp0139.home.FavouriteListActivity;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder>{


    // 1- Variable
    private Context context;
    private ArrayList<Favourite> favouriteList;
    private FavouriteListActivity favouriteListActivity;

    // 2- ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView movie_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.fav_title);
            this.movie_id = itemView.findViewById(R.id.fav_movie_id);
        }
    }

    public FavAdapter(Context context, ArrayList<Favourite> favouriteList, FavouriteListActivity favouriteListActivity) {
        this.context = context;
        this.favouriteList = favouriteList;
        this.favouriteListActivity = favouriteListActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favourite_item,parent,false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int positions) {
        final Favourite favourite = favouriteList.get(positions);

        holder.title.setText(Favourite.getTitle());
        holder.movie_id.setText(Favourite.getMovie_id());

//        holder.itemView.setOnClickListener(view -> {
//            favouriteListActivity.addAndEditFavourites(true,favourite,positions);
//        });
    }


    @Override
    public int getItemCount() {
        return favouriteList.size();
    }
}
