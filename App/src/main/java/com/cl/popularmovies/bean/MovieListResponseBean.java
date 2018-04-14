package com.cl.popularmovies.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class MovieListResponseBean implements Parcelable {

    public static final Parcelable.Creator<MovieListResponseBean> CREATOR = new Parcelable.Creator<MovieListResponseBean>() {
        @Override
        public MovieListResponseBean createFromParcel(Parcel source) {
            return new MovieListResponseBean(source);
        }

        @Override
        public MovieListResponseBean[] newArray(int size) {
            return new MovieListResponseBean[size];
        }
    };
    private String page;
    private String total_results;
    private String total_pages;
    private List<ResultsBean> results;

    public MovieListResponseBean() {
    }

    protected MovieListResponseBean(Parcel in) {
        this.page = in.readString();
        this.total_results = in.readString();
        this.total_pages = in.readString();
        this.results = new ArrayList<>();
        in.readList(this.results, ResultsBean.class.getClassLoader());
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.page);
        dest.writeString(this.total_results);
        dest.writeString(this.total_pages);
        dest.writeList(this.results);
    }

    public static class ResultsBean implements Parcelable {

        public static final Creator<ResultsBean> CREATOR = new Creator<ResultsBean>() {
            @Override
            public ResultsBean createFromParcel(Parcel source) {
                return new ResultsBean(source);
            }

            @Override
            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };
        private String vote_count;
        private String id;
        private String video;
        private String vote_average;
        private String title;
        private String popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private String adult;
        private String overview;
        private String release_date;
        private List<String> genre_ids;

        public ResultsBean() {
        }

        protected ResultsBean(Parcel in) {
            this.vote_count = in.readString();
            this.id = in.readString();
            this.video = in.readString();
            this.vote_average = in.readString();
            this.title = in.readString();
            this.popularity = in.readString();
            this.poster_path = in.readString();
            this.original_language = in.readString();
            this.original_title = in.readString();
            this.backdrop_path = in.readString();
            this.adult = in.readString();
            this.overview = in.readString();
            this.release_date = in.readString();
            this.genre_ids = in.createStringArrayList();
        }

        public String getVote_count() {
            return vote_count;
        }

        public void setVote_count(String vote_count) {
            this.vote_count = vote_count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String isVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVote_average() {
            return vote_average;
        }

        public void setVote_average(String vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPopularity() {
            return popularity;
        }

        public void setPopularity(String popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String isAdult() {
            return adult;
        }

        public void setAdult(String adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public List<String> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<String> genre_ids) {
            this.genre_ids = genre_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.vote_count);
            dest.writeString(this.id);
            dest.writeString(this.video);
            dest.writeString(this.vote_average);
            dest.writeString(this.title);
            dest.writeString(this.popularity);
            dest.writeString(this.poster_path);
            dest.writeString(this.original_language);
            dest.writeString(this.original_title);
            dest.writeString(this.backdrop_path);
            dest.writeString(this.adult);
            dest.writeString(this.overview);
            dest.writeString(this.release_date);
            dest.writeStringList(this.genre_ids);
        }
    }
}
