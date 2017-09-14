package my_network;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ZhierCall extends ZhierCallAbs {

    private int port=0;//访问端口
    private String id=null;//登录人的id
    private String number=null;//要执行的方法
    private String moKuai=null;
    private String param=null;//参数
    private Context context=null;

    NetWork z;
    public ZhierCall() {

    }


    @Override
    public ZhierCallAbs setId(String id) {
        this.id=id;
        return this;
    }

    @Override
    public ZhierCallAbs setNumber(String number) {
        this.number=number;
        return this;
    }

    @Override
    public ZhierCallAbs setMessage(String message) {
        this.moKuai=message;
        return this;
    }

    @Override
    public ZhierCallAbs setCanshu(String canshu) {
        this.param=canshu;
        return this;
    }

    @Override
    public ZhierCallAbs setContext(Context context) {
        this.context=context;
        return this;
    }

    @Override
    public ZhierCallAbs setPort(int port) {
        this.port=port;
        return this;
    }


    public ZhierCall build()
    {
        z=new NetWork(id,number,moKuai,param,context,port);
       return this;
    }

    public void start(NetWork.SocketResult s)
    {
        z.start(s);
    }
}
