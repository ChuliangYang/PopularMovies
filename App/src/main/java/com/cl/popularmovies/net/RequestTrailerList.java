package com.cl.popularmovies.net;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.cl.popularmovies.Interface.OnReceiveTrailersData;
import com.cl.popularmovies.Interface.OnResponseListener;
import com.cl.popularmovies.bean.TrailersResponseBean;
import com.cl.popularmovies.constant.Network;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;


public class RequestTrailerList implements OnResponseListener {


    private Context context;
    private String movieId;
    private OnReceiveTrailersData onReceiveTrailersData;
    private Boolean showProgressBar = true;
    private RequestServerTask requestServerTask;

    public RequestTrailerList(Context context, String movieId, OnReceiveTrailersData onReceiveTrailersData) {
        this.context = context;
        this.movieId = movieId;
        this.onReceiveTrailersData = onReceiveTrailersData;
    }

    public void request() {
        try {
            requestServerTask = new RequestServerTask(context, this).showProgressBar(showProgressBar);
            requestServerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new URL(String.format(Network.REQUEST_TRAILERS, movieId)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//
    }

    @Override
    public void OnSuccess(String response) {
        Gson gson = new Gson();
        TrailersResponseBean trailersListResponseBean = gson.fromJson(response, TrailersResponseBean.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            onReceiveTrailersData.onReceiveTrailersSuccess(trailersListResponseBean.getResults().stream()
                    .filter(t -> t.getType().equals("Trailer")).collect(Collectors.toList()));
        } else {
            for (int i = 0; i < trailersListResponseBean.getResults().size(); i++) {
                if (!"Trailer".equals(trailersListResponseBean.getResults().get(i).getType())) {
                    trailersListResponseBean.getResults().remove(i);
                    i -= 1;
                }
            }
            onReceiveTrailersData.onReceiveTrailersSuccess(trailersListResponseBean.getResults());
        }
    }

    @Override
    public void OnFailed(String response) {
        onReceiveTrailersData.onReceiveTrailersFailed(null);
    }

    public RequestTrailerList showProgressBar(Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return this;
    }

    public void cancelTask() {
        requestServerTask.cancel();
    }

}
