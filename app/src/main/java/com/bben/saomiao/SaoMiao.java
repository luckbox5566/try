package com.bben.saomiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by dantevsyou on 2017/8/13.
 */
/*
 private  final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String text = intent.getStringExtra("data");


        }
    };
 */

public class SaoMiao {

    public static void bulid(Context context,BroadcastReceiver my){
        String   model= android.os.Build.MODEL;
        switch (model){
            case "m80s":
                get("mobi",context,my);

                break;
            case "htc6750_c66_b_m":
                get("bben",context,my);
                break;

        }
    }

    public  static void get(String s, Context context,BroadcastReceiver myReceiver){
        switch (s){
            case "mobi":

                break;
            case "bben":
                IntentFilter filter = new IntentFilter();
                filter.addAction("6252374198A2DB35EBF315CAEF8BAE4E");
                context.registerReceiver(myReceiver, filter);
                break;

        }
    }
}
