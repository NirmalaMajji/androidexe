package com.androidexe.rest;

import com.androidexe.utlis.AppStrings;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApis {

   @GET(AppStrings.baseURL)
   Call<Object> getListData();

}
