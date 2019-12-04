package com.androidexe.rest;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroResponse {
    private static final String TAG = "RetroResponse";
    private Context context;
    private ProgressDialog progressDialog;
    private boolean isProgressVisible;
    private RetroResponseListener mListener;

    public RetroResponse(Context context, boolean isProgressVisible) {
        this.context = context;
        this.isProgressVisible = isProgressVisible;
        this.mListener = (RetroResponseListener) context;
    }

    public RetroResponse(Fragment fragment, boolean isProgressVisible) {
        this.context = fragment.getActivity();
        this.isProgressVisible = isProgressVisible;
        this.mListener = (RetroResponseListener) fragment;
    }

    private void showProgressDialog() {
        if (isProgressVisible) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    private void dismissProgressDialog() {
        if (isProgressVisible) {
            progressDialog.dismiss();
        }
    }

    public String getResponse(final String URL, final Call<Object> objectCall) {
        showProgressDialog();

        Log.e(TAG, "postResponse: " + objectCall.toString());
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                dismissProgressDialog();
                String json = new Gson().toJson(response.body());
                Log.e(TAG, "onResponse: " + json);
                try {
                    mListener.onRetroResponseGet(json, URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                dismissProgressDialog();
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
        return null;
    }

    public interface RetroResponseListener {
        void onRetroResponseGet(String response, String URL) throws JSONException;
    }
}
