package com.tabish.movieapp0139.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tabish.movieapp0139.R;
import com.tabish.movieapp0139.adapters.FavAdapter;
import com.tabish.movieapp0139.db.DBHelper;
import com.tabish.movieapp0139.db.entity.Favourite;
import com.tabish.movieapp0139.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteListActivity extends AppCompatActivity {

    //variables
    private RecyclerView fav_recycler;
    private ArrayList<Favourite> favouriteArrayList = new ArrayList<>();
    private ImageButton back_from_favourite;
    private DBHelper db;
    private FavAdapter favAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        //bind
        back_from_favourite = findViewById(R.id.back_from_favourite);
        fav_recycler = findViewById(R.id.fav_recycler);

        back_from_favourite.setOnClickListener(v -> {
            onBackPressed();
        });

        db = new DBHelper(this);

        // Contacts List
        favouriteArrayList.addAll(db.getAllFavourites());

        favAdapter = new FavAdapter(this, favouriteArrayList,FavouriteListActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        fav_recycler.setLayoutManager(layoutManager);
        fav_recycler.setItemAnimator(new DefaultItemAnimator());
        fav_recycler.setAdapter(favAdapter);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}