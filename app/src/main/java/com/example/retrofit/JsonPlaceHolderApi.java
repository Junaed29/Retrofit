package com.example.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<Post>> getPosts(@Url String url);


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


    //   if we want more than one userId then  we can use array
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") Integer[] userId,
                              @Query("_sort") String sort,
                              @Query("_order") String orderBy
    );

    //   if we want more but one userId and other operation but not specific then we can use map
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);


    /* this is Retrofit ready function
     * this function will return list of a Comment type object for a specific id***/

    // @GET("posts/'2'/comments") '2' can be changed by us just add some value
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);


    // In GET request we just sent parameter as query or something and get '#Body' as list
    // But in Post request we will send body to server for this we this '@Body' as notation
    // We use 'GSON' converter to convert 'java into json', this GSON converter convert 'Post object' into 'json' and sent to server
    // POST request return call as the same as we pass as parameter for this we use 'Call<Post>' as it sent a Post object
    @POST("posts")
    Call<Post> createPost(@Body Post post);


    // We can use POST request in another way bt Using "@FormUrlEncoded"
    // it will send to server like "userId=29&title=New%20Title@Body=New%20Text"
    // '%20' is avoiding space
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body
    );

    // We can use @FieldMap to post my specific map but its can use list
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> maps);

    //We have two request to update server data 'PUT' and 'PATCH'

    //'PUT' request replace the whole object.Suppose if 'title' is null then the existing tittle will be null
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id,@Body Post post);

    //'PATCH' request replace the field.Suppose if 'title' is null then the existing tittle will be same as it was
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id,@Body Post post);

    //'Delete' request get just "Post id" and return just "empty body"
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
