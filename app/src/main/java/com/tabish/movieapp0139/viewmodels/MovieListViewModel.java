package com.tabish.movieapp0139.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {


    // this class is used for view model
    private MovieRepository movieRepository;


    // Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();

    }

    //getters of live data
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }

    public LiveData<List<MovieModel>> getGenres() {return movieRepository.getGenres();}



    //Calling method in view-model
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.serachMovieApi(query, pageNumber);
    }

    // Calling method in view-model
    public void searchMoviePop(int pageNumber){
        movieRepository.serachMoviePop( pageNumber);
    }

    //Calling method in view-model
    public void searchGenresApi(int with_genres, int pageNumber){
        movieRepository.searchGenresApi(with_genres, pageNumber);
    }



    //pagging method
    public void searchNextpage(){
        movieRepository.searchNextPage();
    }


}
