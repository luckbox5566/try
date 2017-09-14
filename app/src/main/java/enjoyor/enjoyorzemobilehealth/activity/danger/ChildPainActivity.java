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
import enjoyor.enjoyorzemobilehealth.views.CoustomGridView;
import my_network.NetWork;
import my_network.ZhierCall;

public class ChildPainActivity extends AppCompatActivity implements View.OnClickListener {
    private String result = Constant.CHILDPAIN;

    @BindView(R.id.iv_colse_child)
    ImageView ivColseChild;
    @BindView(R.id.tv_commit_child)
    TextView tvCommitChild;
    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_pinggu_child)
    TextView tvPingguChild;
    @BindView(R.id.tv_score_child)
    TextView tvScoreChild;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_child)
    RecyclerView rcChild;
    @BindView(R.id.gv_child)
    CoustomGridView gvChild;
    //    private int sorces[0] = 0, sorces[1] = 0, sorces[2] = 0, sorces[3] = 0, sorces[4] = 0, sorces[5] = 0;
    private int all = 0;
//    private String hlcsS[0] = "", hlcsS[1] = "", hlcsS[2] = "", hlcsS[3] = "", hlcsS[4] = "", hlcsS[5] = "";
    private String allString = "";

    private List<CheckBoxBean> listCb;
    private MoreChooseAdapter cbadapter;
//    private RcyAdapter adapter_rec;
    private String[] endNames = new String[]{"A.心理支持", "B.药物治疗", "C.物理止痛", "D.促进病人舒适", "E.分散注意力", "F.健康教育"};
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
    private String sqsh = "";//术前后 默认

    private String cuoShimsg = "";//措施信息
    private String pfMsg = "";//评分信息#
    private String lastPfMsg = "";//最后评分信息
    private String f;
    private DateUtil dateUtil;
    private Intent intent;
    private String tag;
    private String[] beanCsxx;
    private String updataFen;
    private String inCsxx;
    private String csString;
    private String itemId;
    private String inPfxx;
    private String[] inCsString;
    private int[] sorces = new int[]{0, 0, 0, 0, 0, 0};
    private String[] hlcsS = new String[]{"", "", "", "", "", ""};
    private String[] csTags = new String[]{"0", "0", "0", "0", "0", "0"};
    private String pfxxTags[] =new String[]{"","","","","","",};
    private String sfm;
    private CenterDialog centerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_pain);
        ButterKnife.bind(this);
        init();
        initCanShu();
        initData();
        initListener();
    }

    private void initCanShu() {
        intent = getIntent();
        String title = intent.getStringExtra("title");
        tvTitleChild.setText(title);
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
            csString = bean.getHLCS();
            itemId = bean.getID();
            inPfxx = bean.getFZxx();
            //判断是否一样
            cuoShimsg = inCsxx;
            lastPfMsg = inPfxx;
            allString = csString;
            initPfxx();   //评分信息处理

            Log.e("信息", "--" + inCsxx + "--" + csString + "--" + inPfxx + "--" + itemId);


            beanCsxx = inCsxx.split("#");//措施标识    适配器中传参
            inCsString = csString.split("#");//措施内容
            for (int i = 0; i < endNames.length; i++) {
                if (beanCsxx[i].equals("1")) {
                    hlcsS[i] = "#" + endNames[i];
                    Log.e("初始化措施信息", hlcsS[i] + "--" + i);
                    csTags[i] = "1";
                    Log.e("措施标识1", hlcsS.length + "==" + inCsString.length + beanCsxx.length + "==" + endNames.length + i + "--" + hlcsS[i]);
                } else {
                    hlcsS[i] = "";
                    csTags[i] = "0";
                    Log.e("措施标识0", hlcsS[i] + csTags[i]);
                }
            }


            tvPingguChild.setText(fenxian);
            tvScoreChild.setText(updataFen);
            getCurDate(fenxian.toString().trim());
        }

        uesrid = (String) SaveUtils.get(this, Constant.USERID, "");//用户登录ID
        brzyid = getIntent().getStringExtra(Constant.BRZYID);//病人住院ID
        rydate = getIntent().getStringExtra(Constant.RYSJ);//病人入院时间
        name = getIntent().getStringExtra(Constant.BRNAME);//病人姓名
        brid = getIntent().getStringExtra(Constant.BRID);//病人id
        sex = getIntent().getStringExtra(Constant.SEX);//病人性别
        age = getIntent().getStringExtra(Constant.AGE);//病人年龄
        bqmc = (String) SaveUtils.get(this, Constant.BQMC, "");//病区名称
        MyApplication instance = MyApplication.getInstance();

