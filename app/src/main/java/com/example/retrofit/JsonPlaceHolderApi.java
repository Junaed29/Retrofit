package com.example.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/******  All type of json operation work in here  ******/
public interface JsonPlaceHolderApi {

    /* this is Retrofit ready function
     * this function will return list of a Post type object***/
    @GET("posts")
    Call<List<Post>> getPosts();

    //  We can use direct URL to get list
    @GET
    Call<List<Post>>  getPosts(@Url String url);


    //   '/posts?userId=1'
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);


    //  '/posts?userId=1&_sort=id&_order=desc'
    //   'int' value can be null so we will user Integer
    //   'Integer' can be null
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") Integer userId,
                              @Query("_sort") String sort,
                              @Query("_order") String orderBy
    );


    //   if we want more than one userId post we can use array
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") Integer[] userId,
                              @Query("_sort") String sort,
                              @Query("_order") String orderBy
    );

    //   if we want more one userId and other operation we can use map
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);


    /* this is Retrofit ready function
     * this function will return list of a Post type object for a specific id***/

    // @GET("posts/'2'/comments") '2' can be changed by us just add some value
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);
}
