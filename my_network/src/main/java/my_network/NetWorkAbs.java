package my_network;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import my_network.tools.CompressTool;
import my_network.tools.DecompressTool;

import static android.content.Context.MODE_PRIVATE;

/**
 * 实现网络层的封装
 */

abstract class NetWorkAbs implements CompressInterface,DecompressInterface {
    /**
     * 分隔符
     * 传输正确
     * 数据传输错误
     */
    public static final String SEPARATE = "¤";
    public static final int CORRECT = 0;
    public static final int ERROR = -1;

    /**
     * Socket使用
     */
    protected BufferedReader reader;
    protected BufferedWriter writer;
    private String ip;
    private int port=5000;
    private Socket socket;
    /**
    * 配置文档
    */
    public static final String IPFILE="ipsetting";
    public static final String IPNODE = "ip";
    public static final String SYSTEM_MESSAGE = "ENJOYOR.MICS.Business.GongYong/ENJOYOR.MICS.Business.GongYong.MessageOption";
    public static final String YIZHU_ZHIXING = "ENJOYOR.MICS.Business.YiZhu/ENJOYOR.MICS.Business.YiZhu.MessageOption";
    public static final String BINGREN_XX = "ENJOYOR.MICS.Business.BingRenXX/ENJOYOR.MICS.Business.BingRenXX.MessageOption";
    public static final String SMTZ = "ENJOYOR.MICS.Business.ShengMingTZ/ENJOYOR.MICS.Business.ShengMingTZ.MessageOption";
    public static final String HULIJIRU = "ENJOYOR.MICS.Business.HuLiJiLu/ENJOYOR.MICS.Business.HuLiJiLu.MessageOption";
    public static final String PINGGUD = "ENJOYOR.MICS.Business.PingGuD/ENJOYOR.MICS.Business.PingGuD.MessageOption";
    public static final String NOTEBOOK = "ENJOYOR.MICS.Business.NursingReport/ENJOYOR.MICS.Business.NursingReport.MessageOption";
    public static final String GongYong ="ENJOYOR.MICS.Business.GongYong/ENJOYOR.MICS.Business.GongYong.MessageOption";
    public static final String HealthEDU ="ENJOYOR.MICS.Business.NursingReport/ENJOYOR.MICS.Business.NursingReport.MessageOption";
    /**
   * 枚举类
   * RIGHT:正确
   * NOT_RIGHT:错误
   */
    protected enum EnumTest {
        RIGHT,
        NOT_RIGHT,
        NETWORK_ERROR,
    }

    public NetWorkAbs(int port, Context context) {
        this.port = port;
        //通过之前的存储找到ip地址
        if (null != context) {
            /*SharedPreferences sp = context.getSharedPreferences(IPFILE, MODE_PRIVATE);
            ip = sp.getString(IPNODE, "192.168.7.82");*/

            SharedPreferences pref = context.getSharedPreferences("data",MODE_PRIVATE);
            ip = pref.getString("ip","192.168.7.82");//第二个参数为默认值
        }
    }

    /**
     * Socket连接
     */
    protected EnumTest startSocket(){
        try {
            System.out.print("执行方法startSocket()"+"\n");
            socket=new Socket(ip,port);
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-16"));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return EnumTest.RIGHT;
        } catch (IOException e) {
            e.printStackTrace();
            return EnumTest.NOT_RIGHT;
        }
    }

    /**
     * Socket关闭
     */
    protected void closeSocket(){
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Override
    public byte[] decompress(byte[] b) {
      return DecompressTool.decompress(b);
    }

    /**
     * 压缩
     */
    @Override
    public String compress(String s) {
        return CompressTool.compress(s);
    }

    /**
     * getData()返回数据
     */
    abstract String getData();

}
