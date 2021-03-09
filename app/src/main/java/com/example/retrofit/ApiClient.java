package com.example.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This is better for use in main Activity

public class ApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static Retrofit retrofit = null;


    // Singleton instance for Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            //Retrofit use okHttp Client for logging (Debugging)
            // Creating HttpLoggingInterceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Connecting HttpLoggingInterceptor with okHttpClient
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

            /** Build a retrofit object **/
            retrofit = new Retrofit.Builder()
                    /*  Base url. '/' must be put at the last of the url**/
                    .baseUrl(BASE_URL)
                    /* Converter which convert JSON to java object **/
                    .addConverterFactory(GsonConverterFactory.create())
                    /* Adding OkHttp Client with Retrofit **/
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
