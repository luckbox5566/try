package com.example.my_xml;

import android.os.Handler;

import com.example.my_xml.handlers.MyParseHandler;

import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by chenlikang
 */

 public class StringXmlParser {

    private Handler handler;
    private Class[] cla;

    public StringXmlParser(Handler handler, @SuppressWarnings("rawtypes") Class[] cla) {
        this.handler = handler;
        this.cla = cla;
    }

    /**
     * @param str 从服务端获取的原始数据
     */
    public void parse(String str) {
        if (str != null) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
                StringReader read = new StringReader(str);
                InputSource mInputSource = new InputSource(read);
                parser.parse(mInputSource,new MyParseHandler(cla,handler));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
