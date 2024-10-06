package com.tabish.movieapp0139.models;

import android.graphics.Bitmap;

public class ProfileModel {

    private String username, password;
    private Bitmap profileImage;

    public ProfileModel(String username, String password, Bitmap profileImage) {
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

}
