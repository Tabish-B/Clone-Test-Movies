package com.tabish.movieapp0139.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tabish.movieapp0139.db.entity.Favourite;
import com.tabish.movieapp0139.models.MovieModel;
import com.tabish.movieapp0139.models.ProfileModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static String DB_NAME = "user.db";
    private static int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    private static String createTableQuery = "Create table users(userName TEXT primary key" +
            ",password TEXT " +
            ",image BLOB)";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableQuery);
        Toast.makeText(context.getApplicationContext(), "You are added successfully", Toast.LENGTH_SHORT).show();

        db.execSQL(Favourite.CREATE_TABLE);
        Toast.makeText(context.getApplicationContext(), "Favourite success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("DROP TABLE IF EXISTS " + Favourite.TABLE_NAME);

        onCreate(db);
    }

    //Inserting data of registration in database
    public Boolean insertData(ProfileModel profileModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = profileModel.getProfileImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", profileModel.getUsername());
        contentValues.put("password", profileModel.getPassword());
        contentValues.put("image", imageInBytes);

        long checkIfQueryRun = db.insert("users", null, contentValues);
        if (checkIfQueryRun != -1) {
            Toast.makeText(context.getApplicationContext(), "table added successfully", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        } else {
            Toast.makeText(context.getApplicationContext(), "failed to add", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    // Insert Data of Favourite into Database

    public long insertFavouriteData(String title, int movie_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Favourite.COLUMN_TITLE, title);
        values.put(Favourite.COLUMN_MOVIEID, movie_id);
        long id = db.insert(Favourite.TABLE_NAME, null, values);
        db.close();

        return id;
    }




    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

//getting user from db
    public Cursor getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users", null);
        return  cursor;
    }

    // Getting Favourite from DataBase
    public Favourite getFavourite(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Favourite.TABLE_NAME,
                new String[]{
                        Favourite.COLUMN_ID,
                        Favourite.COLUMN_TITLE,
                        Favourite.COLUMN_MOVIEID},
                Favourite.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);

        if (cursor !=null)
            cursor.moveToFirst();

        Favourite contact = new Favourite(
                cursor.getString(cursor.getColumnIndexOrThrow(Favourite.COLUMN_TITLE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Favourite.COLUMN_MOVIEID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Favourite.COLUMN_ID))
        );
        cursor.close();
        return contact;

    }

    // Getting all Favourites
    public ArrayList<Favourite> getAllFavourites(){
        ArrayList<Favourite> contacts = new ArrayList<>();


        String selectQuery = "SELECT * FROM " +Favourite.TABLE_NAME + " ORDER BY "+
                Favourite.COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Favourite favourite = new Favourite();
                favourite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Favourite.COLUMN_ID)));
                favourite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Favourite.COLUMN_TITLE)));
                favourite.setMovie_id(cursor.getInt(cursor.getColumnIndexOrThrow(Favourite.COLUMN_MOVIEID)));

                contacts.add(favourite);

            }while(cursor.moveToNext());
        }

        db.close();

        return contacts;
    }


    public void deleteFavourite(Favourite favourite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Favourite.TABLE_NAME, Favourite.COLUMN_ID + " = ?",
                new String[]{String.valueOf(Favourite.getId())}
        );
        db.close();
    }


    public int updateFavourite(Favourite favourite){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Favourite.COLUMN_TITLE, Favourite.getTitle());
        values.put(Favourite.COLUMN_MOVIEID, Favourite.getMovie_id());

        return db.update(Favourite.TABLE_NAME, values,Favourite.COLUMN_ID+ " = ? ",
                new String[]{String.valueOf(Favourite.getId())});

    }


    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public void open(){
        Log.i("Fav", "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i("Fav", "Database Closed");
        dbhandler.close();
    }










    

}
