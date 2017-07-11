package com.cl.popularmovies.net;

import android.content.Context;
import android.util.Log;

import com.cl.popularmovies.Interface.OnReceiveMoviesList;
import com.cl.popularmovies.Interface.OnResponseListener;
import com.cl.popularmovies.bean.MovieListResponseBean;
import com.cl.popularmovies.data.Constant;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;


public class RequestMovieList implements OnResponseListener {


    private Context context;
    private SortType sortType;
    private OnReceiveMoviesList OnReceiveMoviesList;
    private Boolean showProgressBar = true;

    public RequestMovieList(Context context, SortType sortType, OnReceiveMoviesList OnReceiveMoviesList) {
        this.context = context;
        this.sortType = sortType;
        this.OnReceiveMoviesList = OnReceiveMoviesList;
    }

    public void request(int page) {
        switch (sortType) {
            case SORT_POPULAR:
                try {
                    new RequestServerTask(context, this).showProgressBar(showProgressBar).execute(new URL(Constant.REQUEST_POP_LIST_URL + "&&page=" + page));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Log.e("url", Constant.REQUEST_POP_LIST_URL + "&&" + page);
                break;
            case SORT_RATED:
                try {
                    new RequestServerTask(context, this).showProgressBar(showProgressBar).execute(new URL(Constant.REQUEST_RATED_LIST_URL + "&&page=" + page));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Log.e("url", Constant.REQUEST_RATED_LIST_URL + "&&" + page);
                break;

        }
    }

    @Override
    public void OnSuccess(String response) {
        Gson gson = new Gson();
        MovieListResponseBean movieListResponseBean = gson.fromJson(response, MovieListResponseBean.class);
        OnReceiveMoviesList.onSuccess(movieListResponseBean.getResults());
    }

    @Override
    public void OnFailed(String response) {
        OnReceiveMoviesList.onFailed(null);
    }

    public RequestMovieList showProgressBar(Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return this;
    }

    public enum SortType {
        SORT_POPULAR, SORT_RATED
    }

}
