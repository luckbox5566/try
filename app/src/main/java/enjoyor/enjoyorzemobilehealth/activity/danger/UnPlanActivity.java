package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.CuoShiMagAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.MoreChooseAdapter;
//import enjoyor.enjoyorzemobilehealth.adapter.RcyAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.RcyMoreAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.AllWXYS;
import enjoyor.enjoyorzemobilehealth.entities.CheckBoxBean;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import enjoyor.enjoyorzemobilehealth.views.CoustomListView;
import my_network.NetWork;
import my_network.ZhierCall;

public class UnPlanActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_nodanger)
    ImageView ivNodanger;
    @BindView(R.id.activity_un_plan)
    LinearLayout activityUnPlan;


    @BindView(R.id.iv_colse_plan)
    ImageView ivColsePlan;
    @BindView(R.id.tv_commit_plan)
    TextView tvCommitPlan;
    @BindView(R.id.tv_title_plan)
    TextView tvTitlePlan;
    @BindView(R.id.tv_pinggu_plan)
    TextView tvPingguPlan;
    @BindView(R.id.tv_score_plan)
    TextView tvScorePlan;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_plan)
    RecyclerView rcPlan;
    @BindView(R.id.gv_plan)
    CoustomListView gvPlan;
    private String result = Constant.UNPLAN;
    private int[] sorces = new int[]{0, 0, 0, 0, 0};
    private int all = 0;

    private String[] hlcsS = new String[]{"", ""};
    private List<CheckBoxBean> listCb;
    private CuoShiMagAdapter cbadapter;
    private RcyMoreAdapter adapter_rec;
    private String[] endNames = new String[]{"A.标准预防", "B.标准预防+预防性干预措施"};
    private String date;
    private String uesrid;
    private String brzyid;
    private String rydate;

    private String brid;
    private String sex;
    private String age;
    private String bqmc;
    private String pingguren;
    private String bqId;
    private ZhierCall zhierCall;
    private String sqsh = "0";//术前后 默认
    private String cuoShitag = "";//措施信息
    private String cuoshiMsg = "";//措施内容
    private String[] csTags = new String[]{"0", "0"};
    private String lastPfMsg = "";//最后评分信息

    private DateUtil dateUtil;
    /**
     * 修改
     */
    private Intent intent;
    private String[] beanSqsh = null;
    private String[] beanCsxx = null;
    private String beanPfxx = null;
    private String[] inCsString = null;
    private String tag;//添加和修改标识
    private String itemId;//修改item的ID
    private String inCsxx;//护理措施标识
    private String inPfxx;
    private String f;
    private String updataFen;
    private MyApplication instance;
    private String name;
    private CenterDialog centerDialog;
    private String[] pfxxTags = new String[]{"#0#0", "#0#0#0#0#0#0#0", "#0#0#0#0#0#0#0#0", "#0#0", "#0#0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_plan);
        ButterKnife.bind(this);
        init();
        initCanShu();

        initData();
        initListener();
    }

    private void initCanShu() {
        intent = getIntent();
        String title = getIntent().getStringExtra("title");
        tvTitlePlan.setText(title);

        tag = intent.getStringExtra(Constant.TAG);
        if (tag.equals("add")) {
            beanCsxx = new String[endNames.length];
            for (int i = 0; i < endNames.length; i++) {
                beanCsxx[i] = "0";
                Log.e("datas.length", endNames.length + "");
            }
        } else if (tag.equals("updata")) {
            AllWXYS bean = (AllWXYS) intent.getSerializableExtra("bean");
            String fenxian = bean.getJB();

            updataFen = bean.getZPF();
            inCsxx = bean.getCSXX();
            String csString = bean.getHLCS();
            itemId = bean.getID();
            inPfxx = bean.getFZxx();
            //判断是否一样
            cuoShitag = inCsxx;
            lastPfMsg = inPfxx;
            cuoshiMsg = csString;
            initPfxx();   //评分信息处理

            Log.e("信息", "--" + inCsxx + "--" + csString + "--" + inPfxx + "--" + itemId);

            beanCsxx = inCsxx.split("#");//措施标识
            inCsString = csString.split("#");//措施内容
            Log.e("措施内容長度", inCsString.length + "");

            if(beanCsxx[0].equals("0")){

            }
            for (int i = 0; i < endNames.length; i++) {
                if (beanCsxx[i].equals("1")) {
                    hlcsS[i] = "#" + endNames[i];
                    csTags[i] = "1";
                } else {
                    hlcsS[i] = "";
                    csTags[i] = "0";
                    Log.e("措施标识0", hlcsS[i] + csTags[i]);
                }
            }
            beanCsxx = inCsxx.split("#");//标识

            if(Integer.parseInt(updataFen)>=5){
                ivNodanger.setImageResource(R.drawable.btn_choose_null);
            }else {
                ivNodanger.setImageResource(R.drawable.btn_choose_on);
            }
            tvPingguPlan.setText(fenxian);
            tvScorePlan.setText(updataFen);

            getCurDate(fenxian.toString().trim());
        }
        uesrid = (String) SaveUtils.get(this, Constant.USERID, "");//用户登录ID
        brzyid = getIntent().getStringExtra(Constant.BRZYID);//病人住院ID
        rydate = getIntent().getStringExtra(Constant.RYSJ);//病人入院时间
        name = getIntent().getStringExtra(Constant.BRNAME);//病人入院时间

        brid = getIntent().getStringExtra(Constant.BRID);//病人id
        sex = getIntent().getStringExtra(Constant.SEX);//病人性别
        age = getIntent().getStringExtra(Constant.AGE);//病人年龄

        bqmc = (String) SaveUtils.get(this, Constant.BQMC, "");//病区名称
        bqId = instance.getBqdm();//病区ID
        pingguren = instance.getYhxm();//评估人姓名
        Log.e("评估人姓名", pingguren);
    }

    /**
     * 初始化评分信息
     */
    private void initPfxx() {
        String pf1 = inPfxx.substring(0, 3);//2
        String pf2 = inPfxx.substring(4, 17);//7
        String pf3 = inPfxx.substring(18, 33);//8
        String pf4 = inPfxx.substring(34, 37);//2
        String pf5 = inPfxx.substring(38, inPfxx.length());//2
        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("1")) {
                sorces[0] = 1;
                pfxxTag1[i] = "#1";
                Log.e("1", i + "");
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
                sorces[1] = 5;
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

        /////////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                sorces[2] = 2;
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

        /////////4
        String[] split4 = pf4.split("#");
        String[] pfxxTag4 = new String[split4.length];
        for (int i = 0; i < split4.length; i++) {
            if (split4[i].equals("1")) {
                sorces[3] = 5;
                pfxxTag4[i] = "#1";

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
                sorces[4] = 2;
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
        //评分末尾
        Log.e("总分数", sorces[0] + "--" + sorces[1] + "--" + sorces[2] + "--" + sorces[3] + "--" + sorces[4] + "");
        Log.e("总TAG", pfxxTags[0] + "--" + pfxxTags[1] + "--" + pfxxTags[2] + "--" + pfxxTags[3] + "--" + pfxxTags[4]);
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);
        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        instance = MyApplication.getInstance();
    }

    private void initData() {
        YaChuangActivity da = new YaChuangActivity();
        LinearLayoutManager manager = new LinearLayoutManager(UnPlanActivity.this);
        rcPlan.setLayoutManager(manager);
        adapter_rec = new RcyMoreAdapter(UnPlanActivity.this, R.layout.gv_item_cbmore, da.json(result), tag, inPfxx);
        rcPlan.setAdapter(adapter_rec);

        listCb = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listCb.add(new CheckBoxBean(endNames[i], beanCsxx[i], "0"));
        }
        cbadapter = new CuoShiMagAdapter(UnPlanActivity.this, listCb, tag + "#unplan", R.layout.gv_item_cuoshimsg, cuoShitag);
        gvPlan.setAdapter(cbadapter);
    }

    private void initListener() {
        ivColsePlan.setOnClickListener(this);
        tvCommitPlan.setOnClickListener(this);

        adapter_rec.setCheckListener(new RcyMoreAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String pfxxTag, int sorce, int position, String title) {
                switch (title) {
                    case "年龄":
                        sorces[0] = sorce;
                        pfxxTags[0] = pfxxTag;
                        break;
                    case "高危导管(Ⅰ类导管)":
                        sorces[1] = sorce;
                        pfxxTags[1] = pfxxTag;
                        break;
                    case "非高危导管(Ⅱ类导管)":
                        sorces[2] = sorce;
                        pfxxTags[2] = pfxxTag;
                        break;
                    case "意识状态":
                        sorces[3] = sorce;
                        pfxxTags[3] = pfxxTag;
                        break;
                    case "管路刀口":
                        sorces[4] = sorce;
                        pfxxTags[4] = pfxxTag;
                        break;
                    default:
                        break;
                }
                lastPfMsg = pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4];
                all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4];
                total();
                Log.e("====", sorces[0] + "--" + sorces[1] + "--" + sorces[2] + "--" + sorces[3] + "--" + sorces[4]);
                Log.e("pfxxTags", lastPfMsg + "  " + pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + all + "   ");
            }
        });
        //        护理措施
        cbadapter.setCheckListener(new CuoShiMagAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String name, int position, boolean isCheck) {
                if (position == 0) {
                    cuoshiMsg = name;
                    cuoShitag = "1#0";
                } else if (position == 1) {
                    cuoshiMsg = name;
                    cuoShitag = "0#1";
                }
                Log.e("9999", cuoshiMsg + "   " + cuoShitag);
            }
        });
    }
    /**
     * 计算风险程度
     */
    private void total() {
        if (all >= 5) {
            tvPingguPlan.setText("高风险");
            ivNodanger.setImageResource(R.drawable.btn_choose_null);
            sqsh = "1";
        } else if (all < 5) {
            tvPingguPlan.setText("低风险");
            ivNodanger.setImageResource(R.drawable.btn_choose_on);
            sqsh = "0";
        }
//        else if (all >= 1 && all <= 3) {
//            tvPingguPlan.setText("低度风险");
//            ivNodanger.setImageResource(R.drawable.btn_choose_on);
//            sqsh = "0";
//        }
        String fxjb = tvPingguPlan.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);

        tvScorePlan.setText(all + "");
    }

    private void getCurDate(String fxjb) {
        String sfm = date.substring(date.length() - 8, date.length());
        if (fxjb.equals("高风险")) {
            String day = dateUtil.afterDay();
            date = day + " " + sfm;
        } else if (fxjb.equals("低风险")) {
            String day = dateUtil.after3Day();
            date = day + " " + sfm;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse_plan:
                UnPlanActivity.this.finish();
                break;
            case R.id.tv_commit_plan:
                if (tag.equals("add")) {
                    if (pfxxTags[0].equals("#0#0")) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择年龄（必选项）");
                        return;
                    } else if (TextUtils.isEmpty(cuoShitag)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(UnPlanActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(UnPlanActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();

                    }
                } else if (tag.equals(Constant.UPDATA)) {
                    if (inCsxx.equals(cuoShitag) && inPfxx.equals(lastPfMsg)) {
                        Log.e("是否一样", inCsxx + "=" + cuoShitag + "---" + inPfxx + "=" + lastPfMsg);
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                        return;
                    } else {
                        centerDialog = new CenterDialog(UnPlanActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(UnPlanActivity.this);
                                    netWorkUpdata();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 网络修改传参
     */
    private void netWorkUpdata() {
        if (cuoShitag.startsWith("#")) {
            cuoShitag = cuoShitag.substring(1, cuoShitag.length());
            Log.e("!!!!!", cuoShitag);
        }
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("-----", lastPfMsg);
        }
        String allscore = tvScorePlan.getText().toString().trim();
        String fenxian = tvPingguPlan.getText().toString().trim();
        String curdate = dateUtil.getDate();

        String zhpfxx = lastPfMsg.replace("##", "#");
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "4" + f + "" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + zhpfxx + f + cuoShitag + f + itemId;
        Log.e("网络修改传参", canshu);
        //网络参数设置
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
                Log.e("fail", info);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
            }
        });
    }

    /**
     * 网络添加传参
     */
    private void netWorkAdd() {
        if (cuoShitag.startsWith("#")) {
            cuoShitag = cuoShitag.substring(1, cuoShitag.length());
            Log.e("!!!!!", cuoShitag);
        }
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("!!!!!", lastPfMsg);
        }
        String allscore = tvScorePlan.getText().toString().trim();
        String fenxian = tvPingguPlan.getText().toString().trim();


        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "4" + f + "" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShitag;
        Log.e("网络添加传参", canshu);
        //网络参数设置
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
                Log.e("fail", info);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
            }
        });
    }

    /**
     * 添加时间提醒
     */
    private void netWorkTime() {
        //一共4个参数：病人住院ID，病区ID，评估内容，提醒时间
//        示例：20111201¤0404¤压疮¤2017/6/14 15:33:24
        String canshu = brzyid + f + bqId + f + "非计划拔管" + f + date;
        //网络参数设置
        Log.e("添加时间参数设置", canshu);
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
