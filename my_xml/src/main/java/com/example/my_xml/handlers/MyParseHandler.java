package com.example.my_xml.handlers;

import android.os.Handler;
import android.os.Message;

import com.example.my_xml.FanShe;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 用户XML解析的Handler类
 *
 * Created by chenlikang
 */

public class MyParseHandler extends DefaultHandler {
    public static final int END = 1;
    public static final int NODE = 2;
    FanShe fanShe;
    Handler handler;

    public MyParseHandler(@SuppressWarnings("rawtypes") Class[] cla, Handler handler) {
        this.cla = cla;
        this.handler=handler;
        fanShe = new FanShe();
    }

    @SuppressWarnings("rawtypes")
    private Class[] cla;
    Object obj;
    FanShe ref = new FanShe();
    Map<String, String> valueMap;
    @SuppressWarnings("rawtypes")
    List fieldNameList;
    int valueNum = 0;
    int drNum = 0;
    int dcNum = 1;
    int dtNum = -1;
    private String tagName;
    String dcAtt;

    /**
     * 解析开始调用这个函数
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /**
     * 解析结束调用这个函数
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        if (handler != null)
            handler.sendEmptyMessage(END);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        tagName = localName;

        /**
         * DT最外面一层
         */
        if ("DT".equals(localName)) {
            dtNum++;
        }

        /**
         * DR第二层
         */
        if ("DR".equals(localName)) {
            try {
                obj = cla[dtNum].newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fieldNameList = ref.getPropertyNames(cla[dtNum]);
            valueMap = new TreeMap<String, String>();
            int num = Integer.parseInt(attributes.getValue("Num"));
            String name = attributes.getValue("Name");
            valueMap.put("DR", name);
            valueNum++;
            drNum++;
        }

        /**
         * DC第三层
         */
        if ("DC".equals(localName)) {
            dcNum++;
            dcAtt = attributes.getValue("Name");
            valueMap.put(dcAtt, "");
            valueNum++;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if ("DR".equals(localName)) {
            List<String> list = new ArrayList<String>();
            try {
                for (int i = 0, len = fieldNameList.size(); i < len; i++) {
                    Object key = fieldNameList.get(i);
                    if (valueMap.containsKey(key)) {
                        list.add(valueMap.get(key).toString());
                    } else {
                        fieldNameList.remove(key);
                        i--;
                        len--;
                    }
                }
                ref.method(obj, fieldNameList, list);
                if (handler != null) {
                    Message msg = Message.obtain(handler);
                    msg.what = NODE;
                    msg.arg1 = dtNum;
                    msg.obj = obj;
                    msg.sendToTarget();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        tagName = "";
    }

    String preAtt = "";
    String preDate = "";

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        String data = new String(ch, start, length);
        if ("DC".equals(tagName)) {
            if (dcAtt.equals(preAtt)) {
                data = preDate + data;
                try {
                    fanShe.method(obj, dcAtt, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            valueMap.put(dcAtt, data);
            preAtt = dcAtt;
            preDate = data;
        }
    }
}
