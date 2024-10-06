package com.tabish.movieapp0139.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable {

 private String key;
 private String type;
 private Boolean official;
 private String published_at;
 private String site;


    public VideoModel(String key, String type, Boolean official, String published_at, String site) {
        this.key = key;
        this.type = type;
        this.official = official;
        this.published_at = published_at;
        this.site = site;
    }

    protected VideoModel(Parcel in) {
        key = in.readString();
        type = in.readString();
        byte tmpOfficial = in.readByte();
        official = tmpOfficial == 0 ? null : tmpOfficial == 1;
        published_at = in.readString();
        site = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(type);
        dest.writeByte((byte) (official == null ? 0 : official ? 1 : 2));
        dest.writeString(published_at);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", official=" + official +
                ", published_at='" + published_at + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
