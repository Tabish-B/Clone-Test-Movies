package com.tabish.movieapp0139.db.entity;

public class Favourite {

    // 1- Constants for Database
    public static final String TABLE_NAME = "favourite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MOVIEID = "movie_id";
    public static final String COLUMN_TITLE = "title";


    // 2- Variables
    private static String title;
    private static int id;
    private static int movie_id;

    // 3- Constructors
    public Favourite(){

    }

    public Favourite(String title, int id, int movie_id) {
        this.title = title;
        this.id = id;
        this.movie_id = movie_id;
    }

    //4- Getters and Setters
    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    // 5- Creating the Table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_MOVIEID + " INTEGER"
                    + ")";

}
