package com.decode;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by chenlikang
 */

public class DecodeSampleApplication extends Application {
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

}
