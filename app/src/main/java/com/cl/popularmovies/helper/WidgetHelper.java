package com.cl.popularmovies.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.com.cl.popularmovies.R;


public class WidgetHelper {

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.CustomDialog);
        ProgressBar mProgressBar = new ProgressBar(context);
        FrameLayout rootContainer = new FrameLayout(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        mProgressBar.setLayoutParams(lp);
        rootContainer.addView(mProgressBar);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
