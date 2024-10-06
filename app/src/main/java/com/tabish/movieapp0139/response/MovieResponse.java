package com.tabish.movieapp0139.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.tabish.movieapp0139.models.MovieModel;

// This class is for single movie request
public class MovieResponse {
    //Finding the Movie Object

    @SerializedName("results")
    @Expose
    private MovieModel movie;


    public MovieModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
