package enjoyor.enjoyorzemobilehealth.other;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by chenlikang
 */

public class SocketTest {
    public void main(String[] args)
    {
        //获取本机的InetAddress实例
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        address.getHostName();//获取计算机名
        address.getHostAddress();//获取IP地址
        byte[] bytes = address.getAddress();//获取字节数组形式的IP地址,以点分隔的四部分
        //System.out.print();

        //获取其他主机的InetAddress实例
        try {
            InetAddress address2 =InetAddress.getByName("其他主机名");
            InetAddress address3 =InetAddress.getByName("192.168.7.121");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
