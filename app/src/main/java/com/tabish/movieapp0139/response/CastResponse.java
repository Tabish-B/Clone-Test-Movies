package com.tabish.movieapp0139.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tabish.movieapp0139.models.CastModel;

import java.util.List;

public class CastResponse {

    @SerializedName("id")
    @Expose()
    private int movie_id;

    @SerializedName("cast")
    @Expose()
    private List<CastModel> cast;


    public int getMovie_id() {
        return movie_id;
    }

    public List<CastModel> getCast() {
        return cast;
    }


    @Override
    public String toString() {
        return "CastResponse{" +
                "movie_id=" + movie_id +
                ", cast=" + cast +
                '}';
    }
}