//        loginid = instance.getYhgh();//用户工号，登录ID 1000
        pingguren = instance.getYhxm();//评估人姓名
        bqId = instance.getBqdm();//病区ID
    }

    private void initPfxx() {
        String pf1 = inPfxx.substring(0, 5);//3
        String pf2 = inPfxx.substring(6, 11);//3
        String pf3 = inPfxx.substring(12, 17);//3
        String pf4 = inPfxx.substring(18, 23);//3
        String pf5 = inPfxx.substring(24, 29);//3
        String pf6 = inPfxx.substring(30, 35);//3
        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            pf1.split("#");
            if (split1[i].equals("1")) {
                sorces[0] = i;
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
        //////2
        String[] split2 = pf2.split("#");
        String[] pfxxTag2 = new String[split2.length];
        for (int i = 0; i < split2.length; i++) {
            pf2.split("#");
            if (split2[i].equals("1")) {
                sorces[1] = i;
                pfxxTag2[i] = "#1";
                Log.e("1", i + "");
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
        //////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            pf3.split("#");
            if (split3[i].equals("1")) {
                sorces[2] = i;
                pfxxTag3[i] = "#1";
                Log.e("1", i + "");
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
        //////4
        String[] split4 = pf4.split("#");
        String[] pfxxTag4 = new String[split4.length];
        for (int i = 0; i < split4.length; i++) {
            pf4.split("#");
            if (split4[i].equals("1")) {
                sorces[3] = i;
                pfxxTag4[i] = "#1";
                Log.e("4", i + "");
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
        //////5
        String[] split5 = pf5.split("#");
        String[] pfxxTag5 = new String[split5.length];
        for (int i = 0; i < split5.length; i++) {
            pf5.split("#");
            if (split5[i].equals("1")) {
                sorces[4] = i;
                pfxxTag5[i] = "#1";
                Log.e("5", i + "");
            } else {
                pfxxTag5[i] = "#0";
            }
        }
        String s5 = "";
        for (int j = 0; j < pfxxTag5.length; j++) {
            s5 += pfxxTag5[j];
        }
        pfxxTags[4] = s5;
        Log.e("fen", s5);
        //////6
        String[] split6 = pf6.split("#");
        String[] pfxxTag6 = new String[split6.length];
        for (int i = 0; i < split6.length; i++) {
            pf6.split("#");
            if (split6[i].equals("1")) {
                sorces[5] = i;
                pfxxTag6[i] = "#1";
                Log.e("6", i + "");
            } else {
                pfxxTag6[i] = "#0";
            }
        }
        String s6 = "";
        for (int j = 0; j < pfxxTag6.length; j++) {
            s6 += pfxxTag6[j];
        }
        pfxxTags[5] = s6;
        Log.e("fen", s6);
        Log.e("总TAG", pfxxTags[0] + "--" + pfxxTags[1] + "--" + pfxxTags[2] + "--" + pfxxTags[3] + "--" + pfxxTags[4] + "--" + pfxxTags[5]);
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);
        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        sfm = date.substring(date.length() - 8, date.length());
    }

    private void initData() {
        YaChuangActivity da = new YaChuangActivity();
        LinearLayoutManager manager = new LinearLayoutManager(ChildPainActivity.this);
        rcChild.setLayoutManager(manager);
//        adapter_rec = new RcyAdapter(ChildPainActivity.this, da.json(result), inPfxx, tag, "childpain");
//        rcChild.setAdapter(adapter_rec);

        listCb = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listCb.add(new CheckBoxBean(endNames[i], beanCsxx[i],"0"));
        }
        cbadapter = new MoreChooseAdapter(ChildPainActivity.this, listCb, tag, R.layout.gv_item_checkbox);
        gvChild.setAdapter(cbadapter);
    }

    private void initListener() {
        ivColseChild.setOnClickListener(this);
        tvCommitChild.setOnClickListener(this);

        callRcy();
        //        护理措施
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
     * 点击gridview的多选事件
     *
     * @param position
     * @param names
     * @param checked
     */
    private void clickGridview(int position, String names, boolean checked) {
        switch (position) {
            case 0:
                if (checked) {
                    hlcsS[0] = names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[0] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[0] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[0] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            case 1:
                if (checked) {
                    hlcsS[1] = "#" + names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[1] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[1] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[1] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            case 2:
                if (checked) {
                    hlcsS[2] = "#" + names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[2] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[2] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[2] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            case 3:
                if (checked) {
                    hlcsS[3] = "#" + names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[3] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[3] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[3] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            case 4:
                if (checked) {
                    hlcsS[4] = "#" + names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[4] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[4] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[4] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            case 5:
                if (checked) {
                    hlcsS[5] = "#" + names;
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[5] = "1";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                } else {
                    hlcsS[5] = "";
                    allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
                    csTags[5] = "0";
                    cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
                }
               
                break;
            default:
                break;
        }
    }


    private void callRcy() {
//        adapter_rec.setChildListener(new RcyAdapter.OnChildClickListener() {
//            @Override
//            public void onCheckClick(String s, int po, String pingFenMsg) {
//                String sorce = s.substring(0, 1);
//                for (int i = 0; i <pfxxTags.length ; i++) {
//                    if(i==po){
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
//                        total();
//                        break;
//                    case 4:
//                        sorces[4] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        break;
//                    case 5:
//                        sorces[5] = Integer.parseInt(sorce);
//                        all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
//                        total();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all == 0) {
            tvPingguChild.setText("无痛");
        } else if (all >= 1 && all <= 3) {
            tvPingguChild.setText("轻度疼痛");
        } else if (all >= 4 && all <= 6) {
            tvPingguChild.setText("中度疼痛");
        } else if (all >= 7 && all <= 10) {
            tvPingguChild.setText("重度疼痛");
        }
        String fxjb = tvPingguChild.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);

        tvScoreChild.setText(all + "");
    }
    /**
     * /提醒日期
     *
     * @param fxjb
     */
    private void getCurDate(String fxjb) {
        if (fxjb.equals("无痛")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("轻度疼痛")) {
            String day = dateUtil.afterDay();
            date = day + " " + sfm;
        } else if (fxjb.equals("中度疼痛")) {
            date = dateUtil.get4Hour();
        } else if (fxjb.equals("重度疼痛")) {
            date = dateUtil.get1Hour();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse_child:
                ChildPainActivity.this.finish();
                break;
            case R.id.tv_commit_child:
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
                    } else if (TextUtils.isEmpty(allString)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(ChildPainActivity.this,R.layout.dialog_commit,new int[]{R.id.bt_yes,R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if(view.getId()==R.id.bt_yes){
                                ToastUtils.showLoading(ChildPainActivity.this);
                                netWorkAdd();
                                netWorkTime();}
                            }
                        });
                        centerDialog.show();

                    }
                } else if (tag.equals(Constant.UPDATA)) {
                    if (inCsxx.equals(cuoShimsg) && inPfxx.equals(lastPfMsg)) {
                        Log.e("是否一样", inCsxx + "=" + cuoShimsg + "000" + inPfxx + "=" + lastPfMsg);
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                        return;
                    } else {
                        centerDialog = new CenterDialog(ChildPainActivity.this,R.layout.dialog_commit,new int[]{R.id.bt_yes,R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if(view.getId()==R.id.bt_yes){
                                ToastUtils.showLoading(ChildPainActivity.this);
                                netWorkUpdata();
                                netWorkTime();}
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
        String allscore = tvScoreChild.getText().toString().trim();
        String fenxian = tvPingguChild.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "8" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg+f+itemId;
        //网络参数设置
        Log.e("网络修改参数", canshu);
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
                Log.e("fail", info);
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
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
            Log.e("-----", lastPfMsg);
        }
        String allscore = tvScoreChild.getText().toString().trim();
        String fenxian = tvPingguChild.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "8" + f + "" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg;
        //网络参数设置
        Log.e("网络添加参数", canshu);
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
                Log.e("fail", info);
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
        String canshu = brzyid + f + bqId + f + "疼痛儿童" + f + date;
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
                Log.e("fail", info + "添加提醒失败");
            }
        });
    }
}