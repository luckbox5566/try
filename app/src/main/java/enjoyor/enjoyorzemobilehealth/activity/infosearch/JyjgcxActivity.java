package enjoyor.enjoyorzemobilehealth.activity.infosearch;

/**
 * Created by Administrator on 2017/7/14.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import enjoyor.enjoyorzemobilehealth.adapter.JyjgAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.JYJG;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class JyjgcxActivity extends BaseActivity {
    private DateUtil instance;
    private ZhierCall zhierCall;
    private LinearLayout layout;
    private ImageView back;
    private LinearLayout sjxzlayout;
    private SimpleDateFormat format;
    private TimePickerView pvTime;
    private ListView listBgd;
    private String chuanghao;
    private String xingming;
    private String xingbie;
    private String shijian;
    private String zyid;
    private List<JYJG> listJYJG = new ArrayList<>();
    private List<JYJG> listJYJG1 = new ArrayList<>();
    private ProgressDialog progressDialog;
    BRLB brlb = null;
    private TextView xm;
    private TextView ch;
    private TextView sj;
    private LinearLayout nodata;
    private ImageView tx;
    private int i;
    private int flag = 0;
    private JyjgAdapter adapter;
    private int position;
    private String usid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyjgcx);
        defineData();
        clickData();
        initData();
    }

    private void defineData() {
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        sj = (TextView) findViewById(R.id.sj);
        ch = (TextView) findViewById(R.id.ch);
        nodata = (LinearLayout) findViewById((R.id.nodata));
        listBgd = (ListView) findViewById(R.id.bgd);
        layout = (LinearLayout) findViewById(R.id.top);
        sjxzlayout = (LinearLayout) findViewById((R.id.sjxz));
        back = (ImageView) findViewById(R.id.back);
        brlb = MyApplication.getInstance().getOther_brlb();
    }

    private void clickData() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "5");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(JyjgcxActivity.this, BrlbActivity.class);
                intent.putExtra("which", "5");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(JyjgcxActivity.this,XxcxActivity.class));
                finish();
            }
        });

        sjxzlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance = DateUtil.getInstance();
                format = new SimpleDateFormat("yyyy/M/d");
                //时间选择器
                pvTime = new TimePickerView.Builder(JyjgcxActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        shijian = format.format(date);
                        flag = 1;
                        initData();
                    }
                }).setDate(instance.getCalendar(sj.getText().toString())).setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
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

    private void initData() {

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        Date d= null;
        if(flag==1){

        }
        else if (brlb != null &&flag==0) {
            Intent intent = getIntent();
            zyid = intent.getStringExtra("BINGRENZYID");
            shijian = intent.getStringExtra("RUYUANSJ").replace('-','/');
            chuanghao = intent.getStringExtra("CHUANGHAO");
            xingming = intent.getStringExtra("XINGMING");
            xingbie = intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);
           /* try {
                d = formatter.parse(shijian);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shijian = format.format(d);*/
        } else if(brlb == null &&flag==0) {
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            shijian = instance.getListBRLB().get(0).getRUYUANSJ().replace('-','/');
            chuanghao = instance.getListBRLB().get(0).getCHUANGWEIHAO();
            xingming = instance.getListBRLB().get(0).getXINGMING();
            xingbie = instance.getListBRLB().get(0).getXINGBIE();
            /*try {
                d = formatter.parse(shijian);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            shijian = format.format(d);*/
        }
        Log.d("test121", zyid + "___" + shijian);
        usid = preferences2.getString("id", "");
        listJYJG.clear();
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0500301")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(zyid + "¤" + shijian)
                .setContext(this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{JYJG.class});

                Log.v("login11", data);
                parser.parse(data);

            }

            @Override
            public void fail(String info) {
            }

        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (xingbie.equals("男")) {
                        tx.setImageResource(R.drawable.icon_men);
                    } else {
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    xm.setText(xingming);
                    ch.setText(chuanghao + "床");
                    String shijian1 = shijian.replace("/", "-");
                    sj.setText(shijian);
                    Log.d("asdffSSSSa", String.valueOf(listJYJG.size()));

                    if(listJYJG.get(0).getYIZHUID().equals("")){
                        listBgd.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }else{
                        listBgd.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        adapter = new JyjgAdapter(JyjgcxActivity.this, listJYJG);
                        Log.d("login32", "准备执行第一个适配器");
                        listBgd.setAdapter(adapter);

                        listBgd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                initItem(i);


                            }
                        });

                    }
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJYJG.add((JYJG) msg.obj);
                            Log.d("test1000000", String.valueOf(listJYJG.size()));
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
    private void initItem(int i){
        String canshu = listJYJG.get(i).getYIZHUID();
        position=i;
        listJYJG1.clear();
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0500306")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(canshu)
                .setContext(JyjgcxActivity.this)
                .setPort(5000)
                .build();
        Log.d("ssasddasdsadsad", "1.5");
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("ssasddasdsadsad", "2");
                StringXmlParser parser = new StringXmlParser(xmlHandler2,
                        new Class[]{JYJG.class});
                Log.d("10000001", data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
            }

        });
    }
    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    if (JyjgAdapter.mParentItem == position && JyjgAdapter.mbShowChild) {
                        JyjgAdapter.mbShowChild = false;
                    } else {
                        JyjgAdapter.mbShowChild = true;
                    }
                    JyjgAdapter.mParentItem = position;
                    JyjgAdapter.listItem=listJYJG1;
                    adapter.notifyDataSetChanged();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listJYJG1.add((JYJG) msg.obj);
                            Log.d("test5555", String.valueOf(listJYJG1.size()));
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
