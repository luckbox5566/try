package enjoyor.enjoyorzemobilehealth.fragment.danger;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.FallBedActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.YaChuangActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.ZiLiAbilityActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.PainZiPingActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.VteRiskActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.FallActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.PainTaPingActivity;
import enjoyor.enjoyorzemobilehealth.activity.danger.UnPlanActivity;
import enjoyor.enjoyorzemobilehealth.adapter.RightAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.AllWXYS;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment implements View.OnClickListener {


    TextView tvBiaoti;
    TextView tvGuize;
    ImageView ivDengerAdd;
    ListView lvData;

    private String type;
    private Intent intent;
    private String bingrenzyid = "";//病人住院id
    private String name = "";//病人名字
    private String chuangweihao = "";//床位号
    private String ruyuanDate;
    private String pgrname;//评估人名字
    private String bingrenid;
    private String sex;
    private String age;
    private ZhierCall zhierCall;
    private String uesrid;
    private List<AllWXYS> list;
    private int num;
    private RightAdapter rightAdapter;
    private String date;
    private String lastWeek;
    private String fgf;
    private String sfm;
    private String curtime;
    private String lastweek;
    private CenterDialog dialog;
    private MyApplication instance;
    private DangerActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init();
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        initCanshu();
        initView(view);

        initLister();
        return view;

    }

    private void init() {
        fgf = NetWork.SEPARATE;

        type = getArguments().getString("type");
        num = getArguments().getInt("i");
        activity = (DangerActivity) getActivity();
        curtime = activity.getCurtime();
        lastweek = activity.getLastweek();
        instance = MyApplication.getInstance();
    }

    private void initCanshu() {
        intent = getActivity().getIntent();
        String tag = intent.getStringExtra(Constant.TAG);
        try {
            if (tag.equals("bingrenlist")) {
                name = intent.getStringExtra(Constant.BRNAME);
                chuangweihao = intent.getStringExtra(Constant.BRCWH);
                bingrenzyid = intent.getStringExtra(Constant.BRZYID);
                ruyuanDate = intent.getStringExtra(Constant.RYSJ);
                bingrenid = intent.getStringExtra(Constant.BRID);
                sex = intent.getStringExtra(Constant.SEX);
                age = intent.getStringExtra(Constant.AGE);
                pgrname = intent.getStringExtra(Constant.PGRNAME);

            } else if (tag.equals("home")) {
                BRLB brlb = instance.getListBRLB().get(0);
                name = brlb.getXINGMING();//姓名
                chuangweihao = brlb.getCHUANGWEIHAO();//床位号
                bingrenzyid = brlb.getBINGRENZYID();//病人住院ID
                ruyuanDate = brlb.getRUYUANSJ();//病人入院时间
                bingrenid = brlb.getBINGRENID();//病人id
                sex = brlb.getXINGBIE();//病人性别
                age = brlb.getNIANLING();//病人年龄
            }

        } catch (Exception e) {
            Log.e("initCanshu", e.toString());
        }
        uesrid = (String) SaveUtils.get(getActivity(), Constant.USERID, "");
    }
    private void initView(View view) {
        tvBiaoti = (TextView) view.findViewById(R.id.tv_biaoti);
        tvGuize = (TextView) view.findViewById(R.id.tv_guize);
        ivDengerAdd = (ImageView) view.findViewById(R.id.iv_denger_add);
        lvData = (ListView) view.findViewById(R.id.lv_data);

        tvBiaoti.setText(type);
    }

    @Override
    public void onResume() {
        super.onResume();
        String hp = (String) SaveUtils.get(getActivity(), Constant.IS_COMMIT, "");
        String yc = (String) SaveUtils.get(getActivity(), Constant.IS_COMMIT, "");
        if (hp.equals("homepage")) {
            initData();
        } else if (yc.equals("yachuang")) {
            initData();
            Log.e("压疮", "保存");
        }
    }

    private void initData() {
        list = new ArrayList<>();
        ToastUtils.showLoading(getActivity());
        String canshu = bingrenzyid + fgf + num + fgf + lastweek + fgf + curtime;
        Log.e("时间", lastweek + "===" + curtime);
        Log.e("RightFragment--参数", canshu);

        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(canshu)
                .setContext(getActivity())
                .setPort(Constant.PORT)
                .build();


        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{AllWXYS.class});
                if (data != null) {
                    parser.parse(data);
                }
                Log.e("数据", data);
                System.out.print(data + "数据");
            }

            @Override
            public void fail(String info) {
                ToastUtils.dismissLoading();
                Log.e("fail", info);
            }
        });


    }

    private void initLister() {
        tvGuize.setOnClickListener(this);
        ivDengerAdd.setOnClickListener(this);
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog = new CenterDialog(getActivity(), R.layout.dialog_del, new int[]{R.id.bt_del_yes,R.id.bt_del_no});
                dialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CenterDialog dialog, View view) {
                        if(view.getId()==R.id.bt_del_yes){
                            String id = list.get(position).getID();
                            list.remove(position);
                            rightAdapter.notifyDataSetChanged();
                            delItem(id);
                        }
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
        //跳转  修改 页面
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AllWXYS bean = null;
                switch (type) {
                    case "压疮"://1
                        bean = list.get(position);
                        intent = new Intent(getActivity(), YaChuangActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "压疮（修改）");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "跌倒/坠床"://2
                        bean = list.get(position);
                        intent = new Intent(getActivity(), FallBedActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "跌倒/坠床(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "自理能力"://3
                        bean = list.get(position);
                        intent = new Intent(getActivity(), ZiLiAbilityActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "自理能力(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "非计划先拔管"://4
                        bean = list.get(position);
                        intent = new Intent(getActivity(), UnPlanActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "非计划先拔管(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "疼痛（自评）"://5
                        bean = list.get(position);
                        intent = new Intent(getActivity(), PainZiPingActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "疼痛自评(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "疼痛（他评）"://6
                        bean = list.get(position);
                        intent = new Intent(getActivity(), PainTaPingActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "疼痛他评(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    case "VTE风险护理"://7
                        bean = list.get(position);
                        intent = new Intent(getActivity(), VteRiskActivity.class);
                        intent.putExtra("bean", bean);
                        intent.putExtra("title", "VTE风险护理(修改)");
                        intent.putExtra(Constant.TAG, "updata");
                        chuanZhi();
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 删除item
     *
     * @param id
     */
    private void delItem(String id) {
        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId(uesrid)
                .setNumber(Constant.DANGER_DEL)
                .setMessage(NetWork.PINGGUD)
                .setCanshu(id)
                .setContext(getActivity())
                .setPort(Constant.PORT)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
//                initData();
                ToastUtils.makeToast(getActivity().getApplicationContext(), "删除成功");
            }

            @Override
            public void fail(String info) {
                ToastUtils.dismissLoading();
                Log.e(" 删除fail", info);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_guize:
                dialog = new CenterDialog(getActivity(), R.layout.dialog_my, new int[]{R.id.iv_dialog_close});
                dialog.show();
                break;
            case R.id.iv_denger_add:
                start(type);
                break;
            default:
                break;
        }
    }

//添加跳转
    private void start(String trim) {
        switch (trim) {
            case "压疮"://1
                intent = new Intent(getActivity().getApplicationContext(), YaChuangActivity.class);
                intent.putExtra("title", "压疮评估");
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            case "跌倒/坠床"://2
                intent = new Intent(getActivity().getApplicationContext(), FallBedActivity.class);
                intent.putExtra("title", "跌倒/坠床风险评估");
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            case "自理能力"://3  ADL
                intent = new Intent(getActivity().getApplicationContext(), ZiLiAbilityActivity.class);
                intent.putExtra("title", "自理能力评估");
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            case "非计划先拔管"://4
                intent = new Intent(getActivity().getApplicationContext(), UnPlanActivity.class);
                intent.putExtra("title", "非计划先拔管风险评估");
                intent.putExtra(Constant.TAG,"add");
                chuanZhi();
                break;
            case "疼痛（自评）"://5
                intent = new Intent(getActivity().getApplicationContext(), PainZiPingActivity.class);
                intent.putExtra("title", "疼痛程度评估（自评）");
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            case "疼痛（他评）"://6
                intent = new Intent(getActivity().getApplicationContext(), PainTaPingActivity.class);
                intent.putExtra("title", "疼痛行为评估(他评)");
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            case "VTE风险护理"://7
                intent = new Intent(getActivity().getApplicationContext(), VteRiskActivity.class);
                intent.putExtra("title", "VTE风险评估");//栓塞症
                intent.putExtra(Constant.TAG, "add");
                chuanZhi();
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private void chuanZhi() {
        intent.putExtra(Constant.BRZYID, bingrenzyid);
        intent.putExtra(Constant.RYSJ, ruyuanDate);
        intent.putExtra(Constant.BRNAME, name);
        intent.putExtra(Constant.BRID, bingrenid);
        intent.putExtra(Constant.SEX, sex);
        intent.putExtra(Constant.AGE, age);
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, getActivity()) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    ToastUtils.dismissLoading();
                    if (list.size() > 0 && list != null) {
                        rightAdapter = new RightAdapter(list, getActivity());
                        lvData.setAdapter(rightAdapter);
                    }
                    break;
                case NODE:
                    AllWXYS wxys = null;
                    switch (msg.arg1) {
                        case 0:
                            wxys = (AllWXYS) msg.obj;
                            String type = wxys.getLX();
                            System.out.println(((AllWXYS) msg.obj).getPGHS());
                            switch (type) {
                                case "1":
                                    list.add(wxys);
                                    break;
                                case "2":
                                    list.add(wxys);
                                    break;
                                case "3":
                                    list.add(wxys);
                                    break;
                                case "4":
                                    list.add(wxys);
                                    break;
                                case "5":
                                    list.add(wxys);
                                    break;
                                case "6":
                                    list.add(wxys);
                                    break;
                                case "7":
                                    list.add(wxys);
                                    break;
                                case "8":
                                    list.add(wxys);
                                    break;
                                case "9":
                                    list.add(wxys);
                                    break;
                                case "10":
                                    list.add(wxys);
                                    break;
                                default:
                                    break;
                            }
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
