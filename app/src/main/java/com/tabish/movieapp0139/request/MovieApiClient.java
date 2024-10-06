package com.tabish.movieapp0139.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.networksExec.AppExecutors;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.response.MovieSearchResponse;
import com.tabish.movieapp0139.response.VideoResponse;
import com.tabish.movieapp0139.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies;
    private MutableLiveData<List<MovieModel>> mMoviesPop;
    private MutableLiveData<List<MovieModel>> mGenres;

    private static MovieApiClient instance;

    // making Global RUNNABLE
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;
    private RetrieveGenresRunnable retrieveGenresRunnable;


    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
        mGenres = new MutableLiveData<>();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }


    public LiveData<List<MovieModel>> getPop() {
        return mMoviesPop;
    }


    public LiveData<List<MovieModel>> getGenres(){
        return mGenres;
    }



    //Method of searching api particular movie

    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);


    }


    public void searchMoviesPop(int pageNumber) {

        if (retrieveMoviesRunnablePop != null) {
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);


    }


    public void searchGenresApi(int with_genres, int pageNumber) {

        if (retrieveGenresRunnable != null) {
            retrieveGenresRunnable = null;
        }

        retrieveGenresRunnable = new RetrieveGenresRunnable(with_genres,pageNumber);

        final Future myHandler3 = AppExecutors.getInstance().networkIO().submit(retrieveGenresRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
               //cancelling retrofit call
                myHandler3.cancel(true);
            }
        },2000,TimeUnit.MILLISECONDS);

    }

    /////////////////////////////////////////////////////////////////////////////////////

    // Retrieving data from RestAPI by runnable class
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }
        // Search Method/ query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

    private class RetrieveMoviesRunnablePop implements Runnable {

        private int pageNumber;
        boolean cancelRequest;


        public RetrieveMoviesRunnablePop(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the response objects

            try {
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
                        mMoviesPop.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                } else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }
        }

        // Search Method/ query
        private Call<MovieSearchResponse> getPop(int pageNumber) {
            return Service.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }
        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }



    private class RetrieveGenresRunnable implements Runnable{

        //query,path etc
        private int with_genres;
        private int pageNumber;
        boolean cancelRequest;

      //constructor of runnable
        public RetrieveGenresRunnable(int with_genres, int pageNumber) {
            this.with_genres = with_genres;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response3 = getGenres(with_genres, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response3.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response3.body()).getMovies());
                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        mGenres.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mGenres.getValue();
                        currentMovies.addAll(list);
                        mGenres.postValue(currentMovies);
                    }

                } else {
                    String error = response3.errorBody().string();
                    Log.v("Tagy", "Erroryy " + error);
                    mGenres.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//run end


        private Call<MovieSearchResponse> getGenres(int with_genres, int pageNumber) {
            return Service.getMovieApi().getGenres(
                    Credentials.API_KEY,
                    with_genres,
                    pageNumber
            );

        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }
}
