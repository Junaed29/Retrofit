package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

/* this class contain json variable and its value
 * number of variable in thus class must be same name as json
 * or we can use my choice but use a notation for this *********/
public class Post {
    private int userId;

    private int id;

    private String title;

    /*we can use my choice name but use a notation for this*/
    @SerializedName("body")
    private String message;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
