package com.androidexe.rest;


import com.androidexe.utlis.AppStrings;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

   private static Retrofit retrofit;
   private static final String BASE_URL = AppStrings.baseURL;

   public static Retrofit getRetrofitInstance() {

      retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .client(httpAuthKey())
              .build();

      return retrofit;
   }

   public static OkHttpClient httpAuthKey() {

      return new OkHttpClient.Builder()
              .addInterceptor(new Interceptor() {
                 @NotNull
                 @Override
                 public Response intercept(@NotNull Chain chain) throws IOException {
                    Request authorisedRequest = chain.request().newBuilder().build();
                    return chain.proceed(authorisedRequest);
                 }
              }).build();
   }

}
