package my_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Administrator on 2016/12/26.
 */

public class Client {
    public static void clientInfo()
    {
        Socket client=null;
        try{
            client=new Socket("localhost",5000);
            BufferedReader buf=new BufferedReader(new InputStreamReader(client.getInputStream()));
            String info=buf.readLine();
            System.out.print("服务器发送的信息:"+info);
            client.close();
            buf.close();
        }catch (Exception e)
        {

        }
    }
    public static void main(String[] args)
    {
        clientInfo();
    }
}
