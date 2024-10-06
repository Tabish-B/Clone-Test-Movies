package com.tabish.movieapp0139.utils;

import com.tabish.movieapp0139.models.CastModel;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.response.CastResponse;
import com.tabish.movieapp0139.response.MovieSearchResponse;
import com.tabish.movieapp0139.response.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // https://api.themoviedb.org/3/movie/popular?api_key=f147564bb2d1e4a607c918b1119f8ffa&language=en-US&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );

    // Search with ID
    // https://api.themoviedb.org/3/movie/550?api_key=f147564bb2d1e4a607c918b1119f8ffa

    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/324552/credits?api_key=f147564bb2d1e4a607c918b1119f8ffa&language=en-US
    //Get cast
    @GET("3/movie/{movie_id}/credits")
    Call<CastResponse> getCast(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/500/videos?api_key=f147564bb2d1e4a607c918b1119f8ffa
    //get trailer of a video
    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/discover/movie?api_key=f147564bb2d1e4a607c918b1119f8ffa&with_genres=28
    //getting category wise
    @GET("3/discover/movie")
    Call<MovieSearchResponse> getGenres(
            @Query("api_key") String key,
            @Query("with_genres") int with_genres,
            @Query("page") int page
    );


}
