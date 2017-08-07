package com.cl.popularmovies.constant;

public class Network {
    public final static String REQUEST_POP_LIST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=58db6e9f579236f079b9460f081da511";
    public final static String REQUEST_RATED_LIST_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=58db6e9f579236f079b9460f081da511&language=en-US";
    public final static String PICTURE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    //一个参数id
    public final static String REQUEST_TRAILERS = "https://api.themoviedb.org/3/movie/%s/videos?api_key=58db6e9f579236f079b9460f081da511&language=en-US";
    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    //两个参数，id和page
    public final static String REQUEST_REVIEWS_LIST_URL = "https://api.themoviedb.org/3/movie/%s/reviews?api_key=58db6e9f579236f079b9460f081da511&language=en-US&page=%s";

}
