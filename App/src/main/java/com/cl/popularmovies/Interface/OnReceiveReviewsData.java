package com.cl.popularmovies.Interface;

import java.util.List;


public interface OnReceiveReviewsData {
    void onReceiveReviewsSuccess(List List);

    void onReceiveReviewsFailed(Object failed);
}
