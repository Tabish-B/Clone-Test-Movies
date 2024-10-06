package com.tabish.movieapp0139.request;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tabish.movieapp0139.models.CastModel;
import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.networksExec.AppExecutors;
import com.tabish.movieapp0139.response.CastResponse;
import com.tabish.movieapp0139.response.VideoResponse;
import com.tabish.movieapp0139.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class DetailsApiClient {

    //Mutable Live Data
    private MutableLiveData<List<CastModel>> mCast;
    private MutableLiveData<List<VideoModel>> mVideos;

    private static DetailsApiClient instance;


    //Making global runnables
    private RetrieveCastRunnable retrieveCastRunnable;
    private RetrieveVideosRunnable retrieveVideosRunnable;

    public static DetailsApiClient getInstance(){
        if(instance == null){
            instance = new DetailsApiClient();
        }
        return instance;
    }

    private DetailsApiClient(){
        mCast = new MutableLiveData<>();
        mVideos = new MutableLiveData<>();
    }

    public LiveData<List<CastModel>> getCast(){
        return mCast;
    }

    public LiveData<List<VideoModel>> getVideos(){
        return mVideos;
    }


    public void  searchCastApi(int movie_id){
        if(retrieveCastRunnable != null){
         retrieveCastRunnable = null;
        }


        retrieveCastRunnable = new RetrieveCastRunnable(movie_id);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveCastRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //cancelling the retrofit call
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);

    }



    public void searchVideosApi(int movie_id) {

        if (retrieveVideosRunnable != null) {
            retrieveVideosRunnable = null;
        }

        retrieveVideosRunnable = new DetailsApiClient.RetrieveVideosRunnable(movie_id);

        final Future myHandler4 = AppExecutors.getInstance().networkIO().submit(retrieveVideosRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //cancelling retrofit call
                myHandler4.cancel(true);
            }
        },2000,TimeUnit.MILLISECONDS);

    }

/////////////////////////////////////////////////////////////////////////////////////

    //Retrieving data from api by runnable class
    private class RetrieveCastRunnable implements Runnable{

        private int movie_id;
        boolean cancelRequest;


        public RetrieveCastRunnable(int movie_id) {
            this.movie_id = movie_id;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            //object
            try{
                Response response = getCast(movie_id).execute();
                if (cancelRequest) {
                    return;
                }

                if(response.code() == 200){
                    List<CastModel> list = new ArrayList<>(((CastResponse)response.body()).getCast());
                    mCast.postValue(list);
                }
                else {
                    String error = response.errorBody().string();
                    Log.v("Tag","error" +error );
                    mCast.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mCast.postValue(null);
            }

        }
            //search cast
            private Call<CastResponse> getCast(int id){
                return Service.getMovieApi().getCast(
                        movie_id,
                        Credentials.API_KEY
                );
        }

        private void setCancelRequest(){
            Log.v("Tag", "Cancelling request");
            cancelRequest = true;
        }
    }


    private class RetrieveVideosRunnable implements Runnable{

        private int movie_id;
        boolean cancelRequest;

        //constructor of runnable

        public RetrieveVideosRunnable(int movie_id) {
            this.movie_id = movie_id;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response4 = getVideos(movie_id).execute();
                if (cancelRequest) {
                    return;
                }
                if (response4.code() == 200) {
                    List<VideoModel> list = new ArrayList<>(((VideoResponse) response4.body()).getVideos());
                    mVideos.postValue(list);
                } else {
                    String error = response4.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mVideos.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//run end

        private Call<VideoResponse> getVideos(int movie_id) {
            return Service.getMovieApi().getVideos(
                    movie_id,
                    Credentials.API_KEY
            );

        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }



}
