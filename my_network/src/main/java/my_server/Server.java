package my_server;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/12/26.
 */

public class Server {
    public static void serverInfo()
    {
        ServerSocket server=null;
        Socket client=null;
        PrintStream out=null;
        InputStream in=null;

        try{
            server=new ServerSocket(5000);
            System.out.print("服务器正在连接客户端..."+"\n");
            client=server.accept();

            System.out.print("向客户端返回消息..."+"\n");
            String info="欣赏荒野 回归自然";
            out=new PrintStream(client.getOutputStream());
            out.println(info);

          /*  System.out.print("读取客户端信息..."+"\n");
            in=client.getInputStream();
            byte[] b=new byte[200];
            in.read(b);
            String s=new String(b);
            System.out.print(s+"\n");*/

            out.close();
            client.close();
            server.close();
        }catch (Exception e){
            System.out.print("出错"+"\n");
        }
    }

    public static void main(String[] args)
    {
        serverInfo();
    }
}
