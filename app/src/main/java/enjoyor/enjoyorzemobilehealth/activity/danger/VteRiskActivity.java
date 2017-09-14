package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.CuoShiMagAdapter;
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
import enjoyor.enjoyorzemobilehealth.views.CoustomGridView;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * 静脉血栓栓塞症风险评估
 */
public class VteRiskActivity extends AppCompatActivity {
    @BindView(R.id.tv_commit4)
    TextView tvCommit4;
    @BindView(R.id.tv_home_title)
    TextView tvHomeTitle;
    @BindView(R.id.tv_score4)
    TextView tvScore4;
    @BindView(R.id.fen)
    TextView fen;

    @BindView(R.id.gv_end)
    CoustomGridView gvEnd;
    @BindView(R.id.activity_danger_add4)

    LinearLayout activityDangerAdd4;
    @BindView(R.id.iv_colse4)
    ImageView ivColse4;
    @BindView(R.id.tv_jing_fenxian)
    TextView tvJingFenxian;
    @BindView(R.id.rcy_5)
    RecyclerView rcy5;
    @BindView(R.id.rcy_1)
    RecyclerView rcy1;
    @BindView(R.id.rcy_2)
    RecyclerView rcy2;
    @BindView(R.id.rcy_3)
    RecyclerView rcy3;
    private String result = Constant.JINGMOXUESHUAN;
    private String[] endNames = new String[]{"A.警示标识", "B.环境舒适", "C.基础预防", "D.物理预防", "E.药物预防", "F.基本宣教", "G.饮食指导",
            "H.活动指导", "I.用药指导", "J.心理指导", "K.出院指导"
    };

