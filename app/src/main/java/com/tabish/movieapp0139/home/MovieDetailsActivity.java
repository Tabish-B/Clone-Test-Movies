package com.tabish.movieapp0139.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.tabish.movieapp0139.R;
import com.tabish.movieapp0139.adapters.CastRecyclerView;

import com.tabish.movieapp0139.db.DBHelper;
import com.tabish.movieapp0139.db.entity.Favourite;
import com.tabish.movieapp0139.models.CastModel;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.models.VideoModel;
import com.tabish.movieapp0139.viewmodels.DetailsViewModel;

import java.util.List;
import java.util.Objects;

public class MovieDetailsActivity extends AppCompatActivity {

    // Widgets
    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails, voteDetails;
    private RatingBar ratingBarDetails;
    private FloatingActionButton addTOFav;
    private TextView language;
    private TextView releaseDate;
    private int flag_heart = 0;
    private Boolean flag_thumb = true;
    private LinearLayout trailer;

    //ViewModel
    private DetailsViewModel detailsViewModel;

    //Recycler view of cast
    private RecyclerView castRecyclerView;
    //adapter of cast recycler
    private CastRecyclerView castRecyclerAdapter;
    private YouTubePlayerView youTubePlayerView;
    private String key_of_trailer;

    //
    DBHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_moviedetails);
        //////////////UI status bar config/////////////////
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getColor(R.color.seaGreen));
        }
        //////////////////////////////////////////////////

        //binding details section
        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);
        language = findViewById(R.id.language);
        releaseDate = findViewById(R.id.release_date);
        addTOFav = findViewById(R.id.add_to_favourite);
        castRecyclerView = findViewById(R.id.castRecyclerView);
        voteDetails = findViewById(R.id.vote_count_textview);


        //getting view-model here.
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);


        addTOFav.setOnClickListener(v -> {

            ////database addition ///
            dbHelper = new DBHelper(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (flag_heart == 0) {
                SharedPreferences.Editor editor = getSharedPreferences("com.tabish.movieapp0139.home.MovieDetails",MODE_PRIVATE).edit();
                editor.putBoolean("Favourite added", true);
                editor.commit();
                saveFavorite();
//                Intent i = new Intent(this, FavouriteListActivity.class);
//                i.putExtra("title",getTitle());
//                startActivity(i);
                Toast.makeText(this,"Added it",Toast.LENGTH_SHORT).show();
                dbHelper.insertFavouriteData(titleDetails.getText().toString(),24);
                Snackbar.make(addTOFav, "Added to Favourite",Snackbar.LENGTH_SHORT).show();
                addTOFav.setImageResource(R.drawable.ic_like_filled);
                flag_heart = 1;
            } else{

                SharedPreferences.Editor editor = getSharedPreferences("com.tabish.movieapp0139.home.FavouriteListActivity",MODE_PRIVATE).edit();
                editor.putBoolean("Favourite removed", true);
                editor.commit();
              //  dbHelper.deleteFavourite();
                Snackbar.make(addTOFav, "Removed from Favourite",Snackbar.LENGTH_SHORT).show();
                addTOFav.setImageResource(R.drawable.ic_like_holow);
                flag_heart = 0;

            }
        });


        //calling search method and trailer for cast
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            searchCastApi(movieModel.getMovie_id());
            searchVideosApi(movieModel.getMovie_id());
        }

        //this method will get data from page 1 to here and set details of a particular movie
        GetDataFromIntent();
        //will get the result of cast and pass the data for recyclerview.
        ObserveAnyChange();
        //will get the key of trailer and pass the data to youtube player library to play it from youtube.
        ObserveTrailer();
        //Configuring cast recycle view like orientation etc.
        ConfigureRecycleView();

        //
        ImageView thumb = findViewById(R.id.thumbsup);
        thumb.setOnClickListener(v -> {
            if (getIntent().hasExtra("movie")) {
                MovieModel movieModel = getIntent().getParcelableExtra("movie");
                if (flag_thumb) {
                    thumb.setImageResource(R.drawable.ic_thumb);
                    String vote = Integer.toString(movieModel.getMovie_id() + 1);
                    voteDetails.setText(vote);

                    flag_thumb = false;
                } else if (!flag_thumb) {
                    thumb.setImageResource(R.drawable.ic_empty_thumb);
                    String vote = Integer.toString(movieModel.getMovie_id());
                    voteDetails.setText(vote);

                    flag_thumb = true;
                }
            }
        });
    }

    private void saveFavorite() {

        dbHelper = new DBHelper(this);
        Favourite favorite = new Favourite();

        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            favorite.setTitle(movieModel.getTitle());
            favorite.setMovie_id(movieModel.getMovie_id());
        }
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            dbHelper.insertFavouriteData(movieModel.getTitle(),movieModel.getMovie_id() );
        }
    }


    private void searchVideosApi(int movie_id){
        detailsViewModel.searchVideosApi(movie_id);
    }

    private void ObserveTrailer() {
        detailsViewModel.getVideos().observe(this, new Observer<List<VideoModel>>() {
            @Override
            public void onChanged(List<VideoModel> videoModels) {
                if(videoModels != null)
                    for(VideoModel video : videoModels) {
                        if ((Objects.equals(video.getType(), "Trailer")) &&
                                Objects.equals(video.getSite(), "YouTube")) {
                            key_of_trailer = video.getKey();
                            trailer = findViewById(R.id.trailer_area);
                            youTubePlayerView = new YouTubePlayerView(MovieDetailsActivity.this);
                            trailer.addView(youTubePlayerView);
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.cueVideo(key_of_trailer, 0);
                                }

                            });
                            break;
                        }
                    }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youTubePlayerView != null) {
            youTubePlayerView.release();
        }
    }


    private void searchCastApi(int movie_id){
        detailsViewModel.searchCastApi(movie_id);
    }

    private void ObserveAnyChange(){
        detailsViewModel.getCast().observe(this, castModels -> {
            if(castModels != null)
                for(CastModel castModel: castModels){
                    castRecyclerAdapter.setmCast(castModels);
                }
        });
    }


    //Initializing the cast recycler view and updating data in it
    private void ConfigureRecycleView(){
        castRecyclerAdapter = new CastRecyclerView();
        castRecyclerView.setAdapter(castRecyclerAdapter);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

    }


    //getting data from main and setting it in details page
    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating((movieModel.getVote_average())/2);
            language.setText(movieModel.getOriginal_language());
            releaseDate.setText((movieModel.getRelease_date()));

            String vote = Integer.toString(movieModel.getMovie_id());
            voteDetails.setText(vote);


            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                            +movieModel.getPoster_path())
                    .into(imageViewDetails);

        }
    }
}
