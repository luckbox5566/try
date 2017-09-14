package my_network.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * 工具类：循环向服务端发送消息
 */

public class DoMesTool {
    public static void doMes(BufferedWriter writer, BufferedReader reader, int count, String firstMessage, int i)
    {
        try {
            System.out.print("count:"+count+"fitstMessage:"+firstMessage+"i"+i+"\n");
            /*
               向服务端写一次数据
             */
            writer.write("GPStart@" + i + "@" + count + "@" + firstMessage + "@GPEnd");
            writer.flush();
            /*
               读取服务端的数据并计算长度
             */
            char[] readChars = new char[256 + 32];
            int len = reader.read(readChars);
            String receiveStr = String.valueOf(readChars, 0, len);
            System.out.print("doMes()从服务端度的数据"+receiveStr+"\n");
            /*
               进行迭代，直到不满足条件
             */
            if (!"true".equals(receiveStr.split("@")[3])) {
                doMes(writer, reader, count, firstMessage, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("network",e.toString()+"传输消息头或获取数据错误");
            System.out.print("doMes()"+"方法错误"+"\n");
        }
    }
}