    private CuoShiMagAdapter endAdapter;
    private List<CheckBoxBean> listEnd;
    private int all = 0;

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
    private String cuoshiMsg = "";//措施信息内容;
    private String cuoShitag = "";//措施信息tag
    private String[] beanCsxx = null;
    private Intent intent;
    private String tag;
    private String updataFen;
    private String inCsxx;
    private String csString;
    private String itemId;
    private String inPfxx;
    private String[] inCsString;
    private String f;
    private DateUtil dateUtil;
    private String[] pfxxTags = new String[]{"0#0#0#0#0#0", "0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0", "0#0#0#0#0#0#0", "0#0#0#0#0#0#0#0#0#0"};
    private int[] sorces1 = new int[]{0, 0, 0, 0, 0, 0};//5分
    private int[] sorces2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};//1分
    private int[] sorces3 = new int[]{0, 0, 0, 0, 0, 0, 0};//2分
    private int[] sorces4 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};//3分
    private String[] hlcsS = new String[]{"", "", "", "", "", "", "", "", "", "", ""};
    private String[] csTags = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String sfm;
    private CenterDialog centerDialog;

    private boolean isFive;//是否选择5分
    private boolean[] state5 = {false, false, false, false, false, false};
    private boolean[] state1 = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private boolean[] state2 = {false, false, false, false, false, false, false};
    private boolean[] state3 = {false, false, false, false, false, false, false, false, false, false};
    private String pingFenMsg0 = "", pingFenMsg1 = "#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0", pingFenMsg2 = "#0#0#0#0#0#0#0", pingFenMsg3 = "#0#0#0#0#0#0#0#0#0#0";
    private String allPingFenMsg = "";
    private String[] pf;
    private int fen1 = 0, fen2 = 0, fen3 = 0, fen4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_add4);
        ButterKnife.bind(this);
        init();
        initCanShu();
        initData();
        initListener();
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
            cuoShitag = inCsxx;
            allPingFenMsg = inPfxx;
            cuoshiMsg = csString;
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
            tvJingFenxian.setText(fenxian);
            tvScore4.setText(updataFen);
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

    /**
     * 初始化评分信息
     */
    private void initPfxx() {

        //6
        String pf1 = inPfxx.substring(0, 11);
        pingFenMsg0="#"+pf1;
        //17
        String pf2 = inPfxx.substring(12, 45);
        pingFenMsg1="#"+pf2;
        //7
        String pf3 = inPfxx.substring(46, 59);
        pingFenMsg2="#"+pf3;
        //10
        String pf4 = inPfxx.substring(60, inPfxx.length());
        pingFenMsg3="#"+pf4;
        Log.e("initPfxx",pingFenMsg0+"  "+pingFenMsg1+"  "+pingFenMsg2+"  "+pingFenMsg3);

        pf = new String[]{pf1, pf2, pf3, pf4};
        pfxxTags[0] = pf1;
        pfxxTags[1] = pf2;
        pfxxTags[2] = pf3;
        pfxxTags[3] = pf4;

        if (pf1.contains("1")) {
            isFive = true;
        }
        //////1
        String[] split1 = pf1.split("#");
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("1")) {
                sorces1[i] = 5;
                state5[i]=false;

            }
        }
        /////////2
        String[] split2 = pf2.split("#");
        for (int i = 0; i < split2.length; i++) {
            if (split2[i].equals("1")) {
                sorces2[i] = 1;
            }
        }
        /////////3
        String[] split3 = pf3.split("#");
        for (int i = 0; i < split3.length; i++) {
            if (split3[i].equals("1")) {
                sorces3[i] = 2;
            }
        }
        /////////4
        String[] split4 = pf4.split("#");
        for (int i = 0; i < split4.length; i++) {
            if (split4[i].equals("1")) {
                sorces4[i] = 3;
            }
        }
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.hui), 0);
        f = NetWork.SEPARATE;
        dateUtil = DateUtil.getInstance();
        date = dateUtil.getDate();
        sfm = date.substring(date.length() - 8, date.length());

        for (int i = 0; i < state5.length; i++) {
            state5[i] = true;
        }
        for (int i = 0; i < state1.length; i++) {
            state1[i] = true;
        }
        for (int i = 0; i < state2.length; i++) {
            state2[i] = true;
        }
        for (int i = 0; i < state3.length; i++) {
            state3[i] = true;
        }

    }

    private void initData() {
        YaChuangActivity da = new YaChuangActivity();
        rcy5.setLayoutManager(new LinearLayoutManager(this));
        rcy1.setLayoutManager(new LinearLayoutManager(this));
        rcy2.setLayoutManager(new LinearLayoutManager(this));
        rcy3.setLayoutManager(new LinearLayoutManager(this));
        List<CbMoreBean> data = da.json(result);
        rcyAdapter(data);


        listEnd = new ArrayList<>();
        for (int i = 0; i < endNames.length; i++) {
            listEnd.add(new CheckBoxBean(endNames[i], beanCsxx[i], "0"));
        }

        endAdapter = new CuoShiMagAdapter(VteRiskActivity.this, listEnd, tag + "#vte", R.layout.gv_item_cuoshimsg, cuoShitag);
        gvEnd.setAdapter(endAdapter);
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void rcyAdapter(final List<CbMoreBean> data) {
        final List<CbMoreBean.Bean> list = data.get(0).getCbMoreBean();
        showPfxx(list, 0);

        rcy5.setAdapter(new CommonAdapter<CbMoreBean.Bean>(VteRiskActivity.this, R.layout.lv_item_checkbox, list) {
            @Override
            protected void convert(ViewHolder holder, final CbMoreBean.Bean bean, final int position) {
                final RelativeLayout rl_vte = getRelativeLayout(holder, bean, list);
                rl_vte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        zaiciDianji(bean, state5[position], position);
                        state5[position] = !state5[position];

                        pingFenMsg0 = "";
                        for (int i = 0; i < list.size(); i++) {
                            pingFenMsg0 += "#" + list.get(i).getMsg();
                        }
                        notifyDataSetChanged();

                        setFenzhi();//设置分值和评分信息参数
                        Log.e("rcy5", pingFenMsg0);
                        Log.e("rcy5all", allPingFenMsg);
                    }
                });
            }
        });
        final List<CbMoreBean.Bean> list1 = data.get(1).getCbMoreBean();
        showPfxx(list1, 1);
//        for (int j = 0; j < list1.size(); j++) {
//            String[] split = pf[1].split("#");
//            if (split[j].equals("1")) {
//                list1.get(j).setMsg("1");
//            }
//        }
        rcy1.setAdapter(new CommonAdapter<CbMoreBean.Bean>(VteRiskActivity.this, R.layout.lv_item_checkbox, list1) {
            @Override
            protected void convert(ViewHolder holder, final CbMoreBean.Bean bean, final int position) {
                final RelativeLayout rl_vte = getRelativeLayout(holder, bean, list1);
                rl_vte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFive) {
                            zaiciDianjiOther(bean, state1[position], position, "1");
                            state1[position] = !state1[position];
                        }
                        pingFenMsg1 = "";
                        for (int i = 0; i < list1.size(); i++) {
                            pingFenMsg1 += "#" + list1.get(i).getMsg();
                        }
                        notifyDataSetChanged();

                        setFenzhi();//设置分值和评分信息参数
                        Log.e("rcy5", pingFenMsg1);
                        Log.e("rcy5all", allPingFenMsg);
                    }
                });
            }
        });
        final List<CbMoreBean.Bean> list2 = data.get(2).getCbMoreBean();
        showPfxx(list2, 2);
