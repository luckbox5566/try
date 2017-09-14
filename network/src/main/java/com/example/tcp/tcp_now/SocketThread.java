package com.example.tcp.tcp_now;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.BuddhistCalendar;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * Created by chenlikang on 2016-9-20.
 */

public class SocketThread extends Thread implements SocketInterface {

    private int zongCount=0;//总传递次数
    private int falseCount=0;//错误的传递次数
    private String id;
    private String number;
    private String moKuai;
    private String canshu="";//参数
    private Context context;
    private String myLastData="";//从服务器传过来的最终数据

    private Handler handler;
    private int port;
    /**
     *两个构造函数
     * @param handler
     * @param id 登陆系统人的id
     * @param number 要执行的方法，比如0300202
     * @param message 要执行的模块
     * @param canshu  要执行的参数
     * @param context
     * port 为端口，默认为5000
     */
    public SocketThread(Handler handler,String id,String number,
                        String message,String canshu,Context context) {
        this(handler, id, number, message, canshu, context, 5000);
    }

    public SocketThread(Handler handler,String id,String number,
                        String message,String canshu,Context context,int port) {
        this.handler = handler;
        this.id = id;
        this.number = number;
        this.moKuai = message;
        this.canshu = canshu;
        this.context = context;
        this.port = port;
    }
    //获得从服务器传递过来的最终数据
    @Override
    public String getData() {
        return myLastData;
    }

    //获得总传递次数
    public int getZongCount() {
        return zongCount;
    }
    //获得错误的传递次数
    public int getFalseCount() {
        return falseCount;
    }

