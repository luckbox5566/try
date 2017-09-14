package my_network.tools;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * 工具类：把参数进行压缩和加密
 */

public class CompressTool {
    public static String compress(String canshu)
    {
        try {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        Deflater deflater=new Deflater(Deflater.BEST_SPEED);
        DeflaterOutputStream dzip=new DeflaterOutputStream(baos,deflater);

        byte[] b=canshu.getBytes("UTF-16");
        if(canshu!=""){
            byte[] b2=new byte[b.length-2];
            System.arraycopy(b,2,b2,0,b2.length);
            b=b2;
        }
        dzip.write(b);
        dzip.close();
        //得到压缩后的字节
        byte[] comBytes=baos.toByteArray();
        String result= Base64.encodeToString(comBytes,Base64.DEFAULT);
        System.out.print("param:"+result+"\n");
        return result;

    } catch (Exception e) {
        System.out.print("compress()方法错误:"+e.toString()+"\n");
        return  null;
    }
    }

    public static void main(String[] args)
    {
        String canshu="1000"+","+"123"+",0,20";
        System.out.print(canshu+"\n");
        System.out.print("compress的结果为:"+compress(canshu));
    }
}
