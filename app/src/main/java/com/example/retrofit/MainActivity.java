package com.example.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView, captionTextView;

    /*****Retrofit object******/
    Retrofit retrofit;

    //JsonPlaceHolderApi api
    JsonPlaceHolderApi jsonPlaceHolderApi;

    //ProgressDialog
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data is getting ready");
        progressDialog.show();


        textView = findViewById(R.id.textResultId);
        captionTextView = findViewById(R.id.captionTextViewId);

        /** Build a retrofit object **/
        retrofit = new Retrofit.Builder().
                /*  Base url
                 *  '/' must be put at the last of the url**/
                        baseUrl("https://jsonplaceholder.typicode.com/").
                /** Converter which convert JSON to java object **/
                        addConverterFactory(GsonConverterFactory.create()).
                        build();

        /* JsonPlaceHolderApi is a interface so we can't directly create object
         * with help of retrofit object we get jsonPlaceHolderApi object **/
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getPost("https://jsonplaceholder.typicode.com/posts");

        //getComment(5);

        // If we don't need any specification then just pass null
        //getSpecificPost(null, null, null);

        //getSpecificPost(5, "id", "desc");

        //getSpecificPost();
    }


    // Specific Post
    public void getSpecificPost(int postId) {

        captionTextView.setText("\"GET    /posts?userId=" + postId + "\"" + "\n" + "Posts for '" + postId + "' userId");


        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(postId);

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        allPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Post class object
                 * by response body*/
                List<Post> allPost = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Post post : allPost) {
                    stringBuilder.append("User Id : " + post.getUserId() + "\n");
                    stringBuilder.append("Id : " + post.getId() + "\n");
                    stringBuilder.append("Title : " + post.getTitle() + "\n");
                    stringBuilder.append("Message : " + post.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }


    // Specific Post By Map
    public void getSpecificPost() {

        Map <String, String> parameter = new HashMap<>();

        // HashMap can take key just one time so we cant use multiple userId here
        parameter.put("userId", "2");
        parameter.put("_sort", "id");
        parameter.put("_order", "desc");

        captionTextView.setText("Posts for using map");


        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(parameter);

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        allPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Post class object
                 * by response body*/
                List<Post> allPost = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Post post : allPost) {
                    stringBuilder.append("User Id : " + post.getUserId() + "\n");
                    stringBuilder.append("Id : " + post.getId() + "\n");
                    stringBuilder.append("Title : " + post.getTitle() + "\n");
                    stringBuilder.append("Message : " + post.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }

    // More Specific Post sort and orderBy
    public void getSpecificPost(Integer postId, String sort, String orderBy) {

        captionTextView.setText("\"GET    /posts?userId=" + postId + "&_sort="+sort+"&_order="+orderBy+"\"" + "\n" + "Posts for '" + postId + "' userId sort by '"+sort+"' and '"+orderBy+"' order");


        // If we don't need any specification then just pass null
        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(postId, sort, orderBy);


        //   if we want more than one userId post we can use array
        //Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(new Integer[]{1,2}, null, null);


        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        allPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Post class object
                 * by response body*/
                List<Post> allPost = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Post post : allPost) {
                    stringBuilder.append("User Id : " + post.getUserId() + "\n");
                    stringBuilder.append("Id : " + post.getId() + "\n");
                    stringBuilder.append("Title : " + post.getTitle() + "\n");
                    stringBuilder.append("Message : " + post.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }

    // All Post
    public void getPost() {

        captionTextView.setText("\"GET-   /posts\"" + "\n" + "All posts");


        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts();

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        allPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Post class object
                 * by response body*/
                List<Post> allPost = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Post post : allPost) {
                    stringBuilder.append("User Id : " + post.getUserId() + "\n");
                    stringBuilder.append("Id : " + post.getId() + "\n");
                    stringBuilder.append("Title : " + post.getTitle() + "\n");
                    stringBuilder.append("Message : " + post.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }

    // All Post from directly from URL
    public void getPost(String url) {

        captionTextView.setText("\"Post By url\"" + "\n" + "All posts");


        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(url);

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        allPostCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Post class object
                 * by response body*/
                List<Post> allPost = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Post post : allPost) {
                    stringBuilder.append("User Id : " + post.getUserId() + "\n");
                    stringBuilder.append("Id : " + post.getId() + "\n");
                    stringBuilder.append("Title : " + post.getTitle() + "\n");
                    stringBuilder.append("Message : " + post.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }

    // Specific Comments
    public void getComment(int postId) {

        captionTextView.setText("\"GET    /posts/" + postId + "/comments\"" + "\nComments for '" + postId + "' postId");

        Call<List<Comment>> getAllCommentsCall = jsonPlaceHolderApi.getComments(postId);

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to word background thread  ***/
        getAllCommentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    /* sometime response will be ERROR: 404 or
                     * 404 Not Found **/
                    textView.setText("error : " + response.code());

                    /* this return for null point exception***/
                    return;
                }

                /* get all list of Comment class object
                 * by response body*/
                List<Comment> allComment = response.body();

                StringBuilder stringBuilder = new StringBuilder();
                for (Comment comment : allComment) {
                    stringBuilder.append("Post Id : " + comment.getPostId() + "\n");
                    stringBuilder.append("Id : " + comment.getId() + "\n");
                    stringBuilder.append("Name : " + comment.getName() + "\n");
                    stringBuilder.append("Email : " + comment.getEmail() + "\n");
                    stringBuilder.append("Message : " + comment.getMessage() + "\n\n");

                }


                textView.setText(stringBuilder.toString());

                // ProgressDialog Close
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });
    }
}
