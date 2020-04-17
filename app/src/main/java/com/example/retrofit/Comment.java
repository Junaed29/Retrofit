package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

// Same Class Like Post
public class Comment  {
    private int postId;

    private int id;

    private String name;

    private String email;


    // It is a GSON properties
    @SerializedName("body")
    private String message;


    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
