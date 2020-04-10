package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/******  All type of json operation work in here  ******/
public interface JsonPlaceHolderApi {

    /* this is Retrofit ready function
     * this function will return list of a Post type object***/
    @GET("posts")
    Call<List<Post>> getPosts();
}
