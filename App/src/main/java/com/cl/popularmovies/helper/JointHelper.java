package com.cl.popularmovies.helper;

import com.cl.popularmovies.constant.Network;

public class JointHelper {
    public static String jointPicURL(String posterPath) {
        return Network.PICTURE_BASE_URL + posterPath;
    }

    public static String jointVoteAverage(String voteAverage) {
        return voteAverage + "/10";
    }
}
