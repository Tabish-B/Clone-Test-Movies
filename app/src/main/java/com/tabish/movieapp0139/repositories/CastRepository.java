package com.tabish.movieapp0139.repositories;

import androidx.lifecycle.LiveData;

import com.tabish.movieapp0139.models.CastModel;
import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.request.DetailsApiClient;

import java.util.List;

public class CastRepository {

    private static CastRepository instance;

    private DetailsApiClient detailsApiClient;

//vars
    private int mMovie_id;

//getting instance
    public static CastRepository getInstance(){

        if(instance == null){
            instance = new CastRepository();
        }
        return instance;
    }

//constructor
    private CastRepository(){
        detailsApiClient = detailsApiClient.getInstance();
    }

    public LiveData<List<CastModel>> getCast(){
        return detailsApiClient.getCast();
    }


   //Calling the method in repository
   public void searchCastApi(int movie_id){
        detailsApiClient.searchCastApi(movie_id);

   }

////////////////////////////////////////////////

    public LiveData<List<VideoModel>> getVideos() {
        return detailsApiClient.getVideos();
    }

    public void searchVideosApi(int movie_id){
        mMovie_id = movie_id;
        detailsApiClient.searchVideosApi(movie_id);
    }


}
