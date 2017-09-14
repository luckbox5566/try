package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.handlers.MyXmlHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.ShengMingTiZhengLuRuAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.CanShu;
import enjoyor.enjoyorzemobilehealth.entities.ContentBean;
import enjoyor.enjoyorzemobilehealth.entities.KongJian;
import enjoyor.enjoyorzemobilehealth.entities.MoKuaiFenLei;
import enjoyor.enjoyorzemobilehealth.entities.ShengMingTiZhengLuRuBean;
import enjoyor.enjoyorzemobilehealth.entities.ValueSMTZKongJian;
import enjoyor.enjoyorzemobilehealth.utlis.CreateXmlUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SPUtil;
import enjoyor.enjoyorzemobilehealth.views.ConfirmAndCancelDialog;
import enjoyor.enjoyorzemobilehealth.views.DateTimeDialogOnlyYMD;
import enjoyor.enjoyorzemobilehealth.views.MyTimePicker;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/2/4.
 */

public class ShengMingTiZhengLuRuActivity extends AppCompatActivity implements View.OnClickListener, DateTimeDialogOnlyYMD.MyOnDateSetListener, MyTimePicker.MyOnTimeSetListener {
//    @BindView(RcyMoreAdapter.id.tv_time_ymd)
//    TextView mTvYMD;
//    @BindView(RcyMoreAdapter.id.tv_time_hm)
//    TextView mTvHM;
    @BindView(R.id.lv_smtz_content)
    ListView mContentLV;
    @BindView(R.id.btn_save)
    Button btnSave;

    //public KeyboardView keyboardView;
    private Context context;
    private ImageView mIvBack;
    private ImageView mIvTouXiang;
    private TextView mTvPatientName;
    private TextView mTvChuangHao;
    private TextView mTvYMD;
    private TextView mTvHM;

    private String yhid;
    private String bqdm;
    private String brzyid;
    private String brname;

//    private String name;
//    private String bedNum;

    private static final int REQUEST_CODE = 1; // 请求码
    private int selectPos=0;
    private MyApplication myApplication;

    private static final String SMTZ_KONGJIAN_DATA = "smtz_kongjian_data";
    private DateTimeDialogOnlyYMD mDateTimeDialogOnlyYMD;
    private MyTimePicker myTimePicker;
    // 日期格式化工具
    //private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    private String[] selectTime=new String[]{"09:00", "12:00", "15:00", "18:00", "21:00"};
    private List<ShengMingTiZhengLuRuBean> mLVDatas;
    private List<ContentBean> contentChangLuXiang;
    private List<ContentBean> contentQiTaXiang;
    private List<ContentBean> totalXiang;
    private List<ValueSMTZKongJian> getValueList;

    private ShengMingTiZhengLuRuAdapter mAdatper;


    List<MoKuaiFenLei> moKuaiFenLeiList = new ArrayList<>();
    List<KongJian> kongJianList = new ArrayList<>();
    List<CanShu> canShuList = new ArrayList<>();

    private List<ContentBean> tempContentChangLuXiang = new ArrayList<>();
    private List<ContentBean> tempContentQiTaXiang = new ArrayList<>();

