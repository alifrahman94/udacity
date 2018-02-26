package com.example.alif.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.alif.movie.activity.DetailMovieActivity;
import com.example.alif.movie.model.Movies;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 1;/*
    private static final String ID_MOVIE = "id_movie";*/
    private static final String DATABASE_NAME = "moviesManager";
    private static final String TABLE_MOVIES = "tblmastermovies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_FAVORITE = "favorite";
    private static final String KEY_RELEASE_DATE = "release_date";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " " +
                "("  + KEY_ID + " INTEGER PRIMARY KEY " +
                ", " + KEY_TITLE + " TEXT" +
                "," + KEY_ORIGINAL_TITLE + " TEXT" +
                "," + KEY_ORIGINAL_LANGUAGE + " TEXT" +
                "," + KEY_OVERVIEW + " TEXT" +
                "," + KEY_POPULARITY + " TEXT" +
                "," + KEY_VOTE_AVERAGE + " TEXT" +
                "," + KEY_VOTE_COUNT + " TEXT" +
                "," + KEY_RELEASE_DATE + " TEXT" +
                "," + KEY_POSTER_PATH + " TEXT," +
                "" + KEY_FAVORITE + " TEXT ) ";
        Log.d(TAG, "onCreate: table movie create:" + CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_MOVIES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }
    public int getCountMovies() {
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='false' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.getCount();
    }



    public DatabaseHandler open(){
        Log.d("all","opened");
        SQLiteDatabase db =this.getWritableDatabase();
        return this;
    }
    public void close(){
        SQLiteDatabase db=this.getWritableDatabase();
        this.close();
    }
    public int getCountByRated() {
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='true' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }
    public void addMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, movies.getId());
        values.put(KEY_TITLE, movies.getTitle());
        values.put(KEY_POSTER_PATH, movies.getPoster_path());
        values.put(KEY_FAVORITE, "false");
        values.put(KEY_OVERVIEW, movies.getOverview());
        values.put(KEY_POPULARITY, movies.getPopularity());
        values.put(KEY_RELEASE_DATE, movies.getRelease_date());
        values.put(KEY_VOTE_AVERAGE, movies.getVote_average());
        values.put(KEY_VOTE_COUNT, movies.getVote_count());
        db.insert(TABLE_MOVIES, null, values);
       /* db.insert(TABLE_MOVIES, null, values);*/
        /*db.insertWithOnConflict(TABLE_MOVIES, null, values, CONFLICT_REPLACE);
*/
        Log.d(TAG, "onCreate: ADDED movie create:" + db);
        db.close();
    }
    public void clearMovie() {
        SQLiteDatabase db = this.getWritableDatabase();
        /*db.delete(TABLE_MOVIES,null,null);
        */
        db.execSQL("DELETE FROM "+TABLE_MOVIES);
        db.close();
    }
    public Movies getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_ID,
                        KEY_TITLE
                        ,KEY_ORIGINAL_TITLE
                        ,KEY_ORIGINAL_LANGUAGE
                        ,KEY_OVERVIEW
                        ,KEY_POPULARITY
                        ,KEY_VOTE_AVERAGE
                        ,KEY_VOTE_COUNT
                        ,KEY_RELEASE_DATE
                        ,KEY_POSTER_PATH,
                        KEY_FAVORITE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
        return movies;
    }
    public List<Movies> getAllMovies() {
        List<Movies> movieList = new ArrayList<Movies>();
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='false' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        return movieList;
    }
    public ArrayList<Movies> getAllRated() {
        ArrayList<Movies> movieList = new ArrayList<>();
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='true' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        return movieList;
    }
    public ArrayList<Movies> getAllListRated() {
        ArrayList<Movies> movieList = new ArrayList<>();
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='true' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        return movieList;
    }
    public ArrayList<Movies> getAllListMovies() {
        ArrayList<Movies> movieList = new ArrayList<>();
        String selectQuery = "select id,title,poster_path,favorite,overview,popularity,release_date,vote_average,vote_count FROM " + TABLE_MOVIES +" where favorite='false' order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getString(6), cursor.getDouble(7), cursor.getInt(8));
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        return movieList;
    }
    public int updateMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, movies.getId());
        values.put(KEY_TITLE, movies.getTitle());
        values.put(KEY_POSTER_PATH, movies.getPoster_path());
        values.put(KEY_FAVORITE, "true");
        return db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(movies.getId()) });
        /*Toast.makeText(DetailMovieActivity.this,"Hello Javatpoint", Toast.LENGTH_SHORT).show();*/
    }
    public void deleteMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[] { String.valueOf(movies.getId()) });
        db.close();
    }

}