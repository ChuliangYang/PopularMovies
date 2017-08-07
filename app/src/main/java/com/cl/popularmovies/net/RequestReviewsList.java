package com.cl.popularmovies.net;

import android.content.Context;
import android.os.AsyncTask;

import com.cl.popularmovies.Interface.OnReceiveReviewsData;
import com.cl.popularmovies.Interface.OnResponseListener;
import com.cl.popularmovies.bean.ReviewsResponseBean;
import com.cl.popularmovies.constant.Network;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;


public class RequestReviewsList implements OnResponseListener {


    private Context context;
    private String movieId;
    private OnReceiveReviewsData onReceiveData;
    private Boolean showProgressBar = true;
    private RequestServerTask requestServerTask;

    public RequestReviewsList(Context context, String movieId, OnReceiveReviewsData onReceiveData) {
        this.context = context;
        this.movieId = movieId;
        this.onReceiveData = onReceiveData;
    }

    public void request(int page) {
        try {
            requestServerTask = new RequestServerTask(context, this).showProgressBar(showProgressBar);
            requestServerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new URL(String.format(Network.REQUEST_REVIEWS_LIST_URL, movieId, String.valueOf(page))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnSuccess(String response) {
        Gson gson = new Gson();
        ReviewsResponseBean reviewsResponseBean = gson.fromJson(response, ReviewsResponseBean.class);
        onReceiveData.onReceiveReviewsSuccess(reviewsResponseBean.getResults());
    }

    @Override
    public void OnFailed(String response) {
        onReceiveData.onReceiveReviewsFailed(null);
    }

    public RequestReviewsList showProgressBar(Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return this;
    }

    public void cancelTask() {
        requestServerTask.cancel();
    }


}
