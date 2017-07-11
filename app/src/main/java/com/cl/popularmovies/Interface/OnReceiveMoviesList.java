package com.cl.popularmovies.Interface;


import com.cl.popularmovies.bean.MovieListResponseBean;

import java.util.List;

public interface OnReceiveMoviesList {
    void onSuccess(List<MovieListResponseBean.ResultsBean> MovieBeans);

    void onFailed(Object MovieBeans);
}