//        for (int j = 0; j < list2.size(); j++) {
//            String[] split = pf[2].split("#");
//            if (split[j].equals("1")) {
//                list2.get(j).setMsg("1");
//            }
//        }
        rcy2.setAdapter(new CommonAdapter<CbMoreBean.Bean>(VteRiskActivity.this, R.layout.lv_item_checkbox, list2) {
            @Override
            protected void convert(ViewHolder holder, final CbMoreBean.Bean bean, final int position) {
                final RelativeLayout rl_vte = getRelativeLayout(holder, bean, list2);
                rl_vte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFive) {
                            zaiciDianjiOther(bean, state2[position], position, "2");
                            state2[position] = !state2[position];
                        }
                        pingFenMsg2 = "";
                        for (int i = 0; i < list2.size(); i++) {
                            pingFenMsg2 += "#" + list2.get(i).getMsg();
                        }
                        notifyDataSetChanged();

                        setFenzhi();//设置分值和评分信息参数
                        Log.e("rcy2", pingFenMsg2);
                        Log.e("rcy2all", allPingFenMsg);
                    }
                });

            }
        });
        final List<CbMoreBean.Bean> list3 = data.get(3).getCbMoreBean();
        showPfxx(list3, 3);
        rcy3.setAdapter(new CommonAdapter<CbMoreBean.Bean>(VteRiskActivity.this, R.layout.lv_item_checkbox, list3) {
            @Override
            protected void convert(ViewHolder holder, final CbMoreBean.Bean bean, final int position) {
                final RelativeLayout rl_vte = getRelativeLayout(holder, bean, list3);
                rl_vte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isFive) {
                            zaiciDianjiOther(bean, state3[position], position, "3");
                            state3[position] = !state3[position];
                        }
                        pingFenMsg3 = "";
                        for (int i = 0; i < list3.size(); i++) {
                            pingFenMsg3 += "#" + list3.get(i).getMsg();
                        }
                        notifyDataSetChanged();

                        setFenzhi();//设置分值和评分信息参数
                        Log.e("rcy3", pingFenMsg3);
                        Log.e("rcy3all", allPingFenMsg);
                    }
                });
            }
        });
    }

    //展示评分信息
    private void showPfxx(List<CbMoreBean.Bean> list, int position) {
        if (tag.equals(Constant.UPDATA)) {
            for (int j = 0; j < list.size(); j++) {
                String[] split = pf[position].split("#");
                if (split[j].equals("1")) {
                    list.get(j).setMsg("1");
                }
            }
        }
    }

    //    设置分值和评分信息参数
    private void setFenzhi() {
        allPingFenMsg = pingFenMsg0 + pingFenMsg1 + pingFenMsg2 + pingFenMsg3;
        fen1 = sorces1[0] + sorces1[1] + sorces1[2] + sorces1[3] + sorces1[4] + sorces1[5];

        fen2 = sorces2[0] + sorces2[1] + sorces2[2] + sorces2[3] + sorces2[4] + sorces2[5] + sorces2[6] + sorces2[7] + sorces2[8] + sorces2[9] + sorces2[10] + sorces2[11] + sorces2[12] + sorces2[13] + sorces2[14] + sorces2[15] + sorces2[16];

        fen3 = sorces3[0] + sorces3[1] + sorces3[2] + sorces3[3] + sorces3[4] + sorces3[5] + sorces3[6];

        fen4 = sorces4[0] + sorces4[1] + sorces4[2] + sorces4[3] + sorces4[4] + sorces4[5] + sorces4[6] + sorces4[7] + sorces4[8] + sorces4[9];


        all = fen1 + fen2 + fen3 + fen4;
        total();
        Log.e("分1", sorces1[0] + "  " + sorces1[1] + "  " + sorces1[2] + "  " + sorces1[3] + "  " + sorces1[4] + "  " + sorces1[5]);
        Log.e("分2", sorces2[0] + "  " + sorces2[1] + "  " + sorces2[2] + "  " + sorces2[3] + "  " + sorces2[4] + "  " + sorces2[5] + "  " + "  " + sorces2[6] + "  " + sorces2[7] + "  " + sorces2[8] + "  " + sorces2[9] + "  " + sorces2[10] + "  " + "  " + sorces2[11] + "  " + "  " + sorces2[12] + "  " + sorces2[13] + "  " + sorces2[14] + "  " + sorces2[15] + "  " + sorces2[16] + "  ");
        Log.e("分3", sorces3[0] + "  " + sorces3[1] + "  " + sorces3[2] + "  " + sorces3[3] + "  " + sorces3[4] + "  " + sorces3[5] + "  " + sorces3[6]);
        Log.e("分4", sorces4[0] + "  " + sorces4[1] + "  " + sorces4[2] + "  " + sorces4[3] + "  " + sorces4[4] + "  " + sorces4[5] + "  " + sorces4[6] + "  " + sorces4[7] + "  " + sorces4[8] + "  " + sorces4[9]);
    }

    private RelativeLayout getRelativeLayout(ViewHolder holder, CbMoreBean.Bean bean, List<CbMoreBean.Bean> list) {
        holder.setText(R.id.gv_name, bean.getName());
        holder.setText(R.id.gv_sorce, bean.getSorce());
        final ImageView image = holder.getView(R.id.iv_choose);
        final TextView gv_sorce = holder.getView(R.id.gv_sorce);
        final RelativeLayout rl_vte = holder.getView(R.id.rl_vte);

        if (bean.isChecked()) {//状态选中
            image.setImageResource(R.drawable.btn_choose_on);
            gv_sorce.setTextColor(Color.parseColor("#3f90eb"));
        } else {
            image.setImageResource(R.drawable.btn_choose_null);
            gv_sorce.setTextColor(Color.parseColor("#888888"));
        }
        //展示评分信息
        for (int i = 0; i < list.size(); i++) {
            if (bean.getMsg().equals("1")) {
                image.setImageResource(R.drawable.btn_choose_on);
                gv_sorce.setTextColor(Color.parseColor("#3f90eb"));
            }
        }
        return rl_vte;
    }

    private void zaiciDianjiOther(CbMoreBean.Bean bean, boolean state, int position, String tag) {
        if (state) {
            bean.setChecked(true);//点击的设为选中
            bean.setMsg("1");
            if (tag.equals("1")) {
                sorces2[position] = 1;
            } else if (tag.equals("2")) {
                sorces3[position] = 2;
            } else if (tag.equals("3")) {
                sorces4[position] = 3;
            }
        } else {
            bean.setChecked(false);//再次点击取消
            bean.setMsg("0");
            if (tag.equals("1")) {
                sorces2[position] = 0;
            } else if (tag.equals("2")) {
                sorces3[position] = 0;
            } else if (tag.equals("3")) {
                sorces3[position] = 0;
            }
        }
    }

    /**
     * 点击五分
     *
     * @param bean
     * @param state
     * @param position
     */
    private void zaiciDianji(CbMoreBean.Bean bean, boolean state, int position) {
        if (state) {
            bean.setChecked(true);//点击的设为选中
            bean.setMsg("1");
            isFive = true;
            sorces1[position] = 5;
            showDialog();
        } else {
            bean.setChecked(false);//再次点击取消
            bean.setMsg("0");
            isFive = false;
            sorces1[position] = 0;
        }
    }

    private void initListener() {
        //        护理措施
        endAdapter.setCheckListener(new CuoShiMagAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(String name, int position, boolean isCheck) {
                CheckBoxBean bean = listEnd.get(position);
                String names = bean.getName();
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
            case 10:
                dianjiCuoshiMsg(10, names, checked);
                break;
            default:
                break;
        }
    }

    private void dianjiCuoshiMsg(int position, String names, boolean checked) {
        if (checked) {
            hlcsS[position] = "#" + names;
            cuoshiMsg = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9] + hlcsS[10];
            csTags[position] = "1";
            cuoShitag = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9] + "#" + csTags[10];
        } else {
            hlcsS[position] = "";
            cuoshiMsg = hlcsS[0] + hlcsS[1] + hlcsS[2] + hlcsS[3] + hlcsS[4] + hlcsS[5] + hlcsS[6] + hlcsS[7] + hlcsS[8] + hlcsS[9] + hlcsS[10];
            csTags[position] = "0";
            cuoShitag = csTags[0] + "#" + csTags[1] + "#" + csTags[2] + "#" + csTags[3] + "#" + csTags[4] + "#" + csTags[5] + "#" + csTags[6] + "#" + csTags[7] + "#" + csTags[8] + "#" + csTags[9] + "#" + csTags[10];
        }
        Log.e("点击措施信息", cuoshiMsg + "--" + cuoShitag);
    }

    private void showDialog() {
        centerDialog = new CenterDialog(VteRiskActivity.this, R.layout.dialog_vte, new int[]{R.id.bt_yes, R.id.bt_no});
        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
            @Override
            public void OnCenterItemClick(CenterDialog dialog, View view) {
                if (view.getId() == R.id.bt_yes) {
                    if (TextUtils.isEmpty(cuoshiMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    }
                    netWorkAdd();
                    netWorkTime();
                }
            }
        });
        centerDialog.show();
    }

    /**
     * 计算风险程度
     */
    private void total() {
        if (all == 0 || all == 1) {
            tvJingFenxian.setText("低危");
        } else if (all == 2) {
            tvJingFenxian.setText("中危");
        } else if (all >= 3 && all <= 4) {
            tvJingFenxian.setText("高危");
        } else if (all >= 5) {
            tvJingFenxian.setText("极高危");
        }
        String fxjb = tvJingFenxian.getText().toString().trim();
        getCurDate(fxjb);//提醒日期
        Log.e("提醒日期", date);
        tvScore4.setText(all + "");
    }

    /**
     * 日期提醒
     *
     * @param fxjb
     */
    private void getCurDate(String fxjb) {
        if (fxjb.equals("低危")) {
            String afterWeek = dateUtil.afterWeek();
            date = afterWeek + " " + sfm;
        } else if (fxjb.equals("中危")) {
            String day = dateUtil.after3Day();
            date = day + " " + sfm;
        } else if (fxjb.equals("高危")) {
            String day = dateUtil.afterDay();
            date = day + " " + sfm;
        } else if (fxjb.equals("极高危")) {
            date = dateUtil.get1Hour();
        }
    }

    @OnClick({R.id.iv_colse4, R.id.tv_commit4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_colse4:
                finish();
                break;
            case R.id.tv_commit4:
                if (tag.equals(Constant.ADD)) {
                    if (all == 0) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您还未评分");
                        return;
                    } else if (TextUtils.isEmpty(cuoshiMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "请选择合适的护理措施");
                        return;
                    } else {
                        centerDialog = new CenterDialog(VteRiskActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(VteRiskActivity.this);
                                    netWorkAdd();
                                    netWorkTime();
                                }
                            }
                        });
                        centerDialog.show();

                    }
                } else if (tag.equals(Constant.UPDATA)) {
                    if (inCsxx.equals(cuoshiMsg) && inPfxx.equals(allPingFenMsg)) {
                        ToastUtils.makeToast(MyApplication.getContext(), "您未作出任何修改");
                    } else {
                        centerDialog = new CenterDialog(VteRiskActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                if (view.getId() == R.id.bt_yes) {
                                    ToastUtils.showLoading(VteRiskActivity.this);
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
        String canshu = brzyid + f + bqId + f + "静脉血栓" + f + date;
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

    /**
     * 网络修改保存传参
     */
    private void netWorkUpdata() {
        if (cuoshiMsg.startsWith("#")) {
            cuoshiMsg = cuoshiMsg.substring(1, cuoshiMsg.length());
            Log.e("!!!!!", cuoshiMsg);
        }
        if (allPingFenMsg.startsWith("#")) {
            allPingFenMsg = allPingFenMsg.substring(1, allPingFenMsg.length());
            Log.e("-----", allPingFenMsg);
        }

        String allscore = tvScore4.getText().toString().trim();
        String fenxian = tvJingFenxian.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "7" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + allPingFenMsg + f + cuoShitag + f + itemId;
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
        if (cuoshiMsg.startsWith("#")) {
            cuoshiMsg = cuoshiMsg.substring(1, cuoshiMsg.length());
            Log.e("!!!!!", cuoshiMsg);
        }
        if (allPingFenMsg.startsWith("#")) {
            allPingFenMsg = allPingFenMsg.substring(1, allPingFenMsg.length());
            Log.e("-----", allPingFenMsg);
        }

        String allscore = tvScore4.getText().toString().trim();
        String fenxian = tvJingFenxian.getText().toString().trim();
        String curdate = dateUtil.getDate();
        String canshu = brzyid + f + curdate + f + allscore + f + fenxian + f + cuoshiMsg + f + pingguren + f + curdate + f + "7" + f + "" + f + ""
                + f + rydate + f + name + f + brid + f + sex + f + age + f + bqId + f + bqmc + f + allPingFenMsg + f + cuoShitag;
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
                Log.e("fail", info);
                ToastUtils.dismissLoading();
                ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
            }
        });
    }
}