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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.MoreChooseAdapter;
//import enjoyor.enjoyorzemobilehealth.adapter.RcyAdapter;
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

public class FallActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_colse_fall)
    ImageView ivColseFall;
    @BindView(R.id.tv_commit_fall)
    TextView tvCommitFall;
    @BindView(R.id.tv_title_fall)
    TextView tvTitleFall;
    @BindView(R.id.tv_pinggu_fall)
    TextView tvPingguFall;
    @BindView(R.id.tv_score_fall)
    TextView tvScoreFall;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_fall)
    RecyclerView rcFall;
    @BindView(R.id.gv_fall)
    CoustomListView gvFall;
    @BindView(R.id.rb_before)
    RadioButton rbBefore;
    @BindView(R.id.rb_after)
    RadioButton rbAfter;
    @BindView(R.id.rd_gp)
    RadioGroup rdGp;
    @BindView(R.id.activity_fall)
    LinearLayout activityFall;

    private String result = Constant.FALL;

    private List<CheckBoxBean> listCb;
    private MoreChooseAdapter cbadapter;
//    private RcyAdapter adapter_rec;
    private String[] endNames = new String[]{"A.卧床休息、留陪人", "B.根据医嘱使用保护性约束带", "C.行走、活动、如厕时需搀扶;头眩晕时避免上、下床", "D.根据医嘱关注血压及血糖的变化", "E.告知病人及家属药物的副作用及注意事项", "F.保持地面干燥;光线充足;有障碍物时要防止标识牌",
            "G.嘱病人穿大小合适的拖鞋", "H.使用带轮子的设施、设备都要锁定轮子,防止滑动。设施、设备出现故障及时报修", "I.告知病人及家属卧床时床档必须在功能位,教给病人及家属床档的正确方法", "J.根据需要床旁放置便盆或尿壶,沐浴时放置座椅,如厕时使用扶手,有情况时使用紧急呼叫器"};

    private int all = 0;
    private String allString = "";
    private String date;
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
    private ZhierCall zhierCall;
    private String sqsh = "1#0";//术前后 默认
    private String shuqian = "1", shuhou = "0";
    private String cuoShimsg = "";//措施信息
    private String lastPfMsg = "";//最后评分信息
    private Intent intent;
    private String[] beanSqsh = null;
    private String[] beanCsxx = null;
    private String beanPfxx = "";
    private String inShoushu;
    private String inCsxx;
    private String inPfxx;
    private String itemId;
    private String[] inCsString;
    private String[] hlcsS = new String[]{"", "", "", "", "", "", "", "", "", ""};
    private String[] csTags = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String f;
    private DateUtil dateUtil;
    private MyApplication instance;
    private int[] sorces = new int[]{0, 0, 0, 0, 0, 0};
    private String[] pfxxTags = new String[]{"", "", "", "", "", ""};
    private String tag;
    private CenterDialog centerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall);
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
        tvTitleFall.setText(title);
        tag = intent.getStringExtra(Constant.TAG);
        if (tag.equals("add")) {
            beanCsxx = new String[endNames.length];
            for (int i = 0; i < endNames.length; i++) {
                beanCsxx[i] = "0";
                Log.e("beabbb", beanCsxx[i]);
            }
            Log.e("adddd", beanCsxx.toString() + beanCsxx.length);

        } else if (tag.equals("updata")) {
            AllWXYS bean = (AllWXYS) intent.getSerializableExtra("bean");
            String fenxian = bean.getJB();

            String fen = bean.getZPF();
            inShoushu = bean.getSqsh();
            inCsxx = bean.getCSXX();
            inPfxx = bean.getFZxx();
            String csString = bean.getHLCS();
            itemId = bean.getID();

            cuoShimsg = inCsxx;
            lastPfMsg = inPfxx;
            allString = csString;
            sqsh = inShoushu;
            Log.e("信息", inShoushu + "--" + inCsxx + "--" + inPfxx + "--" + itemId);

            beanSqsh = inShoushu.split("#");

            beanSqsh = inShoushu.split("#");
            if (beanSqsh[0].equals("1")) {
                rbBefore.setChecked(true);
                rbAfter.setChecked(false);
                rbAfter.setEnabled(false);
            } else {
                rbAfter.setChecked(true);
                rbBefore.setChecked(false);
                rbBefore.setEnabled(false);
            }
            initPfxx();   //评分信息处理

            beanCsxx = inCsxx.split("#");//措施标识
            inCsString = csString.split("#");//措施内容
            Log.e("措施内容長度", inCsString.length + "");
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

            beanPfxx = inPfxx;

            tvPingguFall.setText(fenxian);
            tvScoreFall.setText(fen);

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

    /**
     * 初始化评分信息
     */
    private void initPfxx() {
        String pf1 = inPfxx.substring(0, 3);//2
        String pf2 = inPfxx.substring(4, 7);//2
        String pf3 = inPfxx.substring(8, 11);//2
        String pf4 = inPfxx.substring(12, 15);//2
        String pf5 = inPfxx.substring(16, 27);//3
        String pf6 = inPfxx.substring(28, inPfxx.length());//2
        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("1")) {
                if (split1[0].equals("1")) {
                    sorces[0] = 1;
                } else {
                    sorces[0] = 0;
                }
                pfxxTag1[i] = "#1";
                Log.e("1", i + "--" + sorces[0]);
            } else {
                pfxxTag1[i] = "#0";
            }
        }
        String s1 = "";
        for (int j = 0; j < pfxxTag1.length; j++) {
            s1 += pfxxTag1[j];
        }
        pfxxTags[0] = s1;
        Log.e("fen1", s1);
        /////////2
        String[] split2 = pf2.split("#");
        String[] pfxxTag2 = new String[split2.length];
        for (int i = 0; i < split2.length; i++) {
            if (split2[i].equals("1")) {
                if (split2[1].equals("1")) {
                    sorces[1] = 6;
                } else {
                    sorces[1] = 0;
                }
                pfxxTag2[i] = "#1";
                Log.e("2", i + "--" + sorces[1]);
            } else {
                pfxxTag2[i] = "#0";
            }
        }
        String s2 = "";
        for (int j = 0; j < pfxxTag2.length; j++) {
            s2 += pfxxTag2[j];
        }
        pfxxTags[1] = s2;
        Log.e("fen2", s2);
        /////////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                if (split3[0].equals("1")) {
                    sorces[2] = 0;
                } else if (split3[1].equals("1")) {
                    sorces[2] = 6;
                }
                pfxxTag3[i] = "#1";
                Log.e("3", i + "--" + sorces[2]);
            } else {
                pfxxTag3[i] = "#0";
            }
        }
        String s3 = "";
        for (int j = 0; j < pfxxTag3.length; j++) {
            s3 += pfxxTag3[j];
        }
        pfxxTags[2] = s3;
        Log.e("fen3", s3);
        /////////4
        String[] split4 = pf4.split("#");
        String[] pfxxTag4 = new String[split4.length];
        for (int i = 0; i < split4.length; i++) {
            if (split4[i].equals("1")) {
                if (split4[0].equals("1")) {
                    sorces[3] = 1;
                } else if(split4[1].equals("1")){
                    sorces[3] = 3;
                }
                pfxxTag4[i] = "#1";
                Log.e("4", i + "--" + sorces[3]);
            } else {
                pfxxTag4[i] = "#0";
            }
        }
        String s4 = "";
        for (int j = 0; j < pfxxTag4.length; j++) {
            s4 += pfxxTag4[j];
        }
        pfxxTags[3] = s4;
        Log.e("fen4", s4);
        /////////5
        String[] split5 = pf5.split("#");
        String[] pfxxTag5 = new String[split5.length];
        for (int i = 0; i < split5.length; i++) {
            if (split5[i].equals("1")) {
                if (split5[2].equals("1")||split5[5].equals("1")) {
                    sorces[4] = 2;
                }  else {
                    sorces[4] = 1;
                }
                pfxxTag5[i] = "#1";
                Log.e("5", i + "--" + sorces[4]);
            } else {
                pfxxTag5[i] = "#0";
            }
        }
        String s5 = "";
        for (int j = 0; j < pfxxTag5.length; j++) {
            s5 += pfxxTag5[j];
        }
        pfxxTags[4] = s5;
        Log.e("fen5", s5);
        /////////6
        String[] split6 = pf6.split("#");
        String[] pfxxTag6 = new String[split6.length];
        for (int i = 0; i < split6.length; i++) {
            if (split6[i].equals("1")) {
                if (split6[0].equals("1")) {
                    sorces[5] = 5;
                } else if (split6[1].equals("1")||split6[3].equals("1")){
                    sorces[5] = 3;
                }else if (split6[2].equals("1")){
                    sorces[5] = 2;
                }else if (split6[4].equals("1")){
                    sorces[5] = 6;
                }
                pfxxTag6[i] = "#1";
                Log.e("6", i + "--" + sorces[5]);
            } else {
                pfxxTag6[i] = "#0";
            }
        }
        String s6 = "";
        for (int j = 0; j < pfxxTag6.length; j++) {
            s6 += pfxxTag6[j];
        }
        pfxxTags[5] = s6;
        Log.e("fen6", s6);
        Log.e("总TAG", pfxxTags[0] + "--" + pfxxTags[1] + "--" + pfxxTags[2] + "--" + pfxxTags[3] + "--" + pfxxTags[4] + "--" + pfxxTags[5]);
    }

    private void initData() {
        YaChuangActivity da = new YaChuangActivity();

        LinearLayoutManager manager = new LinearLayoutManager(FallActivity.this);
        rcFall.setLayoutManager(manager);
//        adapter_rec = new RcyAdapter(FallActivity.this, da.json(result), beanPfxx, tag, "fall");
//        rcFall.setAdapter(adapter_rec);

        listCb = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listCb.add(new CheckBoxBean(endNames[i], beanCsxx[i],"0"));
        }
        cbadapter = new MoreChooseAdapter(FallActivity.this, listCb, tag, R.layout.gv_item_checkbox);
        gvFall.setAdapter(cbadapter);
    }

    private void initListener() {
        ivColseFall.setOnClickListener(this);
        tvCommitFall.setOnClickListener(this);
        if (tag.equals(Constant.ADD)) {
            rbBefore.setChecked(true);//默认
        }
        rdGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbBefore.getId()) {
                    initShouShu();

                    rbBefore.setChecked(true);
                    rbAfter.setChecked(false);

                    shuqian = "1";
                    shuhou = "0";
                    sqsh = shuqian + "#" + shuhou;
                } else if (checkedId == rbAfter.getId()) {
                    initShouShu();

                    rbBefore.setChecked(false);
                    rbAfter.setChecked(true);
                    shuqian = "0";
                    shuhou = "1";
                    sqsh = shuqian + "#" + shuhou;
                }
                callRcy();
            }
        });
        callRcy();
    }


    private void callRcy() {
//        adapter_rec.setChildListener(new RcyAdapter.OnChildClickListener() {
//            @Override
//            public void onCheckClick(String s, int po, String pingFenMsg) {
//                String sorce = s.substring(0, s.length() - 1);
//                for (int i = 0; i < pfxxTags.length; i++) {
//                    if (i == po) {
//                        pfxxTags[i] = pingFenMsg;
//                    }
//                }
//                lastPfMsg = pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + pfxxTags[5];
//                Log.e("评分信息tag", lastPfMsg);
//                switch (po) {
//                    case 0:
//                        sorces[0] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        Log.e("分1", sorces[0] + "");
//                        break;
//                    case 1:
//                        sorces[1] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        Log.e("分2", sorces[1] + "");
//                        break;
//                    case 2:
//                        sorces[2] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        Log.e("分3", sorces[2] + "");
//                        break;
//                    case 3:
//                        sorces[3] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        Log.e("分4", sorces[3] + "");
//                        total();
//
//                        break;
//                    case 4:
//                        sorces[4] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        Log.e("分5", sorces[4] + "");
//                        break;
//                    case 5:
//                        sorces[5] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        Log.e("分6", sorces[5] + "");
//                        break;
//                    default:
//                        break;
//                }
//                Log.e("分数",  all+"--" +sorces[0] +"-- "+ sorces[1] +"--"+ sorces[2] +"--" + sorces[3]+"--"  + sorces[4] +"--" + sorces[5]);
//            }
//        });
        cbadapter.setCheckListener(new MoreChooseAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String name, int position, CheckBox checkBox, String tag) {
                Log.e("gridview的数量", listCb.size() + "");

                CheckBoxBean bean = listCb.get(position);
                String names = bean.getName();
                boolean isCheck = bean.isChecked();
                clickGridview(position, names, isCheck);
            }
        });
    }

    /**
     * 点击术前术后全部数据清空
     */
    private void initShouShu() {
        all = 0;
        sorces[0] = 0;
        sorces[1] = 0;
        sorces[2] = 0;
        sorces[3] = 0;
        sorces[4] = 0;
        sorces[5] = 0;
        total();
        initData();

        hlcsS[0] = "";
        hlcsS[1] = "";
        hlcsS[2] = "";
        hlcsS[3] = "";
        hlcsS[4] = "";
    }

    /**
     * 点击gridview的多选事件
     *
     * @param position
     * @param names
     * @param checked
     */
    private void clickGridview(int position, String names, boolean checked) {
        switch (position) {
            case 0:
                dianjiCuoshiMsg(0, names, checked);

                break;
            case 1:
                dianjiCuoshiMsg(1, names, checked);

                break;
            case 2:
                dianjiCuoshiMsg(2, names, checked);

                break;
            case 3:
                dianjiCuoshiMsg(3, names, checked);

                break;
            case 4:
                dianjiCuoshiMsg(4, names, checked);

                break;
            case 5:
                dianjiCuoshiMsg(5, names, checked);

                break;
            case 6:
                dianjiCuoshiMsg(6, names, checked);

                break;
            case 7:
                dianjiCuoshiMsg(7, names, checked);

                break;
            case 8:
                dianjiCuoshiMsg(8, names, checked);

                break;
            case 9:
                dianjiCuoshiMsg(9, names, checked);

                break;
            default:
                break;
        }
    }

    /**
     * 点击措施信息
     * @param position
     * @param names
     * @param checked
     */
    private void dianjiCuoshiMsg(int position, String names, boolean checked) {
        if (checked) {
            hlcsS[position] = "#" + names;
            allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9];
            csTags[position] = "1";
            cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9];
        } else {
            hlcsS[position] = "";
            allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9];
            csTags[position] = "0";
            cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9];
        }
        Log.e("点击措施信息",allString+"=="+cuoShimsg);
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all <= 24) {
            tvPingguFall.setText("无风险");
        } else if (all >= 25 && all <= 44) {
            tvPingguFall.setText("低风险");
        } else if (all >= 45) {
            tvPingguFall.setText("高风险");
        }
        String fxjb = tvPingguFall.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);
        tvScoreFall.setText(all + "");
    }

    private void getCurDate(String fxjb) {
        String sfm = date.substring(date.length() - 8, date.length());
        if (fxjb.equals("无风险")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("低风险")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("高风险")) {
            String day = date = dateUtil.afterDay();
            date = day + " " + sfm;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse_fall:
                finish();
                break;
            case R.id.tv_commit_fall:
                if (tag.equals("add")) {
                    String[] split = lastPfMsg.split("#");
                    int num = 0;
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].equals("1")) {
                            num++;
                        }
                    }
                    if (num < 6) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未全部评分");
                        return;
                    } else if (TextUtils.isEmpty(cuoShimsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(FallActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(FallActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();

                    }
                } else if (tag.equals("updata")) {
                    if (inCsxx.equals(cuoShimsg) && inPfxx.equals(lastPfMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                    } else {
                        centerDialog = new CenterDialog(FallActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(FallActivity.this);
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
        if (allString.startsWith("#")) {
            allString = allString.substring(1, allString.length());
            Log.e("!!!!!", allString);
        }
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("-----", lastPfMsg);
        }
        String allscore = tvScoreFall.getText().toString().trim();
        String fenxian = tvPingguFall.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "2" + f + "" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg + f + itemId;
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
                Log.e("数据", data);

                ToastUtils.makeToast(MyApplication.getContext(), "修改成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
                Log.e("fail", info);
            }
        });
    }

    /**
     * 网络保存传参
     */
    private void netWorkAdd() {
        if (allString.startsWith("#")) {
            allString = allString.substring(1, allString.length());
            Log.e("!!!!!", allString);
        }
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("!!!!!", lastPfMsg);
        }
        String allscore = tvScoreFall.getText().toString().trim();
        String fenxian = tvPingguFall.getText().toString().trim();

        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "2" + f + "" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg;
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
                ToastUtils.makeToast(MyApplication.getContext(), "保存成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
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
                ToastUtils.dismissLoading();
                Log.e("数据", "添加提醒成功");
            }

            @Override
            public void fail(String info) {
                Log.e("fail", info + "添加提醒成功");
            }
        });
    }
}
