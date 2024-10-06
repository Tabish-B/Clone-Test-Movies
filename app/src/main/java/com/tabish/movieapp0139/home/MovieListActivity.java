package com.tabish.movieapp0139.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tabish.movieapp0139.R;
import com.tabish.movieapp0139.adapters.MovieRecyclerView;
import com.tabish.movieapp0139.adapters.OnMovieListener;
import com.tabish.movieapp0139.db.DBHelper;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.home.profile.RegisterActivity;
import com.tabish.movieapp0139.viewmodels.MovieListViewModel;


import java.util.List;
import java.util.Objects;


public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    // RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    private CardView cardView;
    private Button action,adventure,romance,horror, crime, comedy;
    private ImageView mic;

    // ViewModel
    private MovieListViewModel movieListViewModel;
    boolean isPopular = true;   // True for popular
    boolean isCategory = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_movielist);

        ///// Status bar setting///////
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        //implementing and getting the viewModel
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        ImageView img = findViewById(R.id.profile_pic);
        TextView uname = findViewById(R.id.uName);
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No entereies", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                //
                uname.setText(""+cursor.getString(0));
                byte[] imageByte = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                img.setImageBitmap(bitmap);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);

        action = findViewById(R.id.action);
        adventure = findViewById(R.id.adventure);
        horror = findViewById(R.id.horror);
        comedy = findViewById(R.id.comedy);
        romance = findViewById(R.id.romance);
        crime = findViewById(R.id.crime);

       action.setOnClickListener(v -> searchGenresApi(28,1));
       adventure.setOnClickListener(v -> searchGenresApi(12,1));
       horror.setOnClickListener(v -> searchGenresApi(27,1));
       crime.setOnClickListener(v -> searchGenresApi(80,1));
       comedy.setOnClickListener(v -> searchGenresApi(35,1));
       romance.setOnClickListener(v -> searchGenresApi(10749,1));


       mic = findViewById(R.id.mic);
       mic.setOnClickListener(v -> {
           Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
           intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
           intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start to speak");
           startActivityForResult(intent, 7);
       });

        // SearchView
        SetupSearchView();
        //configuring recycleview
        ConfigureRecyclerView();
        //for normal searches
        ObserveAnyChange();
        //for popular movies searches
        ObservePopular();
        //for genre searches
        ObserveCategory();

        movieListViewModel.searchMoviePop(1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 7 && requestCode == RESULT_OK){
            SearchView searchView = findViewById(R.id.search_view);
            searchView.setQuery(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0),false);
        }
    }

    private void searchGenresApi(int with_genres, int pageNumber){
        movieListViewModel.searchGenresApi(with_genres,pageNumber);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
        //true
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"favourite selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MovieListActivity.this, FavouriteListActivity.class);
                startActivity(intent);
                return true;


            case R.id.Mail:
                Toast.makeText(this,"Mailing Legend Tabish.\nPlz wiat...",Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("https://mail.google.com/mail/");
                Intent intentMail = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentMail);
                return true;

            case R.id.logout_menu:
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(MovieListActivity.this,R.style.MaterialAlertDialog_OK_color );
                builder.setMessage("Do you want to exit ?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Intent intentOut = new Intent(MovieListActivity.this, RegisterActivity.class);
                    startActivity(intentOut);
                    DBHelper dbHelper = new DBHelper(this);
                    dbHelper.close();
                    this.finish();
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



    private void ObservePopular(){
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        // Get the data in log
                        movieRecyclerAdapter.setmMovies(movieModels);

                    }
                }

            }
        });
    }

    private void ObserveCategory() {
        movieListViewModel.getGenres().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                ///observe category
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        Log.v("Tag", "onChanged" + movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }

        });
    }

    // Observing any data change
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        // Get the data in log
                        movieRecyclerAdapter.setmMovies(movieModels);

                    }
                }

            }
        });
    }

    // Intializing recyclerView & adding data to it
    private void ConfigureRecyclerView() {

        movieRecyclerAdapter = new MovieRecyclerView( this);
        recyclerView.setAdapter(movieRecyclerAdapter);

        int orientation = this.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        } else {
            // code for landscape mode
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));

        }


        // RecyclerView Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    //display the next search results on the next page of api
                    movieListViewModel.searchNextpage();

                }
            }
        });

    }

    @Override
    public void onMovieClick(int position) {
        //when any movie is clicked.
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        Log.v("Tag", "clicked " + movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }


    // Get data from search view & query the api to get the results
    private void SetupSearchView() {

        final SearchView searchView = findViewById(R.id.search_view);

        // Get data from search view
            searchView.setOnSearchClickListener(v -> isPopular = false);
            searchView.setOnCloseListener(() -> false);


        // Make search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(query, 1);
                    cardView = findViewById(R.id.card_to_disappear);
                    cardView.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //go back to the popular movie if i set search view to null
                if(newText.equals("")){
                    this.onQueryTextSubmit("");
                  movieListViewModel.searchMoviePop(1);
                    cardView.postDelayed(new Runnable() {
                        public void run() {
                            cardView.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
                return false;
            }
        });

    }

}

