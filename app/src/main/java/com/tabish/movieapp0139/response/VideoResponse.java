package com.tabish.movieapp0139.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tabish.movieapp0139.models.VideoModel;

import java.util.List;

public class VideoResponse {

    @SerializedName("id")
    @Expose()
    private int movie_id;


    @SerializedName("results")
    @Expose()
    private List<VideoModel> videos;

    public int getMovie_id() {
        return movie_id;
    }

    public List<VideoModel> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "VideoResponse{" +
                "movie_id=" + movie_id +
                ", videos=" + videos +
                '}';
    }

}
