package com.cl.popularmovies.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReviewsResponseBean implements Parcelable {

    public static final Parcelable.Creator<ReviewsResponseBean> CREATOR = new Parcelable.Creator<ReviewsResponseBean>() {
        @Override
        public ReviewsResponseBean createFromParcel(Parcel source) {
            return new ReviewsResponseBean(source);
        }

        @Override
        public ReviewsResponseBean[] newArray(int size) {
            return new ReviewsResponseBean[size];
        }
    };
    private String id;
    private String page;
    private String total_pages;
    private String total_results;
    private List<ResultsBean> results;

    public ReviewsResponseBean() {
    }

    protected ReviewsResponseBean(Parcel in) {
        this.id = in.readString();
        this.page = in.readString();
        this.total_pages = in.readString();
        this.total_results = in.readString();
        this.results = in.createTypedArrayList(ResultsBean.CREATOR);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
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
        dest.writeString(this.id);
        dest.writeString(this.page);
        dest.writeString(this.total_pages);
        dest.writeString(this.total_results);
        dest.writeTypedList(this.results);
    }

    public static class ResultsBean implements Parcelable {

        public static final Parcelable.Creator<ResultsBean> CREATOR = new Parcelable.Creator<ResultsBean>() {
            @Override
            public ResultsBean createFromParcel(Parcel source) {
                return new ResultsBean(source);
            }

            @Override
            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };
        private String id;
        private String author;
        private String content;
        private String url;

        public ResultsBean() {
        }

        protected ResultsBean(Parcel in) {
            this.id = in.readString();
            this.author = in.readString();
            this.content = in.readString();
            this.url = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.author);
            dest.writeString(this.content);
            dest.writeString(this.url);
        }
    }
}
