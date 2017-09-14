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
import enjoyor.enjoyorzemobilehealth.views.CoustomGridView;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * 疼痛行为(他评)
 */
public class
PainTaPingActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.iv_colse_sever)
    ImageView ivColseSever;
    @BindView(R.id.tv_commit_sever)
    TextView tvCommitSever;
    @BindView(R.id.tv_title_sever)
    TextView tvTitleSever;
    @BindView(R.id.tv_pinggu_sever)
    TextView tvPingguSever;
    @BindView(R.id.tv_score_sever)
    TextView tvScoreSever;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_sever)
    RecyclerView rcSever;
    @BindView(R.id.gv_sever)
    CoustomGridView gvSever;
    private int all = 0;
    private String allString = "";

    private List<CheckBoxBean> listCb;
    private CuoShiMagAdapter cbadapter;
    private String result = Constant.TAPING;
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

    private String lastPfMsg = "";//最后评分信息
    private String[] beanCsxx = null;

    private Intent intent;
    private DateUtil dateUtil;
    private String f;
    private String tag;
    private String updataFen;
    private String inCsxx;
    private String csString;
    private String itemId;
    private String inPfxx;
    private String[] inCsString;
    private int[] sorces = new int[]{0, 0, 0, 0, 0};
    private String[] hlcsS = new String[]{"", "", "", "", "", ""};
    private String[] csTags = new String[]{"0", "0", "0", "0", "0", "0"};
    private String pfxxTags[] = new String[]{"", "", "", "", ""};
    private String sfm;
    private CenterDialog centerDialog;
    private RcyMoreAdapter adapter_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_severe_pain);
        ButterKnife.bind(this);
        init();
        initCanShu();
        initData();
        initListener();

    }

    private void initCanShu() {
        intent = getIntent();
        String title = intent.getStringExtra("title");
        tvTitleSever.setText(title);
        tag = intent.getStringExtra(Constant.TAG);
        if (tag.equals("add")) {
            beanCsxx = new String[endNames.length];
            for (int i = 0; i < endNames.length; i++) {
                beanCsxx[i] = "0";
//                Log.e("datas.length", endNames.length + "");
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

            tvPingguSever.setText(fenxian);
            tvScoreSever.setText(updataFen);
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
        String pf5 = inPfxx.substring(24, inPfxx.length());//3
        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
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
            if (split2[i].equals("1")) {
                sorces[1] = i;
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
        //////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
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

        Log.e("总TAG", pfxxTags[0] + "--" + pfxxTags[1] + "--" + pfxxTags[2] + "--" + pfxxTags[3] + "--" + pfxxTags[4]);
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);
        String title = getIntent().getStringExtra("title");
        tvTitleSever.setText(title);
        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        sfm = date.substring(date.length() - 8, date.length());
    }

    /**
     * 数据
     */
    private void initData() {
        YaChuangActivity da = new YaChuangActivity();
        LinearLayoutManager manager = new LinearLayoutManager(PainTaPingActivity.this);
        rcSever.setLayoutManager(manager);
        adapter_rec = new RcyMoreAdapter(PainTaPingActivity.this, R.layout.gv_item_cbmore, da.json(result), tag, lastPfMsg);
        rcSever.setAdapter(adapter_rec);

        listCb = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listCb.add(new CheckBoxBean(endNames[i], beanCsxx[i], "0"));
        }
        cbadapter = new CuoShiMagAdapter(PainTaPingActivity.this, listCb, tag + "#taping", R.layout.gv_item_cuoshimsg,cuoShimsg);
        gvSever.setAdapter(cbadapter);
    }

    private void initListener() {
        ivColseSever.setOnClickListener(this);
        tvCommitSever.setOnClickListener(this);
        //        护理措施
        cbadapter.setCheckListener(new CuoShiMagAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String name, int position, boolean isCheck) {
                CheckBoxBean bean = listCb.get(position);
                String names = bean.getName();
                clickGridview(position, names, isCheck);
            }
        });
        //评分tag和分值
        adapter_rec.setCheckListener(new RcyMoreAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String pfxxTag, int sorce, int position, String title) {
                switch (title) {
                    case "面部表情":
                        Log.e("position",position+"");
                        if(sorce==0){
                            sorces[0] = 0;
                        }else if(sorce==1){
                            sorces[0] = 1;
                        }else if(sorce==2){
                            sorces[0] =2;
                        }
                        pfxxTags[0] = pfxxTag;
                        break;
                    case "腿":
                        if(sorce==0){
                            sorces[1] = 0;
                        }else if(sorce==1){
                            sorces[1] = 1;
                        }else if(sorce==2){
                            sorces[1] =2;
                        }
                        pfxxTags[1] = pfxxTag;
                        break;
                    case "活动":
                        if(sorce==0){
                            sorces[2] = 0;
                        }else if(sorce==1){
                            sorces[2] = 1;
                        }else if(sorce==2){
                            sorces[2] =2;
                        }
                        pfxxTags[2] = pfxxTag;
                        break;
                    case "哭叫":
                        if(sorce==0){
                            sorces[3] = 0;
                        }else if(sorce==1){
                            sorces[3] = 1;
                        }else if(sorce==2){
                            sorces[3] =2;
                        }

                        pfxxTags[3] = pfxxTag;
                        break;
                    case "安抚":
                        if(sorce==0){
                            sorces[4] = 0;
                        }else if(sorce==1){
                            sorces[4] = 1;
                        }else if(sorce==2){
                            sorces[4] =2;
                        }
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
            default:
                break;
        }
    }

    private void dianjiCuoshiMsg(int position, String names, boolean checked) {
        if (checked) {
            hlcsS[position] = "#" + names;
            allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
            csTags[position] = "1";
            cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
        } else {
            hlcsS[position] = "";
            allString = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5];
            csTags[position] = "0";
            cuoShimsg = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5];
        }
        Log.e("点击措施信息", cuoShimsg + "--" + allString);
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all == 0) {
            tvPingguSever.setText("无痛");
        } else if (all >= 1 && all <= 3) {
            tvPingguSever.setText("轻度疼痛");
        } else if (all >= 4 && all <= 6) {
            tvPingguSever.setText("中度疼痛");
        } else if (all >= 7 && all <= 10) {
            tvPingguSever.setText("重度疼痛");
        }
        String fxjb = tvPingguSever.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);

        tvScoreSever.setText(all + "");
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
            date = dateUtil.get12Hour();
        } else if (fxjb.equals("重度疼痛")) {
            date = dateUtil.get4Hour();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse_sever:
                PainTaPingActivity.this.finish();
                break;
            case R.id.tv_commit_sever:
                if (tag.equals("add")) {
                    String[] split = lastPfMsg.split("#");
                    int num = 0;
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].equals("1")) {
                            num++;
                        }
                    }
                    if (num < 5) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未全部评分");
                        return;
                    } else if (TextUtils.isEmpty(allString)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(PainTaPingActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(PainTaPingActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
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
                        centerDialog = new CenterDialog(PainTaPingActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(PainTaPingActivity.this);
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
     * 添加时间提醒
     */
    private void netWorkTime() {
        //一共4个参数：病人住院ID，病区ID，评估内容，提醒时间
//        示例：20111201¤0404¤压疮¤2017/6/14 15:33:24
        String canshu = brzyid + f + bqId + f + "疼痛重症" + f + date;
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
                Log.e("fail", info + "添加提醒失败");
            }
        });
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
        String allscore = tvScoreSever.getText().toString().trim();
        String fenxian = tvPingguSever.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "6" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg + f + itemId;
        //网络参数设置
        Log.e("修改", canshu);
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
                ToastUtils.dismissLoading();
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
        String allscore = tvScoreSever.getText().toString().trim();
        String fenxian = tvPingguSever.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + allString + f + pingguren + f + curdate + f + "6" + f + "#" + f + sqsh
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShimsg;
        //网络参数设置
        Log.e("保存", canshu);
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
}