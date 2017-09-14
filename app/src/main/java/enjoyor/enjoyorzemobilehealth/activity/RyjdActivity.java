package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;

import enjoyor.enjoyorzemobilehealth.adapter.JianKangJiaoYuAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.JYDX;
import enjoyor.enjoyorzemobilehealth.entities.JiaoYuKM;
import enjoyor.enjoyorzemobilehealth.entities.KMMX;

import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static android.util.Log.d;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/24.
 */

public class RyjdActivity extends BaseActivity{
    private ZhierCall zhierCall;
    private List<JiaoYuKM> listJiaoYuKM =new ArrayList<>();
    public static  List<KMMX> listKMMX =new ArrayList<>();
    private   List<KMMX> listNomal =new ArrayList<>();
    private List<JYDX> listJYDX =new ArrayList<>();
    private List<KMMX> listSaveData = new ArrayList<>();
    private ListView jyxm;
    private TextView bt_save;
    private TextView sj;
    private ImageView tx;
    private TextView xm;
    private TextView ch;
    private TextView br_select1;
    private TextView js_select1;
    private TextView jn1;
    private TextView jn2;
    private TextView jn3;
    private TextView jn4;
    private ImageView back;
    private LinearLayout layout;
    private CheckBox br_select;
    private CheckBox js_select;
    private CheckBox jineng1;
    private CheckBox jineng2;
    private CheckBox jineng3;
    private CheckBox jineng4;
    private Spinner sp;


