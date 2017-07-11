package com.cl.popularmovies.Interface;


public interface OnResponseListener {
    void OnSuccess(String response);

    void OnFailed(String response);
}