    public void run(){
        try {
            //把message以deflater压缩
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            Deflater deflater=new Deflater(Deflater.BEST_SPEED);
            DeflaterOutputStream dzip=new DeflaterOutputStream(baos,deflater);
            //参数转化
            byte[] canshubytes=canshu.getBytes("UTF-16");
            if(canshu!=""){
                byte[] canshu2bytes=new byte[canshubytes.length-2];
                System.arraycopy(canshubytes,2,canshu2bytes,0,canshu2bytes.length);
                canshubytes=canshu2bytes;
            }
            dzip.write(canshubytes);
            dzip.close();
            //得到压缩后的字节
            byte[] comBytes=baos.toByteArray();
            //laststr是经过Base64转义成的字符串，表示要传递的参数
            String canshuStr= Base64.encodeToString(comBytes,Base64.DEFAULT);

            /*
            String ip=null;
            //得到ip地址
            if(context!=context){
                SharedPreferences sp=context.getSharedPreferences(Configuration.IPFILE,Context.MODE_PRIVATE);
                ip=sp.getString(Configuration.IPNODE,"192.168.19.144");
            }
            */

            //--




            Socket socket=new Socket(ip,port);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-16"));
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //要传递的消息头的实际值
            String firstStr=id+SEPARATE+"高坡"+SEPARATE+"你电脑的mac地址"+SEPARATE+moKuai
                    +moKuai+SEPARATE+number+SEPARATE+canshuStr.length();
            //消息头的长度
            int length=firstStr.length();
            //count:要传递的段数，以256个字符为一段
            int count=(int)Math.ceil(length/256.0);
            //要分段传递的消息头
            String firstMessage="";
            for(int i=0;i<count;i++){
                if(i==(count-1)){
                    firstMessage=firstStr.substring(i*256);
                    writeAndRead(writer,reader,count,firstMessage,i);
                    break;
                }
                firstMessage=firstStr.substring(i*256,(i+1)*256);
                writeAndRead(writer,reader,count,firstMessage,i);
            }
            //开始传递参数，length:firstStr消息头的长度
            length=canshuStr.length();
            //count:要传递的段数，以256个字符为一段
            count=(int)Math.ceil(length/256.0);
            //要分段传递的参数
            String canshuMessage="";

            for(int i=0;i<count;i++){
                if(i==(count-1)){
                    canshuMessage=canshuStr.substring(i*256);
                    writeAndRead(writer,reader,count,canshuMessage,i);
                    break;;
                }
                //截取指定部分数据
                canshuMessage=canshuStr.substring(i*256,(i+1)*256);
                writeAndRead(writer,reader,count,canshuMessage,i);
            }
           
		   
		   //------------------下一个函数----------
            String[] handerItems;
            while(true){
                char[] dataHander=new char[256+32];
                length=reader.read(dataHander);
                String dataHeader=new String(dataHander,0,length);
                handerItems=dataHeader.split("@");
                if(("GPStart".equals(handerItems[0].trim()))
                        && ("GPEnd".equals(handerItems[4].trim()))){
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                    break;
                }else{
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                }
            }
            int dataLen = Integer.parseInt(handerItems[3].trim());
            count = (int) Math.ceil(dataLen / 256.0);
            SparseArray<String> map = new SparseArray<String>();
            // 得到服务器传输过来的数据
            for (int i = 0; i < count; i++)
            {
                char[] dataChars = new char[256 + 32];
                int len = reader.read(dataChars);
                String dataStr = new String(dataChars, 0, len);
                String[] dataItems = dataStr.split("@");
                if ("GPStart".equals(dataItems[0].trim())
                        && "GPEnd".equals(dataItems[4].trim())) {
                    map.put(i, dataItems[3].trim());
                    writer.write("GPStart@1@1@true@GPEnd");
                    writer.flush();
                    zongCount++;
                } else {
                    writer.write("GPStart@1@1@false@GPEnd");
                    writer.flush();
                    zongCount++;
                    falseCount++;
                    i--;
                }
            }
            socket.close();


            //---------------------------------------------------------------------
            String lastData = "";
            for (int i = 0; i < map.size(); i++) {
                lastData += map.get(i);
            }
            byte[] lastBytes = Base64.decode(lastData.getBytes("Unicode"),
                    Base64.DEFAULT);
            byte[] newbytes = decompress(lastBytes);
            byte[] bytes16 = new byte[newbytes.length];
            int index = 0;
            for (int i = 1; i < newbytes.length; i += 2) {
                if (0 == newbytes[i] && 0 == newbytes[i - 1])
                    continue;
                bytes16[index] = newbytes[i];
                bytes16[index + 1] = newbytes[i - 1];
                index += 2;
            }
            // 第三种算法得到的data
            String data = new String(bytes16, 0, index, "UTF-16");
            Log.i("data", data);
            String[] items = data.split(SEPARATE);
            // 以ReturnCode判断正确与否
            if ((CORRECT + "").equals(items[0]) && handler != null) {
                // Log.i("message", "message"); // Message msg =
                // Message.obtain(handler); // msg.what = 0; // msg.obj =
                // data.split(";")[2]; // msg.sendToTarget(); // 发送消息得到正确的数据
                myLastData = items[2];

                // if (null !=YiZhuAct.progressBar) //
                // YiZhuAct.progressBar.setProgress(100);
                handler.sendEmptyMessage(CORRECT);
            } // 接收数据失败时
            if ((ERROR + "").equals(items[0]) && handler != null) {
                // Log.i("message","message"); // Message msg =
                // Message.obtain(handler); // msg.what = 0; // msg.obj =
                // data.split(";")[2]; // msg.sendToTarget();
                // 发送消息得到数据
                myLastData = items[1];
                // if\(null != YiZhuAct.progressBar)
                // //YiZhuAct.progressBar.setProgress(100);
                handler.sendEmptyMessage(ERROR);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送错误的原因
    private void sendExceptionMessage(Exception e){
        myLastData=e.getMessage();
        if(handler=!null){
            handler.sendEmptyMessage(ERROR);
        }
    }

    private void writeAndRead(BufferedWriter writer, BufferedReader reader,
                              int count, String firstMessage, int i) throws IOException {
        zongCount++;
        writer.write("GPStart@" + i + "@" + count + "@" + firstMessage
                + "@GPEnd");
        writer.flush();
        char[] readChars = new char[256 + 32];
        int len = reader.read(readChars);
        String receiveStr = String.valueOf(readChars, 0, len);
        if (!"true".equals(receiveStr.split("@")[3])) {
            writeAndRead(writer, reader, count, firstMessage, i);
            falseCount++;
        }

    }

    /**
     * 用zip算法解压压缩字节
     * @param zipByte 输入的压缩字节
     * @return 解压好的压缩字节
     * @throws IOException
     */
    public byte[] decompress(byte[] zipByte) throws IOException {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        inflater.setInput(zipByte);
        byte[] buff = new byte[1024];
        int byteNum = 0;
        while (!inflater.finished()) {
            try {
                byteNum = inflater.inflate(buff);
                aos.write(buff, 0, byteNum);
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
        return aos.toByteArray();
    }
}
