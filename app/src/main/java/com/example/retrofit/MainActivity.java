package com.example.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

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



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data is getting ready");
        progressDialog.show();

        textView = findViewById(R.id.textResultId);

        /** Build a retrofit object **/
        retrofit = new Retrofit.Builder().
                /** Base url **/
                        baseUrl("https://jsonplaceholder.typicode.com/").
                /** Converter which convert JSON to java object **/
                        addConverterFactory(GsonConverterFactory.create()).
                        build();

        /* JsonPlaceHolderApi is a interface so we can't directly create object
         * with help of retrofit object we get jsonPlaceHolderApi object **/
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

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
                for(Post post : allPost){
                    stringBuilder.append("User Id : "+post.getUserId()+"\n");
                    stringBuilder.append("Id : "+post.getId()+"\n");
                    stringBuilder.append("Title : "+post.getTitle()+"\n");
                    stringBuilder.append("Message : "+post.getMessage()+"\n\n");

                }

                progressDialog.dismiss();
                textView.setText(stringBuilder.toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                /* Throwable is the super call of exception and errors***/
                textView.setText(t.getMessage());
            }
        });

    }
}
