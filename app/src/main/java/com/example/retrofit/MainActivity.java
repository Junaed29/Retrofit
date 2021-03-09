package com.example.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Errors code : https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

    TextView textView, captionTextView;


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


        /* JsonPlaceHolderApi is a interface so we can't directly create object
         * with help of retrofit object we get jsonPlaceHolderApi object **/
        jsonPlaceHolderApi = ApiClient.getClient().create(JsonPlaceHolderApi.class);


        //getPost("https://jsonplaceholder.typicode.com/posts");
        getPost();

        //getComment(5);

        // If we don't need any specification then just pass null
        //getSpecificPost(null, null, null);

        //getSpecificPost(5, "id", "desc");

        //getSpecificPost();

        //createPost();

        //updatePost();

        //deletePost();

    }

    // Delete Post using 'DELETE' request
    private void deletePost() {

        Call<Void> voidCall = jsonPlaceHolderApi.deletePost(5);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code : " + response.code());
                    return;
                }


                textView.setText("Code : " + response.code());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setGravity(Gravity.CENTER | Gravity.TOP);
                textView.setTextSize(40f);
                textView.setText(t.getMessage().toUpperCase());
                progressDialog.dismiss();
            }
        });
    }


    // Update Post using 'PUT' pr 'PATCH' request
    private void updatePost() {
        Post post = new Post(12, null, "New Text");

        // TODO 'PUT' request
        Call<Post> postCall = jsonPlaceHolderApi.putPost("789",5,post);

        // TODO 'PATCH' request
        //Call<Post> postCall = jsonPlaceHolderApi.patchPost(5, post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code : " + response.code());
                    return;
                }

                Post postResponse = response.body();

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Code : " + response.code() + "\n");
                stringBuilder.append("Id : " + postResponse.getId() + "\n");
                stringBuilder.append("User Id : " + postResponse.getUserId() + "\n");
                stringBuilder.append("Title : " + postResponse.getTitle() + "\n");
                stringBuilder.append("Message : " + postResponse.getMessage() + "\n");

                textView.setText(stringBuilder.toString());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setGravity(Gravity.CENTER | Gravity.TOP);
                textView.setTextSize(40f);
                textView.setText(t.getMessage().toUpperCase());
                progressDialog.dismiss();
            }
        });
    }


    // Create Post using 'Post' Request
    private void createPost() {
        captionTextView.setText("\"POST-   /posts\"" + "\n" + "Create post");

        Post post = new Post(29, "Junaed", "A good android developer");
        // TODO WITHOUT USING "@FormUrlEncoded"
        // Call<Post> createPostCall = jsonPlaceHolderApi.createPost(post);


        // TODO USING "@FormUrlEncoded" with Field
        // Call<Post> createPostCall = jsonPlaceHolderApi.createPost(29,"New Title", "New Text");


        // TODO USING "@FormUrlEncoded:" with FieldMap
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("userId", "29");
        fieldMap.put("title", "New Title");
        fieldMap.put("body", "New Text");
        Call<Post> createPostCall = jsonPlaceHolderApi.createPost(fieldMap);


        createPostCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code : " + response.code());
                    return;
                }

                Post postResponse = response.body();

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Code : " + response.code() + "\n");
                stringBuilder.append("Id : " + postResponse.getId() + "\n");
                stringBuilder.append("User Id : " + postResponse.getUserId() + "\n");
                stringBuilder.append("Title : " + postResponse.getTitle() + "\n");
                stringBuilder.append("Message : " + postResponse.getMessage() + "\n");

                textView.setText(stringBuilder.toString());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setGravity(Gravity.CENTER | Gravity.TOP);
                textView.setTextSize(40f);
                textView.setText(t.getMessage().toUpperCase());
                progressDialog.dismiss();
            }
        });

    }


    // Specific Post for 'GET' request
    public void getSpecificPost(int postId) {

        captionTextView.setText("\"GET    /posts?userId=" + postId + "\"" + "\n" + "Posts for '" + postId + "' userId");


        Call<List<Post>> allPostCall = jsonPlaceHolderApi.getPosts(postId);

        /* allPostCall.execute can freeze the app
         * so we call allPostCall.enqueue which is retrofit default method to work in background thread  ***/
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


    // Specific Post By Map for 'GET' request
    public void getSpecificPost() {

        Map<String, String> parameter = new HashMap<>();

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


    // More Specific Post sort and orderBy for 'GET' request
    public void getSpecificPost(Integer postId, String sort, String orderBy) {

        captionTextView.setText("\"GET    /posts?userId=" + postId + "&_sort=" + sort + "&_order=" + orderBy + "\"" + "\n" + "Posts for '" + postId + "' userId sort by '" + sort + "' and '" + orderBy + "' order");


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


    // All Post for 'GET' request
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


    // All Post from directly from URL for 'GET' request
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


    // Specific Comments for 'GET' request
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
