package enjoyor.enjoyorzemobilehealth.activity.infosearch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.activity.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.adapter.XunHuiJLAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiCZ;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.views.MyInterface;
import enjoyor.enjoyorzemobilehealth.views.XunHuiAddDialog;
import enjoyor.enjoyorzemobilehealth.views.XunHuiCZDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/17.
 * //巡回查询
 */

public class XhcxActivity extends BaseActivity{
    private DateUtil instance;
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private ZhierCall zhierCall;
    private LinearLayout xhxz;
    private TextView xhlx;
    private ListView list_xhjl;
    private LinearLayout top;
    private List<XunHuiCZ> listXunHui= new ArrayList<>();
    private List<XunHuiJL> listXunHuiJL= new ArrayList<>();
    private XunHuiCZDialog dialog;
    private XunHuiAddDialog dialog1;
    private ImageView xhjl_add;
    private TextView bq;
    private TextView xm;
    private TextView ch;
    private TextView cxsj;
    private TextView cxsj2;
    private LinearLayout sjxz;
    private ImageView tx;
    private LinearLayout nodata;
    private ImageView back;
    private String sj;
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String BingQuDM;
    private String userID;
    private int flag=0;
    private BRLB brlb=null;
    private MyInterface myListener = new MyInterface(){
        @Override
        public void method(String text){
            xhlx.setText(text);
        }
        @Override
        public void updateData(String zy){
            zyid = zy;
            MyApplication instance = MyApplication.getInstance();
            int i;
            for(i = 0;i<instance.getListBRLB().size();i++){
                if(zyid.equals(instance.getListBRLB().get(i).getBINGRENZYID())){
                    bingRenXM =instance.getListBRLB().get(i).getXINGMING();
                    bingRenXB =instance.getListBRLB().get(i).getXINGBIE();
                    chuangHao =instance.getListBRLB().get(i).getCHUANGWEIHAO();
                    break;
                }
            }
            Date currentTime =new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd");
            sj=formatter.format(currentTime);

            commitData();

        }
    };

    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_xhcx);
        defineData();
        clickData();
        initData();
        commitData();
    }

    private void defineData(){
        xhxz = (LinearLayout) findViewById(R.id.xhxz);
        xhlx = (TextView) findViewById(R.id.xhlx);
        //xhjl_add = (ImageView) findViewById(R.id.xhjl_add);
        list_xhjl = (ListView) findViewById(R.id.list_xhjl);
        bq = (TextView) findViewById(R.id.bq);
        top = (LinearLayout) findViewById(R.id.top);
        xm =(TextView) findViewById(R.id.mz);
        ch =(TextView) findViewById(R.id.ch);
        tx =(ImageView) findViewById(R.id.tx) ;
        cxsj = (TextView) findViewById(R.id.cxsj);
        cxsj2= (TextView) findViewById(R.id.cxsj2);
        sjxz = (LinearLayout) findViewById(R.id.sjxz);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        back = (ImageView) findViewById(R.id.back);
        brlb=MyApplication.getInstance().getOther_brlb();
    }
    private void clickData(){
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(XhcxActivity.this,HomePageActivity.class));
                finish();
            }
        });
        bq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XhcxActivity.this,BqxhcxActivity.class));
                finish();
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","XunHuiCX");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(XhcxActivity.this,BrlbActivity.class);
                intent.putExtra("which","XunHuiCX");
                startActivity(intent);
                finish();
            }
        });
        //开始时间选择,不需要请求数据
        cxsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(XhcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        sj=format.format(date);
                        cxsj.setText(sj);
                        //commitData();
                    }
                }).setDate(instance.getCalendar(cxsj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });
        //结束时间选择，请求数据
        cxsj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/MM/dd");
                //时间选择器
                pvTime = new TimePickerView.Builder(XhcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        sj=format.format(date);
                        cxsj2.setText(sj);
                        commitData();
                    }
                }).setDate(instance.getCalendar(cxsj2.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .build();
                pvTime.show();
            }
        });

        /*xhjl_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xhlx.getText().toString().trim().equals("请选择")){
                    Toast.makeText(XhcxActivity.this, "请选择巡回类型", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    dialog1 =new XunHuiAddDialog(XhcxActivity.this,xhlx.getText().toString(),BingQuDM,myListener);
                    dialog1.show();
                }

            }
        });*/
        xhxz.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                listXunHui.clear();
                zhierCall = (new ZhierCall())
                        .setId(userID)
                        .setNumber("03043003")
                        .setMessage(NetWork.GongYong)
                        .setCanshu("1")
                        .setContext(XhcxActivity.this)
                        .setPort(5000)
                        .build();
                zhierCall.start(new NetWork.SocketResult() {
                    @Override
                    public void success(String data) {
                        StringXmlParser parser = new StringXmlParser(xmlHandler,
                                new Class[]{XunHuiCZ.class});

                        Log.v("login11", data);
                        parser.parse(data);

                    }
                    @Override
                    public void fail(String info) {
                    }

                });
            }
        });

    }
    private void initData(){
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        BingQuDM=preferences2.getString("bqdm","");
        userID=preferences2.getString("id","");

        if(brlb==null){
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
        }else{
            Intent intent =getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);
        }
        Date currentTime =new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd");
        sj=formatter.format(currentTime);


    }
    private void commitData(){
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        ch.setText(chuangHao + "床");

        Log.d("dfafdaf",sj);
        String kaishiSJ=sj+" 0:00:00";
        String jieshuSJ=sj+" 23:59:59";

        listXunHuiJL.clear();
        zhierCall = (new ZhierCall())
                .setId(userID)
                .setNumber("03043001")
                .setMessage(NetWork.GongYong)
                .setCanshu(zyid+"¤"+kaishiSJ+"¤"+jieshuSJ+"¤"+"BR")
                .setContext(this)
                .setPort(5000)
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler2,
                        new Class[]{XunHuiJL.class});

                Log.v("login11", data);
                parser.parse(data);

            }
            @Override
            public void fail(String info) {
            }

        });
    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    Log.d("login11", String.valueOf(listXunHui.size()));
                    String lx=xhlx.getText().toString().trim();
                    if(listXunHui.get(0).getXunHuiCZID().equals("")){
                        listXunHui.clear();
                    }
                    dialog =new XunHuiCZDialog(XhcxActivity.this,listXunHui,lx,myListener);
                    dialog.show();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listXunHui.add((XunHuiCZ) msg.obj);
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
    MyXmlHandler xmlHandler2=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if(listXunHuiJL.get(0).getBingRenZYID()==""){
                        list_xhjl.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else {
                        list_xhjl.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        XunHuiJLAdapter adapter = new XunHuiJLAdapter(XhcxActivity.this, listXunHuiJL);
                        list_xhjl.setAdapter(adapter);
                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listXunHuiJL.add((XunHuiJL) msg.obj);
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

}
