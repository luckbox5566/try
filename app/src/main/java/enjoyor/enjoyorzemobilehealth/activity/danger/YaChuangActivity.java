package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;
import enjoyor.enjoyorzemobilehealth.entities.CheckBoxBean;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * 压疮
 */
public class YaChuangActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tv_home_title)
    TextView tvHomeTitle;
    @BindView(R.id.tv_pinggu3)
    TextView tvPinggu3;
    @BindView(R.id.tv_score3)
    TextView tvScore3;
    @BindView(R.id.fen)
    TextView fen;
    @BindView(R.id.rc_pinggu)
    RecyclerView rcPinggu;
    @BindView(R.id.activity_danger_add3)
    LinearLayout activityDangerAdd3;
    @BindView(R.id.gv_cb3)
    GridView gvCb3;
    @BindView(R.id.rb_before)
    RadioButton rbBefore;
    @BindView(R.id.rb_after)
    RadioButton rbAfter;
    @BindView(R.id.rd_gp)
    RadioGroup rdGp;
    @BindView(R.id.rb_happen)
    RadioButton rb_happen;
    @BindView(R.id.iv_colse3)
    ImageView ivColse3;
    @BindView(R.id.tv_commit3)
    TextView tvCommit3;


    private int sorces[] = new int[]{0, 0, 0, 0, 0, 0};
    private int all = 0;
    private String cuoshiMsg = "";//措施内容
    private List<CheckBoxBean> listCb;
    private CuoShiMagAdapter cbadapter;
    private RcyMoreAdapter adapter_rec;
    private String[] endNames = new String[]{"A.制定翻身计划", "B.协助活动", "C.气垫床", "D.三角海绵垫", "E.枕垫", "F.抬高足跟",
            "G.皮肤护理用品", "H.避免皮肤过度干燥", "I.维持足够的水分", "G.床上使用尿便盆", "K.及时更换衣服",
            "L.营养科会诊", "M.增加蛋白质摄入", "N.增加热量摄入", "O.补充多种维生素", "P.增加饮食指导", "Q.床头抬高小于30",
            "R.使用过床单移动", "S.保护足跟/肘部", "T.保护尾部/后枕部", "U.两人协同翻身", "V.其他"};
    private String www = Constant.YACHUANG;
    private String brzyid;
    private ZhierCall zhierCall;
    private String uesrid;
    private String date;
    private String rydate;
    private String name;
    private String brid;
    private String sex;
    private String age;
    private String bqmc;
    private String pingguren;
    private String bqId;
    private String yachuang = "0", shuqian = "1", shuhou = "0";
    private String sqsh = "0" + "#" + "1" + "#" + "0";//术前后 默认
    private String cuoShitag = "";//措施标识
    private String lastPfMsg = "";//最后评分信息
    /**
     * 修改
     */
    private Intent intent;
    private String[] beanSqsh = null;
    private String[] beanCsxx = null;
    private String beanPfxx = null;
    private String tag;//添加和修改标识
    private String itemId;//修改item的ID
    private String inCsxx;//护理措施标识
    private String inPfxx;
    private String inShoushu;
    private String[] inCsString;
    private String[] hlcsS = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private String[] csTags = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String f;
    private DateUtil dateUtil;
    private MyApplication instance;
    private String[] pfxxTags = new String[]{"", "", "", "", "", ""};
    private CenterDialog centerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_add3);
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
            beanCsxx = new String[endNames.length];
            for (int i = 0; i < endNames.length; i++) {
                beanCsxx[i] = "0";
            }
        } else if (tag.equals("updata")) {
            AllWXYS bean = (AllWXYS) intent.getSerializableExtra("bean");
            String fenxian = bean.getJB();

            String fen = bean.getZPF();
            inShoushu = bean.getSqsh();
            inCsxx = bean.getCSXX();
            inPfxx = bean.getFZxx();
            String csString = bean.getHLCS();
            itemId = bean.getID();

            cuoShitag = inCsxx;
            lastPfMsg = inPfxx;
            cuoshiMsg = csString;
            sqsh = inShoushu;
            Log.e("信息", inShoushu + "--" + inCsxx + "--" + inPfxx + "--" + itemId);

            beanSqsh = inShoushu.split("#");
            if (beanSqsh[0].equals("1")) {
                rb_happen.setChecked(true);
            }
            if (beanSqsh[1].equals("1")) {
                rbBefore.setChecked(true);
                rbAfter.setChecked(false);
                rbAfter.setEnabled(false);
                rb_happen.setEnabled(false);
            }
            if (beanSqsh[2].equals("1")) {
                rbAfter.setChecked(true);
                rbBefore.setChecked(false);
                rbBefore.setEnabled(false);
                rb_happen.setEnabled(false);
            }

            initPfxx();   //评分信息处理
            beanCsxx = inCsxx.split("#");//措施标识
            inCsString = csString.split("#");//措施内容
            Log.e("措施内容長度", inCsString.length + "  " + hlcsS.length + "  " + csTags.length);
            for (int i = 0; i < endNames.length; i++) {
                if (beanCsxx[i].equals("1")) {
                    hlcsS[i] = "#" + endNames[i];
                    csTags[i] = "1";
                } else {
                    hlcsS[i] = "";
                    csTags[i] = "0";
                }
//                Log.e("措施标识0", hlcsS[i] + csTags[i]);
            }
            beanCsxx = inCsxx.split("#");//标识

            beanPfxx = inPfxx;

            tvPinggu3.setText(fenxian);
            tvScore3.setText(fen);

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
        String pf1 = inPfxx.substring(0, 7);
        String pf2 = inPfxx.substring(8, 15);
        String pf3 = inPfxx.substring(16, 23);
        String pf4 = inPfxx.substring(24, 31);
        String pf5 = inPfxx.substring(32, 39);
        String pf6 = inPfxx.substring(40, inPfxx.length());
        //////1
        String[] split1 = pf1.split("#");
        String[] pfxxTag1 = new String[split1.length];
        for (int i = 0; i < split1.length; i++) {
            pf1.split("#");
            if (split1[i].equals("1")) {
                sorces[0] = i + 1;
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
        Log.e("fen", s1);
        /////////2
        String[] split2 = pf2.split("#");
        String[] pfxxTag2 = new String[split2.length];
        for (int i = 0; i < split2.length; i++) {
            pf2.split("#");
            if (split2[i].equals("1")) {
                sorces[1] = i + 1;
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
        Log.e("fen", s2);
        /////////3
        String[] split3 = pf3.split("#");
        String[] pfxxTag3 = new String[split3.length];
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                sorces[2] = i + 1;
                pfxxTag3[i] = "#1";
                Log.e("2", i + "--" + sorces[2]);
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
                sorces[3] = i + 1;
                pfxxTag4[i] = "#1";
                Log.e("2", i + "--" + sorces[4]);
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
                sorces[4] = i + 1;
                pfxxTag5[i] = "#1";
                Log.e("2", i + "");
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
        /////////6
        String[] split6 = pf6.split("#");
        String[] pfxxTag6 = new String[split6.length];
        for (int i = 0; i < split6.length; i++) {
            if (split6[i].equals("1")) {
                sorces[5] = i + 1;
                pfxxTag6[i] = "#1";
                Log.e("2", i + "");
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

    private void initData() {
        List<CbMoreBean> list = json(www);
        LinearLayoutManager manager = new LinearLayoutManager(YaChuangActivity.this);
        rcPinggu.setLayoutManager(manager);
        adapter_rec = new RcyMoreAdapter(YaChuangActivity.this, R.layout.gv_item_cbmore, list, tag, inPfxx);
        rcPinggu.setAdapter(adapter_rec);

        listCb = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listCb.add(new CheckBoxBean(endNames[i], beanCsxx[i], "0"));
        }
        cbadapter = new CuoShiMagAdapter(YaChuangActivity.this, listCb, tag + "#yachuang", R.layout.gv_item_cuoshimsg, cuoShitag);
        gvCb3.setAdapter(cbadapter);
    }

    private void initListener() {
        ivColse3.setOnClickListener(this);
        tvCommit3.setOnClickListener(this);
        rb_happen.setOnClickListener(this);//是否压疮
        if (tag.equals(Constant.ADD)) {
            rbBefore.setChecked(true);//默认
        }
        rdGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbBefore.getId()) {

                    rbBefore.setChecked(true);
                    rbAfter.setChecked(false);
                    shuqian = "1";
                    shuhou = "0";
                    sqsh = yachuang + "#" + shuqian + "#" + shuhou;
                    initShouShu();

                } else if (checkedId == rbAfter.getId()) {
                    rbBefore.setChecked(false);
                    rbAfter.setChecked(true);
                    shuqian = "0";
                    shuhou = "1";
                    sqsh = yachuang + "#" + shuqian + "#" + shuhou;
                    initShouShu();
                }
                callRcy();
                hlcsS[0] = "";hlcsS[1] = "";hlcsS[2] = "";hlcsS[3] = "";hlcsS[4] = "";hlcsS[5] = "";hlcsS[6] = "";hlcsS[7] = "";hlcsS[8] = "";hlcsS[9] = "";hlcsS[10] = "";hlcsS[11] = "";hlcsS[12] = "";hlcsS[13] = "";hlcsS[14] = "";hlcsS[15] = "";hlcsS[16] = "";hlcsS[17] = "";hlcsS[18] = "";hlcsS[19] = "";hlcsS[20] = "";
                pfxxTags[0] = "";pfxxTags[1] = "";pfxxTags[2] = "";pfxxTags[3] = "";pfxxTags[4] = "";pfxxTags[5] = "";
                cuoShitag = "";
                lastPfMsg = "";
            }
        });
        callRcy();

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
        hlcsS[5] = "";
        hlcsS[6] = "";
        hlcsS[7] = "";
        hlcsS[8] = "";
        hlcsS[9] = "";
        hlcsS[10] = "";
        hlcsS[11] = "";
        hlcsS[12] = "";
        hlcsS[13] = "";
        hlcsS[14] = "";
        hlcsS[15] = "";
        hlcsS[16] = "";
        hlcsS[17] = "";
        hlcsS[18] = "";
        hlcsS[19] = "";
        hlcsS[20] = "";

    }

    private void callRcy() {
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
                    case "感觉":
                        sorces[0] = sorce;
                        pfxxTags[0] = pfxxTag;
                        break;
                    case "潮湿":
                        sorces[1] = sorce;
                        pfxxTags[1] = pfxxTag;
                        break;
                    case "活动能力":
                        sorces[2] = sorce;
                        pfxxTags[2] = pfxxTag;
                        break;
                    case "移动能力":
                        sorces[3] = sorce;
                        pfxxTags[3] = pfxxTag;
                        break;
                    case "营养情况":
                        sorces[4] = sorce;
                        pfxxTags[4] = pfxxTag;
                        break;
                    case "摩擦/剪力":
                        sorces[5] = sorce;
                        pfxxTags[5] = pfxxTag;
                        break;
                    default:
                        break;
                }
                lastPfMsg = pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + pfxxTags[5];
                all = sorces[0] + sorces[1] + sorces[2] + sorces[3] + sorces[4] + sorces[5];
                total();
                Log.e("====", sorces[0] + "--" + sorces[1] + "--" + sorces[2] + "--" + sorces[3] + "--" + sorces[4] + "--" + sorces[5]);
                Log.e("pfxxTags", lastPfMsg + "  " + pfxxTags[0] + pfxxTags[1] + pfxxTags[2] + pfxxTags[3] + pfxxTags[4] + pfxxTags[5] + all + "   ");
            }
        });
    }

    /**
     * 点击gridview的多选事件
     *
     * @param position
     * @param names
     * @param msg
     */
    private void clickGridview(int position, String names, boolean msg) {
        switch (position) {
            case 0:
                dianjiCuoshiMsg(0, names, msg);
                break;
            case 1:
                dianjiCuoshiMsg(1, names, msg);
                break;
            case 2:
                dianjiCuoshiMsg(2, names, msg);
                break;
            case 3:
                dianjiCuoshiMsg(3, names, msg);
                break;
            case 4:
                dianjiCuoshiMsg(4, names, msg);
                break;
            case 5:
                dianjiCuoshiMsg(5, names, msg);
                break;
            case 6:
                dianjiCuoshiMsg(6, names, msg);
                break;
            case 7:
                dianjiCuoshiMsg(7, names, msg);
                break;
            case 8:
                dianjiCuoshiMsg(8, names, msg);
                break;
            case 9:
                dianjiCuoshiMsg(9, names, msg);
                break;
            case 10:
                dianjiCuoshiMsg(10, names, msg);
                break;
            case 11:
                dianjiCuoshiMsg(11, names, msg);
                break;
            case 12:
                dianjiCuoshiMsg(12, names, msg);
                break;
            case 13:
                dianjiCuoshiMsg(13, names, msg);
                break;
            case 14:
                dianjiCuoshiMsg(14, names, msg);
                break;
            case 15:
                dianjiCuoshiMsg(15, names, msg);
                break;
            case 16:
                dianjiCuoshiMsg(16, names, msg);
                break;
            case 17:
                dianjiCuoshiMsg(17, names, msg);
                break;
            case 18:
                dianjiCuoshiMsg(18, names, msg);
                break;
            case 19:
                dianjiCuoshiMsg(19, names, msg);
                break;
            case 20:
                dianjiCuoshiMsg(20, names, msg);
                break;
            case 21:
                dianjiCuoshiMsg(21, names, msg);
                break;
            default:
                break;
        }
    }

    private void dianjiCuoshiMsg(int position, String names, boolean msg) {
        if (msg) {
            hlcsS[position] = "#" + names;
            cuoshiMsg = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9] + hlcsS[10] + hlcsS[11] + hlcsS[12] + hlcsS[13] + hlcsS[14] + hlcsS[15] + hlcsS[16] + hlcsS[17] + hlcsS[18] + hlcsS[19] + hlcsS[20] + hlcsS[21];
            csTags[position] = "1";
            cuoShitag = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9] + "#" + csTags[10] + "#" + csTags[11] + "#" + csTags[12] + "#" + csTags[13] + "#" + csTags[14] + "#" + csTags[15] + "#" + csTags[16] + "#" + csTags[17] + "#" + csTags[18] + "#" + csTags[19] + "#" + csTags[20] + "#" + csTags[21];
        } else {
            hlcsS[position] = "";
            cuoshiMsg = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9] + hlcsS[10] + hlcsS[11] + hlcsS[12] + hlcsS[13] + hlcsS[14] + hlcsS[15] + hlcsS[16] + hlcsS[17] + hlcsS[18] + hlcsS[19] + hlcsS[20] + hlcsS[21];
            csTags[position] = "0";
            cuoShitag = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9] + "#" + csTags[10] + "#" + csTags[11] + "#" + csTags[12] + "#" + csTags[13] + "#" + csTags[14] + "#" + csTags[15] + "#" + csTags[16] + "#" + csTags[17] + "#" + csTags[18] + "#" + csTags[19] + "#" + csTags[20] + "#" + csTags[21];
        }
        Log.e("点击措施信息", cuoshiMsg + "--" + cuoShitag);
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all > 18) {
            tvPinggu3.setText("无风险");
        } else if (all >= 15 && all <= 18) {
            tvPinggu3.setText("低度风险");
        } else if (all >= 13 && all <= 14) {
            tvPinggu3.setText("中度风险");
        } else if (all >= 10 && all <= 12) {
            tvPinggu3.setText("重度风险");
        } else if (all <= 9) {
            tvPinggu3.setText("极度风险");
        }

        String fxjb = tvPinggu3.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);

        tvScore3.setText(all + "");
    }

    private void getCurDate(String fxjb) {
        String sfm = date.substring(date.length() - 8, date.length());
        if (fxjb.equals("无风险")) {
            String afterWeek = dateUtil.after14D();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("低度风险")) {
            String afterWeek = dateUtil.after14D();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("中度风险")) {
            String day = dateUtil.afterWeek();
            date = day + " " + sfm;
        } else if (fxjb.equals("重度风险")) {
            String day = dateUtil.after3Day();
            date = day + " " + sfm;
        } else if (fxjb.equals("极度风险")) {
            String day = dateUtil.afterDay();
            date = day + " " + sfm;
        }
    }


    public List<CbMoreBean> json(String result) {
        List<CbMoreBean> lists = new ArrayList<>();
        CbMoreBean cmb = null;
        try {
            JSONObject data = new JSONObject(result);
            String s = data.optString("result");
            String tag = data.optString("tag");
            String type = data.optString("type");
            JSONArray list = new JSONArray(s);
            for (int i = 0; i < list.length(); i++) {
                JSONObject value = list.getJSONObject(i);
                String title = value.getString("title");

                String moreBean = value.getString("bean");
                JSONArray array = new JSONArray(moreBean);
                CbMoreBean.Bean bean = null;
                List<CbMoreBean.Bean> list1 = new ArrayList<>();
                for (int j = 0; j < array.length(); j++) {
                    JSONObject item = array.getJSONObject(j);
                    String name = item.has("name") ? item.getString("name") : "";//标题
                    String sorce = item.has("sorce") ? item.getString("sorce") : "";//
                    bean = new CbMoreBean.Bean();
                    bean.setName(name);
                    bean.setSorce(sorce);
                    bean.setMsg("0");
                    Log.e("0000", title + name + sorce);
                    list1.add(bean);
                }
                cmb = new CbMoreBean();
                cmb.setTitle(title);
                cmb.setTag(tag);
                cmb.setType(type);
                cmb.setCbMoreBean(list1);
                lists.add(cmb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse3:
                YaChuangActivity.this.finish();
                break;
            case R.id.rb_happen:
                if (rb_happen.isChecked()) {
                    yachuang = "1";
                } else {
                    yachuang = "0";
                }
                sqsh = yachuang + "#" + shuqian + "#" + shuhou;
                break;
            case R.id.tv_commit3:
                if (tag.equals(Constant.ADD)) {
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
                    } else if (TextUtils.isEmpty(cuoshiMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(YaChuangActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});

                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(YaChuangActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();
                    }
                } else if (tag.equals(Constant.UPDATA)) {
                    if (inCsxx.equals(cuoShitag) && inPfxx.equals(lastPfMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                    } else {
                        centerDialog = new CenterDialog(YaChuangActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(YaChuangActivity.this);
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
     * 修改
     */
    private void netWorkUpdata() {
        if (cuoshiMsg.startsWith("#")) {
            cuoshiMsg = cuoshiMsg.substring(1, cuoshiMsg.length());
            Log.e("!!!!!", cuoshiMsg);
        }

        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
            Log.e("-----", lastPfMsg);
        }
        String allscore = tvScore3.getText().toString().trim();
        String fenxian = tvPinggu3.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String zhpfxx = lastPfMsg.replace("##", "#");
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "1" + f + "" + f + inShoushu + f + rydate + f + name + f + brid
                + f + sex + f + age + f + bqId + f + bqmc + f + zhpfxx + f + cuoShitag + f + itemId;
//        网络参数设置
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
                SaveUtils.put(YaChuangActivity.this, Constant.IS_COMMIT, "yachuang");
                ToastUtils.makeToast(MyApplication.getContext(), "修改成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "修改失败");
            }
        });
    }

    /**
     * 添加网络传参
     */
    private void netWorkAdd() {

        if (cuoshiMsg.startsWith("#")) {
            cuoshiMsg = cuoshiMsg.substring(1, cuoshiMsg.length());
            Log.e("!!!!!", cuoshiMsg);
        }
        if (lastPfMsg.startsWith("#")) {
            lastPfMsg = lastPfMsg.substring(1, lastPfMsg.length());
        }
        String allscore = tvScore3.getText().toString().trim();
        String fenxian = tvPinggu3.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "1" + f + "" + f + sqsh + f + rydate + f + name + f + brid
                + f + sex + f + age + f + bqId + f + bqmc + f + lastPfMsg + f + cuoShitag;
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
                ToastUtils.dismissLoading();
                SaveUtils.put(YaChuangActivity.this, Constant.IS_COMMIT, "yachuang");
                ToastUtils.makeToast(MyApplication.getContext(), "保存成功");
            }

            @Override
            public void fail(String info) {
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
        String canshu = brzyid + f + bqId + f + "压疮" + f + date;
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
