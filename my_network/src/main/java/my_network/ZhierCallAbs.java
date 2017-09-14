package my_network;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/14.
 */

public abstract class ZhierCallAbs {


    public ZhierCallAbs() {

    }




    public abstract ZhierCallAbs setId(String id);
    public abstract ZhierCallAbs setNumber(String number);
    public abstract ZhierCallAbs setMessage(String message);
    public abstract ZhierCallAbs setCanshu(String canshu);
    public abstract ZhierCallAbs setContext(Context context);
    public abstract ZhierCallAbs setPort(int port);
    public abstract ZhierCall build();
}
