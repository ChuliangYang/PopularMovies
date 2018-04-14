package com.cl.popularmovies.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.cl.popularmovies.Interface.OnReceiveReviewsData;
import com.cl.popularmovies.Interface.OnReceiveTrailersData;
import com.cl.popularmovies.helper.WidgetHelper;

import java.util.List;


public class RequestTrailerAndReviewList implements OnReceiveTrailersData, OnReceiveReviewsData {


    private Context context;
    private String movieId;
    private OnReceiveTrailersData onReceiveTrailersData;
    private OnReceiveReviewsData onReceiveReviewsData;
    private Boolean showProgressBar = true;
    private ProgressDialog progressDialog;
    private RequestTrailerList requestTrailerList;
    private RequestReviewsList requestReviewsList;
    private boolean trailersResponse = false;
    private boolean reviewsResponse = false;


    public RequestTrailerAndReviewList(Context context, String movieId, OnReceiveTrailersData onReceiveTrailersData, OnReceiveReviewsData onReceiveReviewsData) {
        this.context = context;
        this.movieId = movieId;
        this.onReceiveTrailersData = onReceiveTrailersData;
        this.onReceiveReviewsData = onReceiveReviewsData;
    }

    public void request() {
        try {
            progressDialog = WidgetHelper.createProgressDialog(context);
            progressDialog.setOnCancelListener(dialog -> {
                requestTrailerList.cancelTask();
                requestReviewsList.cancelTask();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            });
            if (showProgressBar) {
                progressDialog.show();
            }
            requestTrailerList = new RequestTrailerList(context, movieId, this).showProgressBar(false);
            requestTrailerList.request();
            requestReviewsList = new RequestReviewsList(context, movieId, this).showProgressBar(false);
            requestReviewsList.request(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//
    }

//    @Override
//    public void OnSuccess(String response) {
//        Gson gson = new Gson();
//        TrailersResponseBean trailersListResponseBean = gson.fromJson(response, TrailersResponseBean.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            onReceiveTrailersData.onReceiveTrailersSuccess(trailersListResponseBean.getResults().stream()
//                    .filter(t -> t.getType().equals("Trailer")).collect(Collectors.toList()));
//        } else {
//            for (int i = 0; i < trailersListResponseBean.getResults().size(); i++) {
//                if (!"Trailer".equals(trailersListResponseBean.getResults().get(i).getType())) {
//                    trailersListResponseBean.getResults().remove(i);
//                    i-=1;
//                }
//            }
//            onReceiveTrailersData.onReceiveTrailersSuccess(trailersListResponseBean.getResults());
//        }
//    }
//
//    @Override
//    public void OnFailed(String response) {
//        onReceiveTrailersData.onReceiveTrailersFailed(null);
//    }

    public RequestTrailerAndReviewList showProgressBar(Boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return this;
    }


    @Override
    public void onReceiveReviewsSuccess(List List) {
        reviewsResponse = true;
        onReceiveReviewsData.onReceiveReviewsSuccess(List);
        ifAllFinishDismissProgressbar();
    }

    @Override
    public void onReceiveReviewsFailed(Object failed) {
        reviewsResponse = true;
        onReceiveReviewsData.onReceiveReviewsFailed(failed);
        ifAllFinishDismissProgressbar();
    }

    @Override
    public void onReceiveTrailersSuccess(List List) {
        trailersResponse = true;
        onReceiveTrailersData.onReceiveTrailersSuccess(List);
        ifAllFinishDismissProgressbar();
    }

    @Override
    public void onReceiveTrailersFailed(Object failed) {
        trailersResponse = true;
        onReceiveTrailersData.onReceiveTrailersFailed(failed);
        ifAllFinishDismissProgressbar();

    }

    public synchronized void ifAllFinishDismissProgressbar() {
        if (trailersResponse && reviewsResponse && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
