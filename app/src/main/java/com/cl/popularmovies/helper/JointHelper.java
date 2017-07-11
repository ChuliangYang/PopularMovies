package com.cl.popularmovies.helper;

import com.cl.popularmovies.data.Constant;

public class JointHelper {
    public static String jointPicURL(String posterPath) {
        return Constant.PICTURE_BASE_URL + posterPath;
    }

    public static String jointVoteAverage(String voteAverage) {
        return voteAverage + "/10";
    }
}
