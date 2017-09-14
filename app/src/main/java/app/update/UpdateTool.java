package app.update;

import android.content.Context;



/**
 * Created by dantevsyou on 2017/8/7.
 */

public final class UpdateTool {

    Update update=new Update();
    UpdateManager updateManager;

    public Update setContext(Context context){
        update.setContext(context);
        return update;
    }

    public Update setUri(String uri){
        update.setApk_path(uri);
        return update;
    }

    public Update setVersion(String version){
        update.setVersion(version);
        return update;
    }

    public UpdateManager build(){
        updateManager=new UpdateManager(update.getContext(),update.getApk_path(),update.getVersion());
        return updateManager;
    }

    public void pd(){
        updateManager.pd();
    }

}
