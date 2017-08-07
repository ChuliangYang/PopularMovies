package com.cl.popularmovies.database.contract;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavoriteMoviesContract {

    public static final String AUTHORITYS = "com.com.cl.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITYS);
    public static final String FAVORITE_MOVIES_PATH = "favorite_movies";

    public static class FavoriteMoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUM_POSTER_PATH = "poster_path";
        public static final String COLUM_ORIGINAL_TITLE = "original_title";
        public static final String COLUM_RELEASE_DATA = "release_date";
        public static final String COLUM_VOTE_AVERAGE = "vote_average";
        public static final String COLUM_OVER_VIEW = "overview";
    }

}
