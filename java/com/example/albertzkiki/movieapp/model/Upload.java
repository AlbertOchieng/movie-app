package com.example.albertzkiki.movieapp.model;

/**
 * Created by albertzkiki on 4/2/2018.
 */

public class Upload {

        String mName;
        String mImageUrl;


    public Upload() {
    }

    public Upload(String mName, String mImageUrl) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}


