package com.tabish.movieapp0139.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tabish.movieapp0139.models.CastModel;
import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.repositories.CastRepository;

import java.util.List;

public class DetailsViewModel extends ViewModel {

    private CastRepository castRepository;

    //Constructor
    public DetailsViewModel() {

        castRepository = CastRepository.getInstance();
    }

    public LiveData<List<CastModel>> getCast(){
        return castRepository.getCast();
    }

    public LiveData<List<VideoModel>> getVideos() {return castRepository.getVideos();}

    //calling viewModel
    public void searchCastApi(int movie_id){
        castRepository.searchCastApi(movie_id);
    }

    //calling method
    public void searchVideosApi(int movie_id){
        castRepository.searchVideosApi(movie_id);
    }

}
