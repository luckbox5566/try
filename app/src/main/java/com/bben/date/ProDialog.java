package com.bben.date;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by dantevsyou on 2017/8/13.
 */

public class ProDialog {
     ProgressDialog  progressDialog;

    public ProDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
    }

    public void show(){
        progressDialog.show();
    }

    public void close(){
        progressDialog.dismiss();
    }
}
