package com.cl.popularmovies.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cl.popularmovies.database.FavoriteMoviesDbHelper;
import com.cl.popularmovies.database.contract.FavoriteMoviesContract;
import com.cl.popularmovies.database.contract.FavoriteMoviesContract.FavoriteMoviesEntry;

public class FavoriteMoviesProvider extends ContentProvider {
    public static final int MOVIES = 100;
    public static final int SINGLE_MOVIE = 101;
    private static UriMatcher uriMatcher = buildUriMatcher();
    private FavoriteMoviesDbHelper favoriteMoviesDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITYS, FavoriteMoviesContract.FAVORITE_MOVIES_PATH, MOVIES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITYS, FavoriteMoviesContract.FAVORITE_MOVIES_PATH + "/#", SINGLE_MOVIE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        favoriteMoviesDbHelper = new FavoriteMoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor query;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                SQLiteDatabase readableDatabase1 = favoriteMoviesDbHelper.getReadableDatabase();
                query = readableDatabase1.query(FavoriteMoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_MOVIE:
                SQLiteDatabase readableDatabase2 = favoriteMoviesDbHelper.getReadableDatabase();
                query = readableDatabase2.query(FavoriteMoviesEntry.TABLE_NAME, projection, "_id=?", new String[]{uri.getPathSegments().get(1)}, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri:" + uri);

        }

        query.setNotificationUri(getContext().getContentResolver(), uri);


        return query;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return "vnd.android.cursor.dir/vnd." + FavoriteMoviesContract.AUTHORITYS + "." + FavoriteMoviesEntry.TABLE_NAME;
            case SINGLE_MOVIE:
                return "vnd.android.cursor.item/vnd." + FavoriteMoviesContract.AUTHORITYS + "." + FavoriteMoviesEntry.TABLE_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri returnUri = null;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                SQLiteDatabase writableDatabase = favoriteMoviesDbHelper.getWritableDatabase();
                long insert = writableDatabase.insert(FavoriteMoviesEntry.TABLE_NAME, null, values);

                if (insert > 0) {
                    returnUri = ContentUris.withAppendedId(Uri.parse(FavoriteMoviesContract.BASE_CONTENT_URI + "/" + FavoriteMoviesContract.FAVORITE_MOVIES_PATH), insert);
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                break;
            default:
                throw new UnsupportedOperationException("unknown uri:" + uri);
        }


        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleteCount;
        switch (uriMatcher.match(uri)) {
            case SINGLE_MOVIE:
                SQLiteDatabase writableDatabase = favoriteMoviesDbHelper.getWritableDatabase();
                deleteCount = writableDatabase.delete(FavoriteMoviesEntry.TABLE_NAME, "_id=?", new String[]{uri.getPathSegments().get(1)});
                break;
            default:
                throw new UnsupportedOperationException("unknown uri:" + uri);

        }
        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case SINGLE_MOVIE:
                SQLiteDatabase writableDatabase = favoriteMoviesDbHelper.getWritableDatabase();
                updateCount = writableDatabase.update(FavoriteMoviesEntry.TABLE_NAME, values, "_id=?", new String[]{uri.getPathSegments().get(1)});

                break;
            default:
                throw new UnsupportedOperationException("unknown uri:" + uri);
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

}
