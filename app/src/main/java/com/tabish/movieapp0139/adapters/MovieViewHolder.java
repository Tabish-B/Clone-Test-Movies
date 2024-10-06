package com.tabish.movieapp0139.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tabish.movieapp0139.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    // Widgets
    TextView title, duration;
    ImageView imageView;
    RatingBar ratingBar;
    // Click Listener
    OnMovieListener onMovieListener;




    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);


        this.onMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        title = itemView.findViewById(R.id.title_movie_card);
        title.setSelected(true);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
