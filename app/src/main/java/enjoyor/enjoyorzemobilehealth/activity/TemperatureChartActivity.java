package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.TiWen;
import enjoyor.enjoyorzemobilehealth.utlis.ScreenUtils;
import enjoyor.enjoyorzemobilehealth.views.AutoTemperatureLineChart;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 体温单
 * Created by Administrator on 2017/5/10.
 */

public class TemperatureChartActivity extends AppCompatActivity implements DateTimeDialogOnlyYMD.MyOnDateSetListener, View.OnClickListener {
    private Context context;

    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos=0;
    private MyApplication myApplication;

    private ImageView mIvBack;
    private ImageView mIvTouXiang;
    private TextView mTvTemperDetail;
    private TextView mTvTemperPatientName;
    private TextView mTvChuangHao;
    private TextView mTvTimeYMD;
    private TextView mTvChartTemper;
    private TextView mTvChartMsgNormal;
    private TextView mTvChartMsgUp;
    private ImageView mIvChartMsgNormal;
    private ImageView mIvChartMsgUp;
    private LinearLayout llHsvContent;

    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String selectTime;
    private String threeDayAgoTime;

    private List<TiWen> temperatureList=new ArrayList<>();
    //详情页面的list集合
    private List<TiWen> temperatureDetailList=new ArrayList<>();

    private String[] xLineData;
    private String[] yLineData=new String[]{"35","36","37","38","39","40","41","42"};
    private String[] value;
    private static final int PER_PAGE_COUNT=4;
    private int pageCount;

    MyXmlHandler getTemperatureHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what){
                case END:
                    if(temperatureList.size()>0){
                        float max=0;
                        for(int i=0;i<temperatureList.size();i++){
                            try {
                                float cur=Float.parseFloat(temperatureList.get(i).getTiWen());
                                if(cur>max){
                                    max=cur;
                                }
                            }catch (Exception e){

                            }
                        }
//                        //Textview字符串拼接
//                        String text=String.format(getResources().getString(RcyMoreAdapter.string.max_temperature),max+"");
//                        SpannableString spanString = new SpannableString(text);
//                        int index=text.indexOf(max+"");
//                        int length=(max+"").length();
//                        //设置数值部分文本大小
//                        AbsoluteSizeSpan span = new AbsoluteSizeSpan(60);
//                        spanString.setSpan(span, index, index+length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                        mTvChartTemper.setText(spanString);

                        mTvChartTemper.setText(max+"");
                        if(max>37.5){
                            mTvChartMsgUp.setVisibility(View.VISIBLE);
                            mIvChartMsgUp.setVisibility(View.VISIBLE);
                            mTvChartMsgNormal.setVisibility(View.GONE);
                            mIvChartMsgNormal.setVisibility(View.GONE);
                        }else {
                            mTvChartMsgUp.setVisibility(View.GONE);
                            mIvChartMsgUp.setVisibility(View.GONE);
                            mTvChartMsgNormal.setVisibility(View.VISIBLE);
                            mIvChartMsgNormal.setVisibility(View.VISIBLE);
                        }

                        int res=temperatureList.size()%PER_PAGE_COUNT;
                        if(res!=0){
                            pageCount=temperatureList.size()/PER_PAGE_COUNT+1;
                        }else {
                            pageCount=temperatureList.size()/PER_PAGE_COUNT;
                        }
                        int perWidth= ScreenUtils.getScreenWidth(context)/(PER_PAGE_COUNT-1);
                        AutoTemperatureLineChart lineChart=new AutoTemperatureLineChart(context,temperatureList);
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(perWidth*(temperatureList.size()-1), LinearLayout.LayoutParams.MATCH_PARENT);
                        lineChart.setLayoutParams(params);
                        llHsvContent.removeAllViews();
                        llHsvContent.addView(lineChart);
                    }else {
                        int perWidth= ScreenUtils.getScreenWidth(context)/(PER_PAGE_COUNT-1);
                        AutoTemperatureLineChart lineChart=new AutoTemperatureLineChart(context,temperatureList);
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(context), LinearLayout.LayoutParams.MATCH_PARENT);
                        lineChart.setLayoutParams(params);
                        llHsvContent.removeAllViews();
                        llHsvContent.addView(lineChart);
                    }
                    break;
                case NODE:
                    switch (msg.arg1){
                        case 0:
                            TiWen tiWen= (TiWen) msg.obj;
                            TiWen tiWenDetail= (TiWen) msg.obj;
                            String rqTiWen=tiWen.getCaiJiRQ();
                            String sjTiWen=tiWen.getCaiJiSJ();
                            //有效值判断
                            if(!TextUtils.isEmpty(sjTiWen)){
                                //字符串截取
                                tiWen.setCaiJiRQ(rqTiWen.substring(5));
                                tiWen.setCaiJiSJ(sjTiWen.substring(0,5));
                                temperatureList.add(tiWen);

                                tiWenDetail.setCaiJiRQ(rqTiWen);
                                tiWenDetail.setCaiJiSJ(sjTiWen.substring(0,5));
                                temperatureDetailList.add(tiWenDetail);
                            }
                            Log.i("Data","temperatureList的大小---"+temperatureList.size());
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temperature_chart);
        context=this;
        myApplication= MyApplication.getInstance();

        Intent intent=getIntent();
        selectPos=intent.getIntExtra("position",0);

        initView();
        initData();
    }

    private void initData() {
        String xingBie=myApplication.getListBRLB().get(selectPos).getXINGBIE();
        if(TextUtils.equals(xingBie,"男")){
            mIvTouXiang.setImageResource(R.drawable.icon_men);
        }else {
            mIvTouXiang.setImageResource(R.drawable.icon_women);
        }
        mTvTemperPatientName.setText(myApplication.getListBRLB().get(selectPos).getXINGMING());
        mTvChuangHao.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO()+"床");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //mTvTimeYMD.setText(year + "-" + month + "-" + day);
        selectTime=mDateFormat.format(calendar.getTime())+"";
        mTvTimeYMD.setText(selectTime);
        //三天前时间
        calendar.set(Calendar.DAY_OF_MONTH,day-3);
        threeDayAgoTime=mDateFormat.format(calendar.getTime())+"";

        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(this, this, true, true, true);

