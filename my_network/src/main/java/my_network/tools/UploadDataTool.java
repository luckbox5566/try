package my_network.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *  工具类：向服务器上传参数。
 */

public class UploadDataTool {

    public static void uploadData(String data, BufferedWriter writer, BufferedReader reader){
        int length=data.length();
        int count=(int)Math.ceil(length/256.0);
        String firstMessage="";
        for (int i = 0; i < count; i++) {
            if (i == (count - 1)) {
                // 截取指定部分数据
                firstMessage = data.substring(i * 256);
                System.out.print("消息头截取1:"+firstMessage+"\n");
                DoMesTool.doMes(writer, reader, count, firstMessage, i);
                break;
            }
            // 截取指定部分数据
            firstMessage = data.substring(i * 256, (i + 1) * 256);
            System.out.print("消息头截取2:"+firstMessage+"\n");
            DoMesTool.doMes(writer, reader, count, firstMessage, i);
        }
        
    }
}
