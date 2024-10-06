package com.tabish.movieapp0139.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    // This class is acting as repositories

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;


    private String mQuery;
    private int mPageNumber;
    private int mWith_genres;


    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;

    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }


    ////////////////////////////////
    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    // Calling the method in repository
    public void serachMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage(){
        serachMovieApi(mQuery, mPageNumber+1);
    }
    ////////////////////////////////

    ////////////////////////////////
    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getPop();
    }

    // Calling the method in repository
    public void serachMoviePop(int pageNumber){

        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop( pageNumber);
    }
    ////////////////////////////////


    ////////////////////////////////
    public LiveData<List<MovieModel>> getGenres() {
        return movieApiClient.getGenres();
    }

    public void searchGenresApi(int with_genres,int pageNumber){
        mWith_genres = with_genres;
        mPageNumber = pageNumber;
        movieApiClient.searchGenresApi(with_genres, pageNumber);
    }
    ////////////////////////////////




}