    MyXmlHandler getKongJianDataHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //解析好后的操作
                    Log.i("Data",  "moKuaiFenLeiList大小"+moKuaiFenLeiList.size());
                    Log.i("Data",  "kongJianList大小"+kongJianList.size());
                    Log.i("Data","canShuList大小"+canShuList.size());
                    String str="canShu内容：";
                    for(int i=0;i<canShuList.size();i++){
                        CanShu canShu=canShuList.get(i);
                        str=str+canShu.getJiChuXiangMuID()+"---"+canShu.getCanShuZhi();
                    }
                    Log.i("Data",str);
                    for (int i = 0; i < kongJianList.size(); i++) {
                        KongJian kongJian = kongJianList.get(i);
                        Log.i("Data",  kongJian.getKongJianMC()+"------"+kongJian.getKongJianLX());
                        //totalXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(),kongJian.getMoKuaiFLID()));
                        //Log.i("Data", totalXiang.size() + "");
                        if (kongJian.getMoKuaiFLID().equals("2")) {
                            //常录项
                            contentChangLuXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getKongJianLX(),kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(), kongJian.getMoKuaiFLID()));
                        } else if (kongJian.getMoKuaiFLID().equals("3")) {
                            //其它项
                            contentQiTaXiang.add(new ContentBean(kongJian.getKongJianMC(), kongJian.getKongJianLX(),kongJian.getZhiDanWei(), kongJian.getJiChuXiangMuID(), kongJian.getMoKuaiFLID()));
                        }
                    }
                    //Log.i("Data", contentChangLuXiang.size() + "");
                    //Log.i("Data", contentQiTaXiang.size() + "");
                    for (int i = 0; i < moKuaiFenLeiList.size(); i++) {
                        if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("2")) {
                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentChangLuXiang));
                        } else if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("3")) {
                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentQiTaXiang));
                        }
                    }
                    //Log.i("Data", mLVDatas.size() + "");
                    //给ListView设置适配器
                    mAdatper=new ShengMingTiZhengLuRuAdapter(ShengMingTiZhengLuRuActivity.this, mLVDatas,canShuList);
                    mContentLV.setAdapter(mAdatper);

                    //初始化控件的初始值
                    initValue();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            moKuaiFenLeiList.add((MoKuaiFenLei) msg.obj);
                            //Log.i("Data",moKuaiFenLeiList.size()+"");
                            break;
                        case 1:
                            kongJianList.add((KongJian) msg.obj);
                            //Log.i("Data",kongJianList.size()+"");
                            break;
                        case 2:
                            canShuList.add((CanShu) msg.obj);
                            //Log.i("Data",canShuList.size()+"");
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

    MyXmlHandler getKongJianValueHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    Log.i("Data","getValueList大小"+getValueList.size());
                    //contentChangLuXiang.clear();
                    // contentQiTaXiang.clear();
                    //mLVDatas.clear();
                    for (int i = 0; i < getValueList.size(); i++) {
                        ValueSMTZKongJian valueBean = getValueList.get(i);
                        Log.i("Data","名称"+valueBean.getKongJianMC());
                        String id = "";
                        String smtzId="";
                        for (int j = 0; j < kongJianList.size(); j++) {
                            KongJian kongJian = kongJianList.get(j);
                            Log.i("Data","名称"+kongJian.getKongJianMC());
                            if (valueBean.getKongJianMC().equals(kongJian.getKongJianMC())) {
                                id = kongJian.getMoKuaiFLID();
                                smtzId=valueBean.getShengMingTZID();
                                Log.i("Data", "id" + id);
                                break;
                            }
                        }
                        //ContentBean addBean=new ContentBean(valueBean.getKongJianMC(), valueBean.getShuZhiDW(), valueBean.getJiChuXiangMuID(),id,valueBean.getShuZhi1());
                        if (TextUtils.equals(id,"2")) {
                            //contentChangLuXiang.add(addBean);
                            for (int k = 0; k < contentChangLuXiang.size(); k++) {
                                ContentBean bean = contentChangLuXiang.get(k);
                                if (valueBean.getKongJianMC().equals(bean.getKongJianMC())) {
                                    bean.setContentValue(valueBean.getShuZhi1());
                                    bean.setShengMingTZID(smtzId);
                                    Log.i("Data", "数值" + bean.getContentValue());
                                }
                            }
                        } else if (TextUtils.equals(id,"3")) {
                            //contentQiTaXiang.add(addBean);
                            for (int k = 0; k < contentQiTaXiang.size(); k++) {
                                ContentBean bean = contentQiTaXiang.get(k);
                                if (valueBean.getKongJianMC().equals(bean.getKongJianMC())) {
                                    bean.setContentValue(valueBean.getShuZhi1());
                                    bean.setShengMingTZID(smtzId);
                                    Log.i("Data", "数值" + bean.getContentValue());
                                }
                            }
                        }
                    }

//                    for(int i=0;i<kongJianList.size();i++){
//                        KongJian kongJian = kongJianList.get(i);
//                        String id="";
//                        for(int j=0;j<getValueList.size();j++){
//                            ValueSMTZKongJian valueBean = getValueList.get(i);
//                            if(kongJian.getKongJianMC()==valueBean.getKongJianMC()){
//                                id=kongJian.getMoKuaiFLID();
//                                if(id=="2"){
//                                    for(int k=0;k<contentChangLuXiang.size();k++){
//                                        ContentBean bean=contentChangLuXiang.get(k);
//                                        if(bean.getKongJianMC()==valueBean.getKongJianMC()){
//                                            bean.setContentValue(valueBean.getShuZhi1());
//                                        }
//                                    }
//                                }else if(id=="3"){
//                                    for(int k=0;k<contentQiTaXiang.size();k++){
//                                        ContentBean bean=contentQiTaXiang.get(k);
//                                        if(bean.getKongJianMC()==valueBean.getKongJianMC()){
//                                            bean.setContentValue(valueBean.getShuZhi1());
//                                        }
//                                    }
//                                }
//                                break;
//                            }
//                        }
//
//                    }


