package my_network;

import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;

import handlers.CheckNetworkHandler;
import my_network.tools.DecodeMapTool;
import my_network.tools.DoMesTool;
import my_network.tools.UploadDataTool;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import thread.CheckNetworkThread;

/**
 * Created by Administrator on 2016/11/15.
 */

 public class NetWork extends NetWorkAbs implements Serializable {

    private BufferedWriter writer;
    private BufferedReader reader;

    private Handler handler;
    private String ip;//ip地址
    private int port;//访问端口
    private String id;//登录人的id
    private String number;//要执行的方法
    private String moKuai;
    private String param="";//参数
    private Context context;
    private String final_data="";//从服务器传过来的最终数据

    private SocketResult socketResult;
    private String param_compress;
    private String headers;
    public static final String HealthEDU ="ENJOYOR.MICS.Business.NursingReport/ENJOYOR.MICS.Business.NursingReport.MessageOption";
    /**
     * @param id 登陆系统之人的id
     * @param number 要执行的方法，比如0300202(登录)
     * @param message 要执行的模块
     * @param canshu 要执行的参数
     * @param port 端口(默认5000)
     * @param context 上下文
     */
    public NetWork(String id, String number, String message, String canshu, Context context, int port) {
        super(port,context);
        this.id = id;
        this.number = number;
        this.moKuai = message;
        this.param = canshu;
        this.context = context;
        this.port = port;
    }

    /**
     *  回调接口
     *  success() 成功，获取真实参数后回调
     *  fail() 失败
     */
    public  interface SocketResult{
        void success(String data);
        void fail(String info);
    }

    /**
     *  使用框架:RxJava
     *  线程：网络请求在子线程中，回调函数在主线程中。
     *  回调方式：接口回调。
     *
     *  步骤一:compress() 压缩参数
     *  步骤二:uploadData() 首先上传消息头，然后上传之前压缩的参数
     *  步骤三:loadData() 从服务端下载数据，放在map中
     *  步骤四:decodeMap() 解析map得到真实数据，并返回标志位
     */
    public void start(SocketResult s){
        this.socketResult=s;


        final CheckNetworkThread thread=new CheckNetworkThread(context);
        thread.start();
        Observable.create(new Observable.OnSubscribe<EnumTest>() {
            @Override
            public void call(Subscriber<? super EnumTest> subscriber) {
                if(startSocket()==EnumTest.RIGHT){
                    reader= NetWork.super.reader;
                    writer= NetWork.super.writer;
                    System.out.print("Socket创建成功"+"\n");
                    param_compress=compress(param);
                    uploadData();
                    SparseArray<String> map=loadData();
                    EnumTest enumTest=decodeMap(map);
                    subscriber.onNext(enumTest);
                }else {
                    subscriber.onNext(EnumTest.NETWORK_ERROR);
                }
            }
        }).subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnumTest>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(context,"Rxjava产生错误",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNext(EnumTest enumTest) {
              switch (enumTest)
              {
                  case RIGHT:
                      socketResult.success(final_data);
                      closeSocket();
                      System.out.print("success~回调函数返回结果,并关闭连接"+"\n");
                      break;
                  case NOT_RIGHT:
                      socketResult.fail("网络错误请检查！");
                      closeSocket();
                      System.out.print("fail~回调函数返回结果，并关闭连接"+"\n");
                      break;
              }
            }
        });
    }

    /**
     * 拼接消息头
     */
    private void getHeaders()
    {
        headers = id + SEPARATE + "高坡" + SEPARATE + "你电脑的mac地址"
                + SEPARATE + moKuai + SEPARATE + number + SEPARATE
                + param_compress.length();
    }

    /**
     * 传送消息头和参数
     */
    private void uploadData(){
        try{
            getHeaders();
            UploadDataTool.uploadData(headers,writer,reader);
            UploadDataTool.uploadData(param_compress,writer,reader);
        }catch (Exception e){
            System.out.print("uploadData()方法错误"+"\n");
        }
    }

    /**
     * 下载数据
     */
    public SparseArray<String> loadData(){
        try{
            String[] handerItems;
            while (true) {
                char[] dataHander = new char[256 + 32];
                int length = reader.read(dataHander);
                System.out.print("loadData() length长度"+length+"\n");
                String dataHeader = new String(dataHander, 0, length);
                handerItems = dataHeader.split("@");
               if (("GPStart".equals(handerItems[0].trim()))
                        && ("GPEnd".equals(handerItems[4].trim()))) {
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                    break;
                } else {
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                }
            }

            int dataLen = Integer.parseInt(handerItems[3].trim());
            int count = (int) Math.ceil(dataLen / 256.0);
            System.out.print(" 个数:"+"\n"+count+"\n"+"handerItems:"+handerItems[3]+"\n");
            SparseArray<String> map = new SparseArray<String>();
            // 得到服务器传输过来的数据
            for (int i = 0; i < count; i++) {
                // Log.i("cishu", i+"");
                char[] dataChars = new char[256 + 32];
                int len = reader.read(dataChars);
                String dataStr = new String(dataChars, 0, len);
                String[] dataItems = dataStr.split("@");

                System.out.print(" 从服务端下载的数据:"+"\n"+dataItems+"\n");

                if ("GPStart".equals(dataItems[0].trim())
                        && "GPEnd".equals(dataItems[4].trim())) {
                    map.put(i, dataItems[3].trim());
                    System.out.print(" map中放入的数据:"+dataItems[3].trim()+"\n");
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                } else {
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                    i--;
                }
            }
            closeSocket();
            return map;
        }catch (Exception e){
            System.out.print(" loadData()方法错误"+e.toString()+"\n");
            return null;
        }

    }
    /**
     * 从map中获取数据，并解压和解密
    */
    public EnumTest decodeMap(SparseArray<String> map){
        try {

            String data= DecodeMapTool.decodeMap(map);
            String[] items = data.split(SEPARATE);
            // 以ReturnCode判断正确与否
            if ((CORRECT + "").equals(items[0])) {
                final_data = items[2];
                System.out.print("最正确终数据为:"+"\n"+final_data+"\n");
                return EnumTest.RIGHT;
            }
            if ((ERROR + "").equals(items[0])) {
                final_data = items[1];
                Log.i("info",final_data);
                System.out.print("最错误终数据为:"+"\n"+final_data+"\n");
                return EnumTest.NOT_RIGHT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("decodeMap()方法错误"+"\n");
            return EnumTest.NOT_RIGHT;
        }
       return EnumTest.NOT_RIGHT;
    }

    @Override
    String getData() {
        return final_data;
    }

}