    private String eduStyleID="";
    private  int j=0;
    private  int len=0;
    public static JianKangJiaoYuAdapter adapter;
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String BingQuDM;
    private String usid;
    private BRLB brlb=null;
    private String dx;
    private String jn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkjy);
        defineData();
        clickData();
        initData();
    }
    private void defineData(){
        jyxm = (ListView) findViewById(R.id.jyxm);
        bt_save = (TextView) findViewById(R.id.bt_save);
        sj = (TextView) findViewById(R.id.sj);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
        layout = (LinearLayout) findViewById(R.id.top);
        back = (ImageView) findViewById(R.id.back);
        br_select = (CheckBox) findViewById(R.id.br_select);
        js_select =(CheckBox) findViewById(R.id.js_select);
        jineng1 = (CheckBox) findViewById(R.id.jineng1);
        jineng2 = (CheckBox) findViewById(R.id.jineng2);
        jineng3 = (CheckBox) findViewById(R.id.jineng3);
        jineng4 = (CheckBox) findViewById(R.id.jineng4);
        br_select1 = (TextView) findViewById(R.id.br_select1);
        js_select1 = (TextView) findViewById(R.id.js_select1);
        jn1 = (TextView) findViewById(R.id.jn1);
        jn2 = (TextView) findViewById(R.id.jn2);
        jn3 = (TextView) findViewById(R.id.jn3);
        jn4 = (TextView) findViewById(R.id.jn4);
        sp = (Spinner) findViewById(R.id.spinner2);
        brlb=MyApplication.getInstance().getOther_brlb();
    }
    private void clickData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RyjdActivity.this,JkjyActivity.class));
                finish();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","HealthEdu");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(RyjdActivity.this,BrlbActivity.class);
                intent.putExtra("which","HealthEdu");
                intent.putExtra("eduStyleID",eduStyleID);
                startActivity(intent);
                finish();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        br_select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(br_select.isChecked()){
                    br_select.setChecked(false);
                }else{
                    br_select.setChecked(true);
                }
            }
        });
        js_select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(js_select.isChecked()){
                    js_select.setChecked(false);
                }else{
                    js_select.setChecked(true);
                }
            }
        });
        jn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jineng1.isChecked()){
                    jineng1.setChecked(false);
                }else{
                    jineng1.setChecked(true);
                }
            }
        });
        jn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jineng2.isChecked()){
                    jineng2.setChecked(false);
                }else{
                    jineng2.setChecked(true);
                }
            }
        });
        jn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jineng3.isChecked()){
                    jineng3.setChecked(false);
                }else{
                    jineng3.setChecked(true);
                }
            }
        });
        jn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jineng4.isChecked()){
                    jineng4.setChecked(false);
                }else{
                    jineng4.setChecked(true);
                }
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==Integer.parseInt(eduStyleID)-1){
                    return;
                }else{
                    String[] languages = getResources().getStringArray(R.array.healthedu);
                    switch (languages[position]){
                        case "入院":
                            eduStyleID = "1";
                            break;
                        case "住院":
                            eduStyleID = "2";
                            break;
                        case "出院":
                            eduStyleID = "3";
                            break;
                    }
                    //ToastUtils.makeToast(RyjdActivity.this, "你点击的是:"+languages[position]);
                    commitData();

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initData(){
        String[] curs = getResources().getStringArray(R.array.healthedu);
        //healthedu_spinner，此时改变的是选中后的情况，如果这里不想修改，可引用Android默认的布局，
        //比如android.R.layout.simple_spinner_item
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.healthedu_spinner, curs);
        //此处修改的部分为 点击后弹出的选择框，同上可引用自己写的布局文件，也可以使用默认布局，此处使用的是默认布局
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        BingQuDM=preferences2.getString("bqdm","");
        usid=preferences2.getString("id","");
        if(brlb==null){
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
            Intent intent1 =getIntent();
            eduStyleID=intent1.getStringExtra("eduStyleID");
        }else{
            Intent intent =getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            eduStyleID=intent.getStringExtra("eduStyleID");
            MyApplication.getInstance().setOther_brlb(null);

        }
        int spId =Integer.parseInt(eduStyleID)-1;
        sp.setSelection(spId,true);
        commitData();


    }
    private void commitData(){

        Date currentTime =new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String date = formatter.format(currentTime);

        sj.setText(date);
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        ch.setText(chuangHao + "床");
        listNomal.clear();
        j=0;
        len=0;
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0200315")
                .setMessage(NetWork.HealthEDU)
                .setCanshu(zyid+"¤"+BingQuDM)
                .setContext(this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler1,
                        new Class[]{KMMX.class});
                Log.v("login12", data);
                parser.parse(data);

            }
            @Override
            public void fail(String info) {
                Log.v("login11", "fail");
            }

        });
    }

    MyXmlHandler xmlHandler1=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    KMMX ks = null;

                    for(int i=0;i<listNomal.size();i++){
                        ks=listNomal.get(i);
                    }
                    listJiaoYuKM.clear();
                    listKMMX.clear();
                    listJYDX.clear();
                    Log.d("111-----------",eduStyleID);
                    zhierCall = (new ZhierCall())
                            .setId(usid)
                            .setNumber("0200314")
                            .setMessage(NetWork.HealthEDU)
                            .setCanshu(eduStyleID+"¤"+BingQuDM)
                            .setContext(RyjdActivity.this)
                            .setPort(5000)
                            .build();
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            StringXmlParser parser = new StringXmlParser(xmlHandler,
                                    new Class[]{JiaoYuKM.class, KMMX.class,JYDX.class});

                            Log.v("login11", data);
                            parser.parse(data);

                        }
                        @Override
                        public void fail(String info) {
                            Log.v("login11", "fail");
                        }

                    });
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listNomal.add((KMMX) msg.obj);
                            if(j!=0){
                                len=listNomal.get(j-1).getITEM_ID().length()+len;
                                String str=listNomal.get(j).getITEM_ID().substring(len);
                                listNomal.get(j).setITEM_ID(str);
                            }
                            j++;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    List<KMMX> listNomal1 =new ArrayList<>();
                    for(int i=0;i<listJiaoYuKM.size();i++){
                        for(int j=0;j<listKMMX.size();j++){
                            if(listJiaoYuKM.get(i).getITEM_SUB_CLASS_ID().equals(listKMMX.get(j).getITEM_SUB_CLASS_ID())){
                                listKMMX.get(j).setITEM_SUB_CLASS_NAME(listJiaoYuKM.get(i).getITEM_SUB_CLASS_NAME());
                            }
                        }
                    }
                    adapter =new JianKangJiaoYuAdapter(RyjdActivity.this,listJiaoYuKM,listKMMX,listNomal);
                    jyxm.setAdapter(adapter);
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJiaoYuKM.add((JiaoYuKM) msg.obj);
                            break;
                        case 1:
                            listKMMX.add((KMMX) msg.obj);
                            break;
                        case 2:
                            listJYDX.add((JYDX) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private void saveData(){

        listSaveData.clear();
        for(int i=0;i<listKMMX.size();i++) {
            if (listKMMX.get(i).isChecked()) {
                listSaveData.add(listKMMX.get(i));
            }
        }
        dx="";
        jn="";
        if(!br_select.isChecked()&&!js_select.isChecked()){
            ToastUtils.makeToast(MyApplication.getContext(), "请选择教育对象");
            return;
        }else if(br_select.isChecked()&&!js_select.isChecked()){
            dx="病人";
        }else if(!br_select.isChecked()&&js_select.isChecked()){
            dx="家属";
        }
        else {
            dx="病人和家属";
        }
        StringBuilder st = new StringBuilder();

        if(jineng1.isChecked()){
            st.append("和"+"能复述");
        }
        if(jineng2.isChecked()){
            st.append("和"+"能模仿");
        }
        if(jineng3.isChecked()){
            st.append("和"+"能操作");
        }if(jineng4.isChecked()){
            st.append("和"+"能解释");
        }
        if(st.toString().equals("")){

        }else{
            jn = st.toString().substring(1);
        }



        if (listSaveData.size()==0) {
            ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
        } else {
            CenterDialog centerDialog = new CenterDialog(RyjdActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
            centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                @Override
                public void OnCenterItemClick(CenterDialog dialog, View view) {
                    if (view.getId() == R.id.bt_yes) {
                       // ToastUtils.showLoading(RyjdActivity.this);
                        netWorkUpdata(listSaveData);
                    }

                }
            });
            centerDialog.show();

        }
    }
    private void netWorkUpdata(List<KMMX> listSaveData){
        Date currentTime =new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String date = formatter.format(currentTime);
        String xml=null;
        try {
            xml=createXml(listSaveData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = MyApplication.getInstance().getYhxm();
        StringBuilder s=new StringBuilder();
        s.append(BingQuDM+"*");
        s.append(zyid+"*");
        s.append(name+"*");
        s.append(dx+"*");
        s.append(jn+"*");
        s.append(date+"*");
        s.append(xml);
        d("----------",s.toString());
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0200303")
                .setMessage(NetWork.HealthEDU)
                .setCanshu(s.toString())
                .setContext(RyjdActivity.this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Toast.makeText(RyjdActivity.this,"成功", Toast.LENGTH_LONG).show();
                commitData();
            }

            @Override
            public void fail(String info) {
                Toast.makeText(RyjdActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    public String createXml( List<KMMX> list) throws Exception
    {
        for(int i=0;i<list.size();i++)
        {
            list.get(i).getITEM_ID();
        }
        //1.头部

        StringBuilder s=new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>"+"\r\n");
        s.append("<DS Name=\"17274517\" Num=\"1\">"+"\r\n");
        s.append("<DT Name=\"tab_Item\" Num=\""+list.size()+"\">"+"\r\n");

        //2.中部
        for(int j=0;j<list.size();j++)
        {
            Field[] fields=list.get(j).getClass().getDeclaredFields();
            KMMX km =list.get(j);
            String itmeClassName ="";
            if(km.getITEM_CLASS_ID().equals("1")){
                itmeClassName="入院阶段";
            }else if(km.getITEM_CLASS_ID().equals("2")){
                itmeClassName="住院阶段";
            }else{
                itmeClassName="出院指导";
            }
            s.append("<DR Name=\"39155797\" Num=\"7\">"+"\r\n");
            s.append("<DC Name=\"BOOLOPERATED\" Num=\"0\">"+true+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_CLASS_NAME\" Num=\"0\">"+itmeClassName+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_SUB_CLASS_NAME\" Num=\"0\">"+km.getITEM_SUB_CLASS_NAME()+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_NAME\" Num=\"0\">"+km.getITEM_NAME()+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_CLASS_ID\" Num=\"0\">"+km.getITEM_CLASS_ID()+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_SUB_CLASS_ID\" Num=\"0\">"+km.getITEM_SUB_CLASS_ID()+"</DC>"+"\r\n");
            s.append("<DC Name=\"ITEM_ID\" Num=\"0\">"+km.getITEM_ID()+"</DC>"+"\r\n");

            s.append("</DR>"+"\r\n");
        }

        //3.尾部
        s.append("</DT>"+"\r\n");
        s.append("</DS>");

        System.out.print(s.toString()+"xxxxsdadadasx");
        return s.toString();
    }
}
