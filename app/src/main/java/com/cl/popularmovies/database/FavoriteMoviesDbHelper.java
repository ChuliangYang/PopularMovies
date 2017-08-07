package com.cl.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cl.popularmovies.database.contract.FavoriteMoviesContract.FavoriteMoviesEntry;


public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;//版本，如果数据库结构发生变动需要增加，如果版本号变动，需要复写升级或降级函数
    public static final String DATABASE_NAME = "FavoriteMoives.db";//数据库名称
    private static final String SQL_DELETE_ENTRIES =//sql删除表语句
            "DROP TABLE IF EXISTS " + FavoriteMoviesEntry.TABLE_NAME;
    public static String SQL_CREATE_ENTRIES = "CREATE TABLE " + FavoriteMoviesEntry.TABLE_NAME + " (" +
            FavoriteMoviesEntry._ID + " TEXT PRIMARY KEY NOT NULL," +
            FavoriteMoviesEntry.COLUM_POSTER_PATH + " TEXT," +
            FavoriteMoviesEntry.COLUM_ORIGINAL_TITLE + " TEXT," +
            FavoriteMoviesEntry.COLUM_OVER_VIEW + " TEXT," +
            FavoriteMoviesEntry.COLUM_RELEASE_DATA + " TEXT," +
            FavoriteMoviesEntry.COLUM_VOTE_AVERAGE + " TEXT)";


    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