//                    for (int i = 0; i < moKuaiFenLeiList.size(); i++) {
//                        if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("2")) {
//                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentChangLuXiang));
//                        } else if (moKuaiFenLeiList.get(i).getMoKuaiFLID().equals("3")) {
//                            mLVDatas.add(new ShengMingTiZhengLuRuBean(moKuaiFenLeiList.get(i).getMoKuaiFLMC(), contentQiTaXiang));
//                        }
//                    }
                    //给ListView设置适配器
                    //mContentLV.setAdapter(new ShengMingTiZhengLuRuAdapter(ShengMingTiZhengLuRuActivityBak.this, mLVDatas,canShuList));
                    //刷新适配器
                    mAdatper.notifyDataSetChanged();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            ValueSMTZKongJian bean = (ValueSMTZKongJian) msg.obj;
                            if(!TextUtils.isEmpty(bean.getShengMingTZID())){
                                getValueList.add(bean);
                            }
//                            Log.i("Data", "getValueList大小" + getValueList.size());
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

    MyXmlHandler getIfSaveSuccessHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shengmingtizhengluru);
        ButterKnife.bind(this);
        myApplication= MyApplication.getInstance();
        context=this;
        //keyboardView= (KeyboardView) findViewById(RcyMoreAdapter.id.kv_edit);

        yhid=myApplication.getYhgh();
        bqdm=myApplication.getBqdm();

