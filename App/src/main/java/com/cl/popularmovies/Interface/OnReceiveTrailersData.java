package com.cl.popularmovies.Interface;

import java.util.List;


public interface OnReceiveTrailersData {
    void onReceiveTrailersSuccess(List List);

    void onReceiveTrailersFailed(Object failed);
}
