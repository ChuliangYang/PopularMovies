package com.cl.popularmovies.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.cl.popularmovies.Interface.OnResponseListener;
import com.com.cl.popularmovies.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RequestServerTask extends AsyncTask<URL, Void, String> {
    private Boolean getResponseSuccess;
    private Context context;
    private OnResponseListener onResponseListener;
    private Boolean shouldShowProgressDialog = true;
    private ProgressDialog progressDialog;
    private FrameLayout rootContainer;

    public RequestServerTask(Context context, OnResponseListener onResponseListener) {
        this.context = context;
        this.onResponseListener = onResponseListener;
        initProgressDialog(context);
    }


    @Override
    protected void onPreExecute() {
        if (shouldShowProgressDialog) {
            progressDialog.show();
            progressDialog.setContentView(rootContainer);
        }

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        getResponseSuccess = false;
        String response = "";
        try {
//            Log.d("net", "---------------------------------------------------开始后台网络请求----------------------------------------");
            HttpURLConnection httpURLConnection = (HttpURLConnection) params[0].openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                if (!isCancelled()) {
                    responseBuilder.append(line);
                } else {
                    break;
                }
            }
            if (!isCancelled()) {
                response = responseBuilder.toString();
                Log.d("response", response);
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    getResponseSuccess = true;
                }
            }
//            else {
//                Log.d("net", "---------------------------------------------------任务取消了----------------------------------------");
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.d("net", "---------------------------------------------------结束后台网络请求---------------------------------------------");
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if (shouldShowProgressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (getResponseSuccess) {
            onResponseListener.OnSuccess(response);
        } else {
            onResponseListener.OnFailed(response);
        }
        super.onPostExecute(response);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.e("asynctask", "---------------------------------------------------任务取消了----------------------------------------");
    }

    public RequestServerTask showProgressBar(Boolean show) {
        shouldShowProgressDialog = show;
        return this;
    }

    private void initProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context, R.style.CustomDialog);
        ProgressBar mProgressBar = new ProgressBar(context);
        rootContainer = new FrameLayout(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        mProgressBar.setLayoutParams(lp);
        rootContainer.addView(mProgressBar);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(dialog -> this.cancel(true));
    }

    public void cancel() {
        cancel(true);
    }
}
