package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
//import enjoyor.enjoyorzemobilehealth.adapter.RcyAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.RcyMoreAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.AllWXYS;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * ADL评估     序号 12345677890
 */
public class ZiLiAbilityActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.iv_colse1)
    ImageView ivColse1;
    @BindView(R.id.tv_home_title)
    TextView tvHomeTitle;
    @BindView(R.id.tv_commit1)
    TextView tvCommit1;
    @BindView(R.id.tv_pinggu_fengxian)
    TextView tvPingguFengxian;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_adl)
    RecyclerView rcAdl;
    @BindView(R.id.activity_danger_add1)
    LinearLayout activityDangerAdd1;
    private ImageView ivColse;
    private String result = Constant.ADL;
    //    private RcyAdapter adapter_adl;
    private String[] pfxxTags = new String[]{"", "", "", "", "", "", "", "", "", ""};
    private int[] sorces = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//
//    private int[] sorces1 = new int[]{0, 0, 0};//5分
//    private int[] sorces2 = new int[]{0, 0};//1分
//    private int[] sorces3 = new int[]{0, 0};//2分
//    private int[] sorces4 = new int[]{0, 0, 0};//3分
//    private int[] sorces5 = new int[]{0, 0, 0,};//3分
//    private int[] sorces6 = new int[]{0, 0, 0};//3分
//    private int[] sorces7 = new int[]{0, 0, 0};//3分
//    private int[] sorces8 = new int[]{0, 0, 0, 0};//3分
//    private int[] sorces9 = new int[]{0, 0, 0, 0};//3分
//    private int[] sorces10 = new int[]{0, 0, 0};//3分

    private int all = 0;
    private String lastPfMsg = "";//最后评分信息
    private String f;
    private DateUtil dateUtil;
    private String date;
    private MyApplication instance;
    private Intent intent;
    private String tag;
    private String inPfxx;
    private String itemId;
    private String beanPfxx;
    private String uesrid;
    private String brzyid;
    private String rydate;
    private String name;
    private String brid;
    private String sex;
    private String age;
    private String bqmc;
    private String pingguren;
    private String bqId;
    private CenterDialog centerDialog;
    private ZhierCall zhierCall;
    private RcyMoreAdapter adapter_rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_add1);
        ButterKnife.bind(this);
        init();
        initCanShu();
        initData();
        initListener();
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);

        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        instance = MyApplication.getInstance();
    }

    private void initCanShu() {
        intent = getIntent();
        String title = intent.getStringExtra("title");
        tvHomeTitle.setText(title);
        tag = intent.getStringExtra(Constant.TAG);
        if (tag.equals("add")) {
//            beanCsxx = new String[endNames.length];
//            for (int i = 0; i < endNames.length; i++) {
//                beanCsxx[i] = "0";
//                Log.e("beabbb", beanCsxx[i]);
//            }
//            Log.e("adddd", beanCsxx.toString() + beanCsxx.length);

        } else if (tag.equals("updata")) {
            AllWXYS bean = (AllWXYS) intent.getSerializableExtra("bean");
            String fenxian = bean.getJB();

            String fen = bean.getZPF();
            inPfxx = bean.getFZxx();

            itemId = bean.getID();

            lastPfMsg = inPfxx;
            Log.e("", inPfxx + "--" + itemId);

            initPfxx();   //评分信息处理

            beanPfxx = inPfxx;

            tvPingguFengxian.setText(fenxian);
            tvScore.setText(fen);

            getCurDate(fenxian.toString().trim());

        }


        date = DateUtil.getInstance().getDate();
        uesrid = (String) SaveUtils.get(this, Constant.USERID, "");//用户登录ID
        brzyid = intent.getStringExtra(Constant.BRZYID);//病人住院ID
        rydate = intent.getStringExtra(Constant.RYSJ);//病人入院时间
        name = intent.getStringExtra(Constant.BRNAME);//病人姓名
        brid = intent.getStringExtra(Constant.BRID);//病人id
        sex = intent.getStringExtra(Constant.SEX);//病人性别
        age = intent.getStringExtra(Constant.AGE);//病人年龄

        bqmc = (String) SaveUtils.get(this, Constant.BQMC, "");//病区名称
        pingguren = instance.getYhxm();//评估人姓名
        bqId = instance.getBqdm();//病区ID
    }

    private void getCurDate(String fxjb) {
        String sfm = date.substring(date.length() - 8, date.length());
        if (fxjb.equals("无需依赖")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("轻度依赖")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("中度依赖")) {
            String day = dateUtil.after3Day();
            date = day + " " + sfm;
        } else if (fxjb.equals("重度依赖")) {
            String day = dateUtil.afterDay();
            date = day + " " + sfm;
        }
    }

    /**
     * 初始化评分信息
     */
    private void initPfxx() {
        String pf1 = inPfxx.substring(0, 5);//3
        String pf2 = inPfxx.substring(6, 9);//2
        String pf3 = inPfxx.substring(10, 13);//2
        String pf4 = inPfxx.substring(14, 19);//3

        String pf5 = inPfxx.substring(20, 25);//3
        String pf6 = inPfxx.substring(26, 31);//3
        String pf7 = inPfxx.substring(32, 37);//3

        String pf8 = inPfxx.substring(38, 45);//4
        String pf9 = inPfxx.substring(46, 53);//4
        String pf10 = inPfxx.substring(54, inPfxx.length());//3

        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("1")) {
                if (i == 0) {
                    sorces[0] = 10;
                } else if (i == 1) {
                    sorces[0] = 5;
                } else {
                    sorces[0] = 0;
                }
                pfxxTag1[i] = "#1";
            } else {
                pfxxTag1[i] = "#0";
            }
        }
        String s1 = "";
        for (int j = 0; j < pfxxTag1.length; j++) {
            s1 += pfxxTag1[j];
        }
        pfxxTags[0] = s1;
        Log.e("fen", s1);
        /////////2
        String[] split2 = pf2.split("#");
        String[] pfxxTag2 = new String[split2.length];
        for (int i = 0; i < split2.length; i++) {
            if (split2[i].equals("1")) {
                if (i == 0) {
                    sorces[1] = 5;
                } else {
                    sorces[1] = 0;
                }
                pfxxTag2[i] = "#1";
            } else {
                pfxxTag2[i] = "#0";
            }
        }
        String s2 = "";
        for (int j = 0; j < pfxxTag2.length; j++) {
            s2 += pfxxTag2[j];
        }
        pfxxTags[1] = s2;
        Log.e("fen", s2);
        /////////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                if (i == 0) {
                    sorces[2] = 5;
                } else {
                    sorces[2] = 0;
                }
                pfxxTag3[i] = "#1";
            } else {
                pfxxTag3[i] = "#0";
            }
        }
        String s3 = "";
        for (int j = 0; j < pfxxTag3.length; j++) {
            s3 += pfxxTag3[j];
        }
        pfxxTags[2] = s3;
        Log.e("fen", s3);
        /////////4
        String[] split4 = pf4.split("#");
        String[] pfxxTag4 = new String[split4.length];
        for (int i = 0; i < split4.length; i++) {
            if (split4[i].equals("1")) {
                if (i == 0) {
                    sorces[3] = 10;
                } else if (i == 1) {
                    sorces[3] = 5;
                } else {
                    sorces[3] = 0;
                }
                pfxxTag4[i] = "#1";
                Log.e("2", i + "");
            } else {
                pfxxTag4[i] = "#0";
            }
        }
        String s4 = "";
        for (int j = 0; j < pfxxTag4.length; j++) {
            s4 += pfxxTag4[j];
        }
        pfxxTags[3] = s4;
        Log.e("fen", s4);
        /////////5
        String[] split5 = pf5.split("#");
        String[] pfxxTag5 = new String[split5.length];
        for (int i = 0; i < split5.length; i++) {
            if (split5[i].equals("1")) {
                if (i == 0) {
                    sorces[4] = 10;
                } else if (i == 1) {
                    sorces[4] = 5;
                } else {
                    sorces[4] = 0;
                }
                pfxxTag5[i] = "#1";
            } else {
                pfxxTag5[i] = "#0";
            }
        }
        String s5 = "";
        for (int j = 0; j < pfxxTag5.length; j++) {
            s5 += pfxxTag5[j];
        }
        pfxxTags[4] = s5;

        /////////6
        String[] split6 = pf6.split("#");
        String[] pfxxTag6 = new String[split6.length];
        for (int i = 0; i < split6.length; i++) {
            if (split6[i].equals("1")) {
                if (i == 0) {
                    sorces[5] = 10;
                } else if (i == 1) {
                    sorces[5] = 5;
                } else {
                    sorces[5] = 0;
                }
                pfxxTag6[i] = "#1";
            } else {
                pfxxTag6[i] = "#0";
            }
        }
        String s6 = "";
        for (int j = 0; j < pfxxTag6.length; j++) {
            s6 += pfxxTag6[j];
        }
        pfxxTags[5] = s6;
        /////////7
        String[] split7 = pf7.split("#");
        String[] pfxxTag7 = new String[split7.length];
        for (int i = 0; i < split7.length; i++) {
            if (split7[i].equals("1")) {
                if (i == 0) {
                    sorces[6] = 10;
                } else if (i == 1) {
                    sorces[6] = 5;
                } else {
                    sorces[6] = 0;
                }
                pfxxTag7[i] = "#1";
            } else {
                pfxxTag7[i] = "#0";
            }
        }
        String s7 = "";
        for (int j = 0; j < pfxxTag7.length; j++) {
            s7 += pfxxTag7[j];
        }
        pfxxTags[6] = s7;
        /////////8
        String[] split8 = pf8.split("#");
        String[] pfxxTag8 = new String[split8.length];
        for (int i = 0; i < split8.length; i++) {
            if (split8[i].equals("1")) {
                if (i == 0) {
                    sorces[7] = 15;
                } else if (i == 1) {
                    sorces[7] = 10;
                } else if (i == 2) {
                    sorces[7] = 5;
                } else {
                    sorces[7] = 0;
                }
                pfxxTag8[i] = "#1";
            } else {
                pfxxTag8[i] = "#0";
            }
        }
        String s8 = "";
        for (int j = 0; j < pfxxTag8.length; j++) {
            s8 += pfxxTag8[j];
        }
        pfxxTags[7] = s8;
        /////////9
        String[] split9 = pf9.split("#");
        String[] pfxxTag9 = new String[split9.length];
        for (int i = 0; i < split9.length; i++) {
            if (split9[i].equals("1")) {
                if (i == 0) {
                    sorces[8] = 15;
                } else if (i == 1) {
                    sorces[8] = 10;
                } else if (i == 2) {
                    sorces[8] = 5;
                } else {
                    sorces[8] = 0;
                }
                pfxxTag9[i] = "#1";
            } else {
                pfxxTag9[i] = "#0";
            }
        }
        String s9 = "";
        for (int j = 0; j < pfxxTag9.length; j++) {
            s9 += pfxxTag9[j];
        }
        pfxxTags[8] = s9;
        /////////10
        String[] split10 = pf10.split("#");
        String[] pfxxTag10 = new String[split10.length];
        for (int i = 0; i < split10.length; i++) {
            if (split10[i].equals("1")) {
                if (i == 0) {
                    sorces[9] = 10;
                } else if (i == 1) {
                    sorces[9] = 5;
                } else {
                    sorces[9] = 0;
                }
                pfxxTag10[i] = "#1";
            } else {
                pfxxTag10[i] = "#0";
            }
        }
        String s10 = "";
        for (int j = 0; j < pfxxTag10.length; j++) {
            s10 += pfxxTag10[j];
        }
        pfxxTags[9] = s10;
        //评分末尾
        Log.e("总分数", sorces[0] + "--" + sorces[1] + "--" + sorces[2] + "--" + sorces[3] + "--" + sorces[4] + "--" + sorces[5] + "--" + sorces[6] + "--" + sorces[7] + "--" + sorces[8] + "--" + sorces[9] + "--");
        Log.e("总TAG", pfxxTags[0] + "--" + pfxxTags[1] + "--" + pfxxTags[2] + "--" + pfxxTags[3] + "--" + pfxxTags[4] + "--" + pfxxTags[5] + "--" + pfxxTags[6] + "--" + pfxxTags[7] + "--" + pfxxTags[8] + "--" + pfxxTags[9]);
    }

    private void initData() {
        YaChuangActivity da = new YaChuangActivity();

        LinearLayoutManager manager = new LinearLayoutManager(ZiLiAbilityActivity.this);
        rcAdl.setLayoutManager(manager);
        adapter_rec = new RcyMoreAdapter(ZiLiAbilityActivity.this, R.layout.gv_item_cbmore, da.json(result), tag, inPfxx);
        rcAdl.setAdapter(adapter_rec);
    }

    private void initListener() {
        adapter_rec.setCheckListener(new RcyMoreAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String pfxxTag, int sorce, int position, String title) {
                switch (title) {
                    case "进食":
                        sorces[0] = sorce;
                        pfxxTags[0] = pfxxTag;
                        break;
                    case "洗澡":
                        sorces[1] = sorce;
                        pfxxTags[1] = pfxxTag;
                        break;
                    case "修饰":
                        sorces[2] = sorce;
                        pfxxTags[2] = pfxxTag;
                        break;
                    case "穿衣":
                        sorces[3] = sorce;
                        pfxxTags[3] = pfxxTag;
                        break;
                    case "控制大便":
                        sorces[4] = sorce;
                        pfxxTags[4] = pfxxTag;
                        break;
                    case "控制小便":
                        sorces[5] = sorce;
                        pfxxTags[5] = pfxxTag;
                        break;
                    case "如厕":
                        sorces[6] = sorce;
                        pfxxTags[6] = pfxxTag;
                        break;
                    case "床椅转移":
                        sorces[7] = sorce;
                        pfxxTags[7] = pfxxTag;
                        break;
                    case "平地行走":
                        sorces[8] = sorce;
                        pfxxTags[8] = pfxxTag;
                        break;
                    case "上下楼梯":
                        sorces[9] = sorce;
                        pfxxTags[9] = pfxxTag;
                        break;
                    default:
                        break;
                }
                lastPfMsg = pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + pfxxTags[5] + pfxxTags[6] + pfxxTags[7] + pfxxTags[8] + pfxxTags[9];
                all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5] + sorces[6] + sorces[7] + sorces[8] + sorces[9];
                total();
                Log.e("pfxxTags", lastPfMsg + "   " + all);
            }
        });
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all == 100) {
            tvPingguFengxian.setText("无需依赖");
        }else if (all >= 61 && all <= 99) {
            tvPingguFengxian.setText("轻度依赖");
        } else if (all >= 41 && all <= 60) {
            tvPingguFengxian.setText("中度依赖");
        } else if (all <=40) {
            tvPingguFengxian.setText("重度依赖");
        }
        String fxjb = tvPingguFengxian.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);
        tvScore.setText(all + "");
    }


    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.iv_colse1, R.id.tv_commit1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_colse1:
                finish();
                break;
            case R.id.tv_commit1:
                if (tag.equals("add")) {
                    String[] split = lastPfMsg.split("#");
                    int num = 0;
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].equals("1")) {
                            num++;
                        }
                    }
                    if (num < 10) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未全部评分");
                        return;
                    } else {
                        centerDialog = new CenterDialog(ZiLiAbilityActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(ZiLiAbilityActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();

                    }
                } else if (tag.equals("updata")) {
                    centerDialog = new CenterDialog(ZiLiAbilityActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                    centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                        @Override
                        public void OnCenterItemClick(CenterDialog dialog, View view) {
                            if (view.getId() == R.id.bt_yes) {
                                ToastUtils.showLoading(ZiLiAbilityActivity.this);
                                netWorkUpdata();
                                netWorkTime();
                            }
                        }
                    });
                    centerDialog.show();
                }
                break;
        }
    }

    /**
     * 网络保存传参
     */
    private void netWorkAdd() {
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("!!!!!", lastPfMsg);
        }
        String allscore = tvScore.getText().toString().trim();
        String fenxian = tvPingguFengxian.getText().toString().trim();

        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + "" + f + pingguren + f + curdate + f + "3" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + "";
        //网络参数设置
        Log.e("保存参数", canshu);
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_COMMIT)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.e("数据", data);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
                Log.e("fail", info);
            }
        });
    }

    /**
     * 网络修改传参
     */
    private void netWorkUpdata() {
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("-----", lastPfMsg);
        }
        String allscore = tvScore.getText().toString().trim();
        String fenxian = tvPingguFengxian.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + "" + f + pingguren + f + curdate + f + "2" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + "" + f + itemId;
        //网络参数设置
        Log.e("修改参数", canshu);
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_UPDATA)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                ToastUtils.dismissLoading();
                Log.e("数据", data);
                ToastUtils.makeToast(MyApplication.getContext(), "修改成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
                Log.e("fail", info);
            }
        });
    }

    /**
     * 添加时间提醒
     */
    private void netWorkTime() {
        //一共4个参数：病人住院ID，病区ID，评估内容，提醒时间
//        示例：20111201¤0404¤压疮¤2017/6/14 15:33:24
        String canshu = brzyid + f + bqId + f + "跌倒" + f + date;
        //网络参数设置
        Log.e("添加时间提醒", canshu);
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_TIME)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {

                Log.e("数据", "添加提醒成功");
            }

            @Override
            public void fail(String info) {
                Log.e("fail", info + "添加提醒成功");
            }
        });
    }
}
