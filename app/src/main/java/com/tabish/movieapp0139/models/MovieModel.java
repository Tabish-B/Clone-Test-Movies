package com.tabish.movieapp0139.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {
    // Model Class for our movies
    private String title;
    private String poster_path;
    private String release_date;
    private float vote_average;
    private int vote_count;
    private int isLiked;

    //serialized the name coz api has 'overview'
    @SerializedName("overview")
    private String movie_overview;
    @SerializedName("id")
    private int movie_id;
    private String original_language;

    //Constructor


    public MovieModel() {
    }

    public MovieModel(String poster_path, float vote_average) {
        this.poster_path = poster_path;
        this.vote_average = vote_average;
    }

    public MovieModel(String title, String poster_path, String release_date
            , int movie_id, float vote_average, int vote_count
            , String movie_overview, String original_language) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.movie_id = movie_id;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.movie_overview = movie_overview;
        this.original_language = original_language;

    }

    public MovieModel(String title, String poster_path, String release_date, float vote_average,
                      int vote_count, int isLiked, String movie_overview, int movie_id,
                      String original_language) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.isLiked = isLiked;
        this.movie_overview = movie_overview;
        this.movie_id = movie_id;
        this.original_language = original_language;
    }

    protected MovieModel(Parcel in){
        title = in.readString();
        vote_count = in.readInt();
        poster_path = in.readString();
        release_date = in.readString();
        movie_id = in.readInt();
        vote_average = in.readFloat();
        movie_overview = in.readString();
        original_language = in.readString();
        isLiked = in.readInt();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    // Getters
    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getIsLiked() {
        return isLiked;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(vote_count);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(movie_id);
        parcel.writeFloat(vote_average);
        parcel.writeString(movie_overview);
        parcel.writeString(original_language);
        parcel.writeInt(isLiked);

    }


    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", isLiked=" + isLiked +
                ", movie_overview='" + movie_overview + '\'' +
                ", movie_id=" + movie_id +
                ", original_language='" + original_language + '\'' +
                '}';
    }
}
