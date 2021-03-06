package com.example.android.miwok;

import com.google.gson.annotations.SerializedName;

public class Post {
    private String userId;

    private String id;
    private String title;
    @SerializedName("body")
    private String text;


    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
