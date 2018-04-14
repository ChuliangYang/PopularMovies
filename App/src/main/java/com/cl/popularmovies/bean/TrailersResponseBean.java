package com.cl.popularmovies.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class TrailersResponseBean implements Parcelable {

    public static final Parcelable.Creator<TrailersResponseBean> CREATOR = new Parcelable.Creator<TrailersResponseBean>() {
        @Override
        public TrailersResponseBean createFromParcel(Parcel source) {
            return new TrailersResponseBean(source);
        }

        @Override
        public TrailersResponseBean[] newArray(int size) {
            return new TrailersResponseBean[size];
        }
    };
    private int id;
    private List<ResultsBean> results;

    public TrailersResponseBean() {
    }

    protected TrailersResponseBean(Parcel in) {
        this.id = in.readInt();
        this.results = in.createTypedArrayList(ResultsBean.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        dest.writeInt(this.id);
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
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private String size;
        private String type;

        public ResultsBean() {
        }

        protected ResultsBean(Parcel in) {
            this.id = in.readString();
            this.iso_639_1 = in.readString();
            this.iso_3166_1 = in.readString();
            this.key = in.readString();
            this.name = in.readString();
            this.site = in.readString();
            this.size = in.readString();
            this.type = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.iso_639_1);
            dest.writeString(this.iso_3166_1);
            dest.writeString(this.key);
            dest.writeString(this.name);
            dest.writeString(this.site);
            dest.writeString(this.size);
            dest.writeString(this.type);
        }
    }
}
