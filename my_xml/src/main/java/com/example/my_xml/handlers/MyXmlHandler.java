package com.example.my_xml.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 *
 * Created by chenlikang
 */

public abstract class MyXmlHandler extends Handler {
    private WeakReference<Object> weekPeference;

    public MyXmlHandler(Object obj, Context context) {
        weekPeference = new WeakReference<Object>(obj);
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        Object obj = weekPeference.get();
        if (null != obj) handlerMessage(msg);

    }

    /**
     * 处理消息
     *
     * @param msg
     */
    public abstract void handlerMessage(Message msg);

}
