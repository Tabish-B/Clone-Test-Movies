package com.tabish.movieapp0139.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CastModel implements Parcelable {


    private int gender;
    private String name;
    private String character;
    private String profile_path;
    private int order;


    public CastModel(int gender, String name, String character, String profile_path, int order) {
        this.gender = gender;
        this.name = name;
        this.character = character;
        this.profile_path = profile_path;
        this.order = order;
    }

    protected CastModel(Parcel in) {
        gender = in.readInt();
        name = in.readString();
        character = in.readString();
        profile_path = in.readString();
        order = in.readInt();
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public static final Creator<CastModel> CREATOR = new Creator<CastModel>() {
        @Override
        public CastModel createFromParcel(Parcel in) {
            return new CastModel(in);
        }

        @Override
        public CastModel[] newArray(int size) {
            return new CastModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(gender);
        dest.writeString(name);
        dest.writeString(character);
        dest.writeString(profile_path);
        dest.writeInt(order);
    }



    @Override
    public String toString() {
        return "CastModel{" +
                "gender=" + gender +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", order=" + order +
                '}';
    }


}