//        String canShu="20111201"+ NetWork.SEPARATE+"0404"+ NetWork.SEPARATE+"2017/2/3"+ NetWork.SEPARATE
//               +"2017/2/4";
//        ZhierCall call = new ZhierCall()
//                .setId("1000")
//                .setNumber("0600701")
//                .setMessage(NetWork.SMTZ)
//                .setCanshu(canShu)
//                .setContext(this)
//                .setPort(5000)
//                .build();
//        call.start(new NetWork.SocketResult() {
//            @Override
//            public void success(String data) {
//                Log.i("Data","数据++++++"+data);
//                StringXmlParser parser=new StringXmlParser(getTemperatureHandler,new Class[]{TiWen.class});
//                parser.parse(data);
//            }
//
//            @Override
//            public void fail(String info) {
//                Log.i("Data","请求数据失败++++++"+info);
//            }
//        });
        //获取数据
        initValue();
    }
    private void initValue(){
//        String canShu="20111201"+ NetWork.SEPARATE+"0404"+ NetWork.SEPARATE+"2017/2/3"+ NetWork.SEPARATE
//                +"2017/2/4";
        String yhid=myApplication.getYhgh();
        String id=myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
        String bingQuDM=myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        Log.i("Data","id++++++bingQuDM"+id+"---"+bingQuDM);
        String canShu=id + NetWork.SEPARATE + bingQuDM + NetWork.SEPARATE + threeDayAgoTime + NetWork.SEPARATE
                + selectTime;

//        ZhierCall call = new ZhierCall()
//                .setId(yhid)
//                .setNumber("0600701")
//                .setMessage(NetWork.SMTZ)
//                .setCanshu(canShu)
//                .setContext(this)
//                .setPort(5000)
//                .build();

        ZhierCall call = new ZhierCall()
                .setId("1000")
                .setNumber("0600701")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canShu)
                .setContext(this)
                .setPort(5000)
                .build();
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.i("Data","数据++++++"+data);
                StringXmlParser parser=new StringXmlParser(getTemperatureHandler,new Class[]{TiWen.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                Log.i("Data","请求数据失败++++++"+info);
            }
        });
    }

    private void initView() {
        mIvBack= (ImageView) findViewById(R.id.iv_back);
        mIvTouXiang= (ImageView) findViewById(R.id.iv_touxiang);
        mTvChuangHao= (TextView) findViewById(R.id.tv_chuanghao);
        mTvTemperDetail= (TextView) findViewById(R.id.tv_temper_detail);
        mTvTimeYMD= (TextView) findViewById(R.id.tv_time_ymd);
        mTvTemperPatientName= (TextView) findViewById(R.id.tv_temperature_patient_name);
        mTvChartTemper= (TextView) findViewById(R.id.tv_chart_temperature);
        mTvChartMsgNormal= (TextView) findViewById(R.id.tv_chart_msg_normal);
        mTvChartMsgUp= (TextView) findViewById(R.id.tv_chart_msg_up);
        mIvChartMsgNormal= (ImageView) findViewById(R.id.iv_chart_msg_normal);
        mIvChartMsgUp= (ImageView) findViewById(R.id.iv_chart_msg_up);
        llHsvContent= (LinearLayout) findViewById(R.id.ll_hsv_content);

        mIvBack.setOnClickListener(this);
        mTvTemperDetail.setOnClickListener(this);
        mTvTemperPatientName.setOnClickListener(this);
        mTvTimeYMD.setOnClickListener(this);
    }

    @Override
    public void onDateSet(Calendar calendar) {
        selectTime=mDateFormat.format(calendar.getTime())+"";
        mTvTimeYMD.setText(selectTime);
        //三天前时间
        int day=calendar.get(Calendar.DAY_OF_MONTH)-3;
        calendar.set(Calendar.DAY_OF_MONTH,day);
        threeDayAgoTime=mDateFormat.format(calendar.getTime())+"";
        //重新获取值
        initValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_temper_detail:
                Intent temperatureDetailIntent=new Intent(context,TemperatureDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("temperatureList", (Serializable) temperatureDetailList);
                temperatureDetailIntent.putExtras(bundle);
                startActivity(temperatureDetailIntent);
                break;
            case R.id.tv_temperature_patient_name:
                Intent intent=new Intent(context,BrlbActivity.class);
                intent.putExtra("which","TWD");
                startActivity(intent);
                finish();
                //startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.tv_time_ymd:
                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE){
                int pos=data.getIntExtra("position",0);
                Log.i("data","------"+pos);
                selectPos=pos;
                String name= myApplication.getListBRLB().get(pos).getXINGMING();
                mTvTemperPatientName.setText(name);
                //重新获取控件值
                initValue();
            }
        }
    }
}