//        Intent intent=getIntent();
//        selectPos=intent.getIntExtra("position",0);

        mLVDatas = new ArrayList<>();
        contentChangLuXiang = new ArrayList<>();
        contentQiTaXiang = new ArrayList<>();
        totalXiang = new ArrayList<>();
        getValueList = new ArrayList<>();

        initView();
        initData();
        initKongJianData();
        //setRvData();
        setListener();
    }

    private void initView() {
        mIvBack= (ImageView) findViewById(R.id.iv_back);
        //View topBgView= LayoutInflater.from(context).inflate(RcyMoreAdapter.layout.top_bg,null);
        View topBgView= LayoutInflater.from(context).inflate(R.layout.top_bg,mContentLV,false);
        //topBgView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mIvTouXiang= (ImageView) topBgView.findViewById(R.id.iv_touxiang);
        mTvPatientName= (TextView) topBgView.findViewById(R.id.tv_patient_name);
        mTvChuangHao= (TextView) topBgView.findViewById(R.id.tv_chuanghao);
        mTvYMD= (TextView) topBgView.findViewById(R.id.tv_time_ymd);
        mTvHM= (TextView) topBgView.findViewById(R.id.tv_time_hm);
        mContentLV.addHeaderView(topBgView);
    }

    private void setListener() {
        mIvBack.setOnClickListener(this);
        mIvTouXiang.setOnClickListener(this);
        mTvPatientName.setOnClickListener(this);
        mTvYMD.setOnClickListener(this);
        mTvHM.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

//    private void setRvData() {
//        rvChangLuXiang.setLayoutManager(new GridLayoutManager(this, 3));
//        rvChangLuXiang.setAdapter(new CommonAdapter<ChangLuXiangBean>(this, RcyMoreAdapter.layout.item_rv_changluxiang, mRVList) {
//
//            @Override
//            protected void convert(ViewHolder holder, ChangLuXiangBean changLuXiangBean, int position) {
//                holder.setText(RcyMoreAdapter.id.tv_title, mRVList.get(position).getTitle());
//                holder.setText(RcyMoreAdapter.id.tv_danwei, mRVList.get(position).getDanWei());
//            }
//        });
//    }

    private void initData() {
        String xingBie=myApplication.getListBRLB().get(selectPos).getXINGBIE();
        if(TextUtils.equals(xingBie,"男")){
            mIvTouXiang.setImageResource(R.drawable.icon_men);
        }else {
            mIvTouXiang.setImageResource(R.drawable.icon_women);
        }
        brname=myApplication.getListBRLB().get(selectPos).getXINGMING();
        mTvPatientName.setText(brname);
        mTvChuangHao.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO()+"床");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int minute = calendar.get(Calendar.MINUTE);
        mTvYMD.setText(year + "-" + month + "-" + day);
        //mTvHM.setText(hour + ":" + minute);

        long temp=0l;
        String finalTime="";
        //找出最接近当前的时间点
        for(int i=0;i<selectTime.length;i++){
            String time=mTvYMD.getText()+" "+selectTime[i];
            try {
                Date now=new Date();
                Date select=format.parse(time);
                if(i==0){
                    temp=Math.abs(now.getTime()-select.getTime());
                    finalTime=selectTime[i];
                }else {
                    if(temp>Math.abs(now.getTime()-select.getTime())){
                        temp=Math.abs(now.getTime()-select.getTime());
                        finalTime=selectTime[i];
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.i("ParseException","ParseException日期格式转换异常"+e.toString());
            }
        }
        mTvHM.setText(finalTime);

        mDateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(this, this, true, true, true);
        myTimePicker = new MyTimePicker(this, this);

    }

    /**
     * 初始化控件属性
     */
    private void initKongJianData() {
        //清空数据避免重复
        //moKuaiFenLeiList kongJianList canShuList contentChangLuXiang contentQiTaXiang mLVDatas
        moKuaiFenLeiList.clear();
        kongJianList.clear();
        canShuList.clear();
        contentChangLuXiang.clear();
        contentQiTaXiang.clear();
        mLVDatas.clear();

        String getSPData = SPUtil.getStringVal(ShengMingTiZhengLuRuActivity.this, SMTZ_KONGJIAN_DATA, "");
        //初始化控件
        if (TextUtils.isEmpty(getSPData)) {
            Log.i("Data", "从服务端获取控件数据");
            initKongJian();
        } else {
            Log.i("Data", "从本地SP获取控件数据");
            Log.i("Data", "从本地SP获取控件数据"+getSPData);
            //解析并初始化
            parseData(getKongJianDataHandler, new Class[]{MoKuaiFenLei.class, KongJian.class,
                    CanShu.class}, getSPData);
        }

        //注意在这里初始化控件的录入值时可能导致程序崩掉，因为初始化控件数据有可能还没完成,故放到解析完成后
        //initValue();
    }

    /**
     * 解析xml并发消息
     *
     * @param handler 消息handler
     * @param cla     bean数组
     * @param data    从服务端获得的数据
     */
    private void parseData(MyXmlHandler handler, Class[] cla, String data) {
        StringXmlParser parser = new StringXmlParser(handler, cla);
        parser.parse(data);
    }

    public String GetNowDate(){
        String temp_str="";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        temp_str=sdf.format(dt);
        return temp_str;
    }

    /**
     * 初始化控件的值
     */
    private void initValue() {
        //清空数据源
        getValueList.clear();
        //控件的值置空操作
        for(int i=0;i<contentChangLuXiang.size();i++){
            ContentBean bean=contentChangLuXiang.get(i);
            Log.i("常录项bean名称",bean.getKongJianMC());
            bean.setContentValue("");
        }
        for(int i=0;i<contentQiTaXiang.size();i++){
            ContentBean bean=contentQiTaXiang.get(i);
            Log.i("其它项bean名称",bean.getKongJianMC());
            bean.setContentValue("");
        }

        Log.i("Data",GetNowDate());
        brzyid=myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
        //String bingQuDM=myApplication.getListBRLB().get(selectPos).getBINGQUDM();
        mTvPatientName.setText(myApplication.getListBRLB().get(selectPos).getXINGMING());
        //20111201
//            String canShu = "20111201" + NetWork.SEPARATE
//                    + mTvYMD.getText().toString() + NetWork.SEPARATE
//                    + mTvHM.getText().toString() + NetWork.SEPARATE
//                    + "0404";
        String canShu = brzyid + NetWork.SEPARATE
                + mTvYMD.getText().toString() + NetWork.SEPARATE
                + mTvHM.getText().toString() + NetWork.SEPARATE
                + bqdm;
//        ZhierCall call = new ZhierCall()
//                .setId(yhid)
//                .setNumber("0600301")
//                .setMessage(NetWork.SMTZ)
//                .setCanshu(canShu)
//                .setContext(this)
//                .setPort(5000)
//                .build();
            ZhierCall call = new ZhierCall()
                    .setId(yhid)
                    .setNumber("0600301")
                    .setMessage(NetWork.SMTZ)
                    .setCanshu(canShu)
                    .setContext(this)
                    .setPort(5000)
                    .build();
            //Log.i("Data", "执行了");
            call.start(new NetWork.SocketResult() {
                @Override
                public void success(String data) {
                    Log.i("Data", "初始化值+++++++" + data);
                    Log.i("Data",GetNowDate());
                    parseData(getKongJianValueHandler, new Class[]{ValueSMTZKongJian.class}, data);
//                StringXmlParser parser = new StringXmlParser(getKongJianValueHandler,
//                        new Class[]{ValueSMTZKongJian.class});
//                parser.parse(data);
                }

                @Override
                public void fail(String info) {
//                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, info, Toast.LENGTH_SHORT).show();
                    Log.i("aaaaaaaaa", "aaaaaaaaa+++++++" + info);
                }
            });
    }

    /**
     * 初始化控件
     */
    private void initKongJian() {
        Log.i("data","用户ID"+yhid+"---"+bqdm);
//        ZhierCall call = new ZhierCall()
//                .setId(id)
//                .setNumber("0600202")
//                .setMessage(NetWork.SMTZ)
//                .setCanshu(bqdm)
//                .setContext(this)
//                .setPort(5000)
//                .build();
        ZhierCall call = new ZhierCall()
                .setId(yhid)
                .setNumber("0600202")
                .setMessage(NetWork.SMTZ)
                .setCanshu(bqdm)
                .setContext(this)
                .setPort(5000)
                .build();
        //Log.i("Data", "执行了");
        call.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                //Log.i("Data", "初始化控件+++++++" + data);
                //存储控件信息到SP
                SPUtil.putStringVal(ShengMingTiZhengLuRuActivity.this, SMTZ_KONGJIAN_DATA, data);

                //解析并初始化
                parseData(getKongJianDataHandler, new Class[]{MoKuaiFenLei.class, KongJian.class,
                        CanShu.class}, data);

//                StringXmlParser parser = new StringXmlParser(getKongJianDataHandler,
//                        new Class[]{MoKuaiFenLei.class, KongJian.class,
//                                CanShu.class});
//                parser.parse(data);

            }

            @Override
            public void fail(String info) {
//                Toast.makeText(ShengMingTiZhengLuRuActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(ShengMingTiZhengLuRuActivity.this,HomePageActivity.class));
                finish();
                break;
            case R.id.iv_touxiang:
            case R.id.tv_patient_name:
                Intent intent=new Intent(ShengMingTiZhengLuRuActivity.this,BrlbActivity.class);
                intent.putExtra("which","SMTZ");
//                startActivity(intent);
//                finish();
                startActivityForResult(intent,REQUEST_CODE);

                break;
            case R.id.tv_time_ymd:
                mDateTimeDialogOnlyYMD.hideOrShow();
                break;
            case R.id.tv_time_hm:
                myTimePicker.hideOrShow();
                break;
            case R.id.btn_save:
                //弹出确定取消对话框
                final ConfirmAndCancelDialog dialog=new ConfirmAndCancelDialog(context);
                dialog.setOnConfirmOrCancelClickListener(new ConfirmAndCancelDialog.OnConfirmOrCancelClickListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        //保存数据
                        saveData();
                    }

                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
//                saveData();
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        totalXiang.clear();
        totalXiang.addAll(tempContentChangLuXiang);
        totalXiang.addAll(tempContentQiTaXiang);
        Log.i("data", "totalXiang的大小" + totalXiang.size());
        String tag = "";
        for (int i = 0; i < totalXiang.size(); i++) {
            tag = tag + totalXiang.get(i).getPanDuanBZ() + "---";
        }
        Log.i("Data", "totalXiang判断标志" + tag);
//        for(int i=0;i<totalXiang.size();i++){
//            if(totalXiang.get(i).getPanDuanBZ().equals("2")){
//                totalXiang.remove(totalXiang.get(i));
//            }
//        }
        if (totalXiang != null && totalXiang.size() > 0) {
            Iterator iterator = totalXiang.iterator();
            while (iterator.hasNext()) {
                ContentBean bean = (ContentBean) iterator.next();
                if (bean.getPanDuanBZ().equals("2")) {
                    //移除对象
                    iterator.remove();
                }
            }
        }
        Log.i("data", "totalXiang的大小" + totalXiang.size());
        String lastTag = "";
        for (int i = 0; i < totalXiang.size(); i++) {
            lastTag = lastTag + totalXiang.get(i).getPanDuanBZ() + "---";
        }
        Log.i("Data", "totalXiang判断标志" + lastTag);

        //判断是否有添加或更改
        if(totalXiang.size()>0){
            String result = CreateXmlUtil.createSMTZXml(totalXiang, "tab_CaoZuo", 0);
            Log.i("SaveData", result);
            String canShu = yhid + NetWork.SEPARATE
                    + mTvYMD.getText().toString() + NetWork.SEPARATE
                    + mTvHM.getText().toString() + NetWork.SEPARATE
                    + bqdm + NetWork.SEPARATE
                    + brzyid + NetWork.SEPARATE
                    + brname + NetWork.SEPARATE
                    + result;
            ZhierCall call = new ZhierCall()
                    .setId(yhid)
                    .setNumber("0600306")
                    .setMessage(NetWork.SMTZ)
                    .setCanshu(canShu)
                    .setContext(this)
                    .setPort(5000)
                    .build();
            //Log.i("Data", "执行了");
            call.start(new NetWork.SocketResult() {
                @Override
                public void success(String data) {
                    //Toast.makeText(ShengMingTiZhengLuRuActivityBak.this, data, Toast.LENGTH_SHORT).show();
                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
//                    //页面数据置空操作
//                    for(int i=0;i<contentChangLuXiang.size();i++){
//                        ContentBean bean=contentChangLuXiang.get(i);
//                        Log.i("常录项bean名称",bean.getKongJianMC());
//                        bean.setContentValue("");
//                    }
//                    for(int i=0;i<contentQiTaXiang.size();i++){
//                        ContentBean bean=contentQiTaXiang.get(i);
//                        Log.i("其它项bean名称",bean.getKongJianMC());
//                        bean.setContentValue("");
//                    }
//                    //刷新数据
//                    mAdatper.notifyDataSetChanged();

                    //刷新页面
                    initKongJianData();
                    //清空适配器传递过来的变化的数据
                    tempContentChangLuXiang.clear();
                    tempContentQiTaXiang.clear();
                }

                @Override
                public void fail(String info) {
                    Toast.makeText(ShengMingTiZhengLuRuActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(ShengMingTiZhengLuRuActivity.this, "数据未更改!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(Calendar calendar) {
        Date date=calendar.getTime();
        mTvYMD.setText(mDateFormat.format(date) + "");
        //重新获取控件值
        initValue();
    }


    @Override
    public void onTimeSet(String time) {
        mTvHM.setText(time);
        //重新获取控件值
        initValue();
    }

    public void getChangeData(List<ContentBean> changeData) {
        if (changeData.get(0).getMoKuaiFenLeiID().equals("2")) {
            //this.contentChangLuXiang = changeData;
            tempContentChangLuXiang = changeData;
            String tag = "";
            for (int i = 0; i < tempContentChangLuXiang.size(); i++) {
                tag = tag + tempContentChangLuXiang.get(i).getPanDuanBZ() + "---";
            }
            Log.i("Data", "常录项判断标志" + tag);
        } else if (changeData.get(0).getMoKuaiFenLeiID().equals("3")) {
            //this.contentQiTaXiang = changeData;
            tempContentQiTaXiang = changeData;
            String tag = "";
            for (int i = 0; i < tempContentQiTaXiang.size(); i++) {
                tag = tag + tempContentQiTaXiang.get(i).getPanDuanBZ() + "---";
            }
            Log.i("Data", "其它项判断标志" + tag);
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
                brzyid=myApplication.getListBRLB().get(selectPos).getBINGRENZYID();
                brname= myApplication.getListBRLB().get(pos).getXINGMING();
                mTvPatientName.setText(brname);
                mTvChuangHao.setText(myApplication.getListBRLB().get(selectPos).getCHUANGWEIHAO()+"床");
                //重新获取控件值
                initValue();
            }
        }
    }


}
