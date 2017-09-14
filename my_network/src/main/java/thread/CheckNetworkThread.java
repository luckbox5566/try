package thread;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;

import handlers.CheckNetworkHandler;

/**
 * Created by dantevsyou on 2017/9/4.
 */

public class CheckNetworkThread extends Thread {

    boolean flag = false;
    ConnectivityManager manager;
    Context context;
    CheckNetworkHandler handler;
    public CheckNetworkThread(Context context){
        this.context=context;
        handler=new CheckNetworkHandler(context);
    }

    public void go(){
        this.run();
    }
    @Override
    public void run() {
        super.run();
        //得到网络连接信息
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }

    }
}


