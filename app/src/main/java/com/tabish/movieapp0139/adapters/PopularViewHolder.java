package com.tabish.movieapp0139.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabish.movieapp0139.R;


public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    //TextView vote;
    ImageView imageView_pop;
    RatingBar ratingBar_pop;
    OnMovieListener listener;

    public PopularViewHolder(@NonNull View itemView, OnMovieListener listener) {
        super(itemView);

        this.listener = listener;
        //vote = itemView.findViewById(R.id.vote_count_textview);
        imageView_pop = itemView.findViewById(R.id.movie_img_popualar);
        ratingBar_pop = itemView.findViewById(R.id.rating_bar_pop);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    }
}
