package com.example.android.miwok;

import com.google.gson.annotations.SerializedName;

public class Post {
    private Integer userId;

    private Integer id;
    private String title;
    @SerializedName("body")
    private String text;

    public Post(Integer userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
