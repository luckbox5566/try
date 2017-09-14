package enjoyor.enjoyorzemobilehealth.activity.infosearch;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.adapter.JcjgAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.JCJG;
import enjoyor.enjoyorzemobilehealth.entities.JYJG;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import enjoyor.enjoyorzemobilehealth.views.JcjgDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/7/7.
 */

public class JcjgcxActivity extends BaseActivity {
    private DateUtil instance;
    private SimpleDateFormat format;
    private TimePickerView pvTime;
    private ZhierCall zhierCall;
    private TextView xm;
    private ImageView tx;
    private TextView cxsj;
    private TextView ch;
    private ListView jcd;
    private ImageView back;
    private LinearLayout nodata;
    private LinearLayout layout;
    private String chaXunSJ;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String zyid;
    private JcjgDialog dialog;
    private int flag=0;
    private List<JCJG> listJCJG=new ArrayList<JCJG>();
    private JCJG jcjg=null;
    private  BRLB brlb=null;

    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_jcjgcx);
        defineData();
        clickData();
        initData();
    }
    private void defineData(){
        xm=(TextView) findViewById(R.id.mz);
        tx=(ImageView) findViewById(R.id.tx);
        ch=(TextView) findViewById(R.id.ch);
        cxsj=(TextView) findViewById(R.id.cxsj);
        jcd=(ListView) findViewById(R.id.jcd);
        back=(ImageView) findViewById(R.id.back);
        layout=(LinearLayout) findViewById(R.id.top);

        nodata=(LinearLayout) findViewById(R.id.nodata);
        brlb=MyApplication.getInstance().getOther_brlb();
    }
    private void clickData(){
        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","6");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(JcjgcxActivity.this,BrlbActivity.class);
                intent.putExtra("which","6");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(JcjgcxActivity.this,XxcxActivity.class));
                finish();
            }
        });
        cxsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance= DateUtil.getInstance();
                format=new SimpleDateFormat("yyyy/M/d");
                //时间选择器
                pvTime = new TimePickerView.Builder(JcjgcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        chaXunSJ=format.format(date);
                        flag=1;
                        initData();
                    }
                }).setDate(instance.getCalendar(cxsj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("选择查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        //.isDialog(true)
                        .build();
                pvTime.show();

            }
        });
    }
    private void initData(){

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        MyApplication instance = MyApplication.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        Date d= null;

        if(flag==1){

        }else if(brlb==null&&flag==0){
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            chaXunSJ =instance.getListBRLB().get(0).getRUYUANSJ().replace('-','/');
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
            /*try {
                d = formatter.parse(chaXunSJ);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chaXunSJ = format.format(d);*/
        }else if(brlb!=null&&flag==0){
            Intent intent=getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chaXunSJ=intent.getStringExtra("RUYUANSJ").replace('-','/');
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);
           /* try {
                d = formatter.parse(chaXunSJ);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            chaXunSJ = format.format(d);*/
        }
        Log.d("111111111212",chaXunSJ);
        listJCJG.clear();
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500201")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(zyid+"¤"+chaXunSJ)
                .setContext(this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JCJG.class});

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
                    xm.setText(bingRenXM);
                    ch.setText(chuangHao+"床");
                    cxsj.setText(chaXunSJ);
                    if(bingRenXB.equals("男")){
                        tx.setImageResource(R.drawable.icon_men);
                    }else{
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    if (listJCJG.get(0).getYiZhuID()==""){
                        nodata.setVisibility(View.VISIBLE);
                        jcd.setVisibility(View.GONE);
                    }else{
                        nodata.setVisibility(View.GONE);
                        jcd.setVisibility(View.VISIBLE);
                        JcjgAdapter adapter=new JcjgAdapter(JcjgcxActivity.this,listJCJG);
                        jcd.setAdapter(adapter);
                        jcd.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                JCJG jcjg = new JCJG();
                                jcjg.setJianChaXM(listJCJG.get(position).getJianChaXM());
                                jcjg.setJianChaSJ(listJCJG.get(position).getJianChaSJ());
                                jcjg.setZhenDuanJG(listJCJG.get(position).getZhenDuanJG());
                                dialog = new JcjgDialog(JcjgcxActivity.this,jcjg);
                                dialog.show();

                            }
                        });
                    }

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJCJG.add((JCJG) msg.obj);
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
