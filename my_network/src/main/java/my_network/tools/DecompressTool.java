package my_network.tools;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/12/28.
 */

public class DecompressTool {
    public static byte[]  decompress(byte[] b)
    {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        inflater.setInput(b);
        byte[] buff = new byte[1024];
        int byteNum = 0;
        try {
            while (!inflater.finished()) {
                try {
                    byteNum = inflater.inflate(buff);
                    aos.write(buff, 0, byteNum);
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
            }
            return aos.toByteArray();
        } catch (Exception e) {
            System.out.print("decompress()方法错误:"+e.toString()+"\n");
            return null;
        }
    }
}
