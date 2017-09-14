package com.example.tcp.xml;

/**
 * Created by chenlikang on 2016-10-11.
 */

public class StringXmlParser {
    public static final int END = 1;
    public static final int NODE = 2;
    private Handler handler;
    private Class[] cla;

    public StringXmlParser(Handler handler,
                           @SuppressWarnings("rawtypes") Class[] cla) {
        this.handler = handler;
        this.cla = cla;

    }

    public StringXmlParser() {

    }

    public void parse(String str) {
        if (str != null) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
                StringReader read = new StringReader(str);
                InputSource mInputSource = new InputSource(read);
                parser.parse(mInputSource, new StringParseHandler(cla));
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private class StringParseHandler extends DefaultHandler {
        FanShe fanShe;

        public StringParseHandler(@SuppressWarnings("rawtypes") Class[] cla) {
            // this.brxx=brxx;
            this.cla = cla;
            fanShe = new FanShe();
        }

        @SuppressWarnings("rawtypes")
        private Class[] cla;
        Object obj;
        // BingRenXX brxx;
        FanShe ref = new FanShe();
        /**
         * ֵ�б�
         */
        Map<String, String> valueMap;

        /**
         * �����б�
         */
        @SuppressWarnings("rawtypes")
        List fieldNameList;
        // public StringParseHandler(){
        // List fieldNameList=ref.getPropertyNames(brlb.getClass());
        // Map<String,String> fieldMap=new TreeMap<String, String>();
        //
        // }
        // Map<Integer,Map<String,String>> fieldMap=new HashMap<Integer,
        // Map<String,String>>();
        // DT dt;
        //
        // DC dc;
        int valueNum = 0;
        // int dtNum = 0;
        int drNum = 0;
        int dcNum = 1;
        int dtNum = -1;
        private String tagName;
        // Map<Integer, DT> dtmap = new HashMap<Integer, DT>();

        String dcAtt;

        @Override
        public void startDocument() throws SAXException {
            // TODO Auto-generated method stub
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            // TODO Auto-generated method stub
            super.endDocument();
            if (handler != null)
                handler.sendEmptyMessage(END);
            // if (handler != null) {
            // Message msg = Message.obtain(handler);
            // msg.what = 0;
            // msg.obj = taitou;
            // msg.sendToTarget();
            // }
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            // TODO Auto-generated method stub
            super.startElement(uri, localName, qName, attributes);
            // ��ס��ʼ��ǩ�ı�ǩ��
            tagName = localName;
            // �����ʼ����music��ǩ����ʵ����music���󣬲���ȡ��id
            // if ("DS".equals(localName)) {
            // ds = new DS();
            // int num = Integer.parseInt(attributes.getValue("Num"));
            // // Log.i("num", "" + num);
            // ds.setNum(num);
            // String name = attributes.getValue("Name");
            // ds.setName(name);
            // Log.i("name", "" + name);
            // }else if ("DT".equals(localName)) {
            // dt = new DT();
            // int num = Integer.parseInt(attributes.getValue("Num"));
            //
            // // Log.i("num", "" + num);
            // dt.setNum(num);
            // String name = attributes.getValue("Name");
            // dt.setName(name);
            // Log.i("name", "" + name);
            //
            // dtmap.put(dtNum, dt);
            // dtNum++;
            // ds.setMap(dtmap);
            // }
            if ("DT".equals(localName)) {

                dtNum++;
            }

            if ("DR".equals(localName)) {
                try {
                    // ������ʵ��

                    obj = cla[dtNum].newInstance();

                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // �õ���������Լ���
                fieldNameList = ref.getPropertyNames(cla[dtNum]);
                // ������Ժ�����ֵ�ļ���
                valueMap = new TreeMap<String, String>();
                // dc = new DC();
                int num = Integer.parseInt(attributes.getValue("Num"));

                // Log.i("num", "" + num);
                // brlb.setNum(num);
                String name = attributes.getValue("Name");
                // brlb.setName(name);
                // value.add(name);
                valueMap.put("DR", name);
                valueNum++;
                // Log.i("name", "" + name);
                // brxxMap.put(drNum, obj);
                drNum++;

            }
            if ("DC".equals(localName)) {
                dcNum++;
                dcAtt = attributes.getValue("Name");

                //	Log.i("dcAtt", dcAtt);
                /**
                 * ��Ϊ�ȵ���characters����startElement������/>��β�ǣ�
                 * ����㷨���ϡ������õ�������valueMap����
                 */
                // if(dcNum>valueNum){
                // // valueMap.put(dcNum, valueMap.get(valueMap));
                // value.add(value.get(valueNum));
                // value.set(valueNum, "");
                // for(int i=valueNum;i<dcNum;i++){
                // valueMap.put(valueNum, "");
                // }
                // valueNum=dcNum;
                // }
                // ���Լ������ȷ�������
                valueMap.put(dcAtt, "");
                valueNum++;
                // int num = Integer.parseInt(attributes.getValue("Num"));

            }

        }

        @SuppressWarnings("unchecked")
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            // TODO Auto-generated method stub
            super.endElement(uri, localName, qName);
            if ("DR".equals(localName)) {
                // ������������Ӧ������ֵ
                List<String> list = new ArrayList<String>();
                try {
                    // Iterator<String> iterator = valueMap.values().iterator();
                    // while (iterator.hasNext()) {
                    // list.add(iterator.next());
                    //
                    // }
                    // ������
                    for (int i = 0, len = fieldNameList.size(); i < len; i++) {

                        // Log.i("fieldNameList" + i, fieldNameList.get(i)
                        // .toString());
                        // Log.i("valueList:" + i,
                        // valueMap.get(fieldNameList.get(i)).toString());
                        // ������
                        Object key = fieldNameList.get(i);
                        if (valueMap.containsKey(key)) {
                            list.add(valueMap.get(key).toString());
                        } else {// ȥ��xml�ַ���������û�е�����
                            fieldNameList.remove(key);
                            i--;
                            len--;
                        }

                    }
                    ref.method(obj, fieldNameList, list);

                    // ��һ����DRΪ�ڵ��������󣬷�����Ϣ
                    if (handler != null) {
                        Message msg = Message.obtain(handler);
                        msg.what = NODE;
                        // �������ص����ĸ���/��
                        msg.arg1 = dtNum;
                        msg.obj = obj;
                        msg.sendToTarget();
                    }
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // ����ǩ��������ʱ����tagName�ÿ�
            // Log.i("local name: qname", localName + ":" + qName);
            // if (null == qName || "".equals(qName)) {
            // value.add(null);
            // }
            tagName = "";

            // ���music��ǩ�����������򽫸�music���� ���͵����߳�
            // if ("music".equals(localName)) {
            // if (handler != null) {
            // Message msg = Message.obtain(handler);
            // msg.what = 0;
            // msg.obj = music;
            // msg.sendToTarget();
            // }
            // }
            // Log.i("musics", "get a music");
        }

        // ��char[] ch���ʱ���ܹ�ƴ��
        String preAtt = "";
        String preDate = "";

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            // TODO Auto-generated method stub
            super.characters(ch, start, length);
            // ��ȡ��ǩ�е�����
            String data = new String(ch, start, length);
            // �ж���ǩ����������������Ӧ�Ĳ���
            // if ("DC".equals(tagName)) {
            // if ("YiYuanMC".equals(dcAtt))
            // taitou.setYiYuanMC(data);
            // else if ("GongSiMC".equals(dcAtt))
            // taitou.setGongSiMC(data);
            if ("DC".equals(tagName)) {
                // if (null != data || !"".equals(data))
                // value.add(data);
                // else
                // value.add(null);
                // valueMap.remove(valueNum);

                // Log.i(dcAtt, data);

                if (dcAtt.equals(preAtt)) {
                    data = preDate + data;
                    // Log.i("----------", "--------");
                    // ���÷��䷽������ͬһ���������ظ���������ʱ�����÷��䷽��ʹ����ֵ�ۼ�
                    // �Ա��ܽ�������ȷ������
                    // ��Ϊ����д�������ǰ��"JiHuaZXSJ"��������������������Σ�
                    // Ϊ2012-6-15 21:32:0��0������2012-6-15 21:32:��00
                    try {
                        fanShe.method(obj, dcAtt, data);
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                valueMap.put(dcAtt, data);
                // ����ǰ���ԣ���Ϊ������׼��
                preAtt = dcAtt;
                // ����ǰ����ֵ
                preDate = data;
                //
            }
        }

    }

}
