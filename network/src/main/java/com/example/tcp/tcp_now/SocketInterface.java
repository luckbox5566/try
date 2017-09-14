package com.example.tcp.tcp_now;

/**
 * Created by youxi on 2016-10-9.
 */

public interface SocketInterface {
    //数据传输正确
    public static final int CORRECT = 0;
    //数据传输错误
    public static final int ERROR = -1;
    //分隔符
    public static final String SEPARATE="ddd";

    //返回通信的数据
    String getData();

    void start();
}
