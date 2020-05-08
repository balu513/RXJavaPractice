package com.balu.rxjavapractice.api.api_client;

import com.balu.rxjavapractice.api.CricetApiInterface;
import com.balu.rxjavapractice.api.PostsApiInterface;
import com.balu.rxjavapractice.constants.Consts;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsApiClient {

    public static PostsApiInterface getPostsApiClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        PostsApiInterface postsApiInterface = new Retrofit.Builder().baseUrl(Consts.Post.URL_PREFIX_POSTS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build().create(PostsApiInterface.class);

        return postsApiInterface;
    }
}
