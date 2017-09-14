package my_network.tools;

import android.util.Base64;
import android.util.SparseArray;

import static my_network.tools.DecompressTool.decompress;

/**
 * Created by Administrator on 2016/12/28.
 */

public class DecodeMapTool {
    public static  String decodeMap(SparseArray<String> map)
    {
        try {
            String lastdata="";
            for (int i = 0; i < map.size(); i++) {
                lastdata += map.get(i);
            }
            System.out.print("map的个数："+map.size()+"\n"+"map解析出来的string为:"+lastdata+"\n");
            byte[] lastBytes = Base64.decode(lastdata.getBytes("Unicode"), Base64.DEFAULT);
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
            System.out.print("未分割前的数据："+"\n"+data+"\n");

            return data;
        }catch (Exception e)
        {
            return null;
        }
    }

}
