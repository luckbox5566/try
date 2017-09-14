package app.update;

import android.content.Context;

/**
 * Created by dantevsyou on 2017/8/7.
 */

public class Update {
    private Context context;
    private String apk_path="";
    private String version;

    public String getApk_path() {
        return apk_path;
    }

    public void setApk_path(String apk_path) {
        this.apk_path = apk_path;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
