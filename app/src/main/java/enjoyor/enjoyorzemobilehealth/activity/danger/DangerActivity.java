package enjoyor.enjoyorzemobilehealth.activity.danger;

import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.my_xml.entities.BRLB;

import java.text.SimpleDateFormat;
import java.util.Date;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.adapter.LeftAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.fragment.danger.RightFragment;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.DateUtil;

public class DangerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_danger_back;
    private LinearLayout ll_msg;
    private TextView tv_bingrenname;
    private TextView tv_chuanghao;
    private TextView tv_date_before;
    private TextView tv_date_after;
    private ListView lv_left;
    private LeftAdapter leftAdapter;
    /**
     * 数据源
     */
    private String data[];
    public static int mPosition;

    private String curNyr;//年月日
    private String lastNyr;

    public String curtime, lastweek;//年月日时分秒
    private DateUtil instance;

    public String getCurtime() {
        return curtime;
    }

    public void setCurtime(String curtime) {
        this.curtime = curtime;
    }

    public void setLastweek(String lastweek) {
        this.lastweek = lastweek;
    }

    public String getLastweek() {
        return lastweek;
    }

    private String type;


    private Intent intent;
    private FrameLayout fragment_danger;
    private RightFragment fragment;
    private FragmentTransaction fragmentTransaction;
    private Bundle bundle;
    private String name;
    private String chuangweihao;
    private TimePickerView pvTime;
    private SimpleDateFormat format;
    private String curdate;
    private String sfm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);
        initView();
        init();
        initTime();
        setMoren();
        setData();
        initLinstener();
    }


    private void initView() {
        lv_left = (ListView) findViewById(R.id.lv_left);
        fragment_danger = (FrameLayout) findViewById(R.id.fragment_danger);

        iv_danger_back = (ImageView) findViewById(R.id.iv_danger_back);
        ll_msg = (LinearLayout) findViewById(R.id.ll_msg);
        tv_bingrenname = (TextView) findViewById(R.id.tv_bingrenname);
        tv_chuanghao = (TextView) findViewById(R.id.tv_chuanghao);
        tv_date_before = (TextView) findViewById(R.id.tv_date_before);
        tv_date_after = (TextView) findViewById(R.id.tv_date_after);
    }

    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.my_bule), 0);

        intent = getIntent();
        String tag = intent.getStringExtra(Constant.TAG);
        try {
            if (tag.equals("bingrenlist")) {
                name = intent.getStringExtra(Constant.BRNAME);
                chuangweihao = intent.getStringExtra(Constant.BRCWH);
            } else if (tag.equals("home")) {
                BRLB brlb = MyApplication.getInstance().getListBRLB().get(0);
                name = brlb.getXINGMING();//姓名
                chuangweihao = brlb.getCHUANGWEIHAO();//床位号
            }

        } catch (Exception e) {
            finish();
            Log.e("DangerActivity-94", e.toString());
        }

        tv_chuanghao.setText(chuangweihao);
        tv_bingrenname.setText(name);

    }

    private void initTime() {
        format = new SimpleDateFormat("yyyy/MM/dd");
        instance = DateUtil.getInstance();
        curNyr = instance.getYear_Day();
        sfm = instance.getHour_s();
        lastNyr = instance.lastWeek();

        tv_date_before.setText(lastNyr);
        tv_date_after.setText(curNyr);

        curdate = curNyr + "" + sfm;
        lastweek = lastNyr + " " + sfm;
        setCurtime(curdate);
        setLastweek(lastweek);
        Log.e("默认时间", curdate + "===" + lastweek);
    }

    private void setMoren() {
        data = getResources().getStringArray(R.array.danger_menus);

        //创建fragment
        fragment = new RightFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_danger, fragment);
        //通过bundle传值给MyFragment
        bundle = new Bundle();
        bundle.putString("type", data[0]);
        bundle.putInt("i", 1);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }


    private void setData() {
        leftAdapter = new LeftAdapter(DangerActivity.this, data);
        lv_left.setAdapter(leftAdapter);
    }

    private void initLinstener() {
        iv_danger_back.setOnClickListener(this);
        ll_msg.setOnClickListener(this);
        tv_date_before.setOnClickListener(this);
        tv_date_after.setOnClickListener(this);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                freshPage(mPosition);
            }
        });
    }

    private void freshPage(int position) {
        //拿到当前位置
        //一级分类id
        type = data[position];
        //即时刷新adapter
        leftAdapter.notifyDataSetChanged();
        fragment = new RightFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_danger, fragment);
        bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putInt("i", position + 1);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_danger_back:
                finish();
                break;
            case R.id.ll_msg:
                intent = new Intent(DangerActivity.this, BrlbActivity.class);
                intent.putExtra("which", "danger");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_date_before:
                //时间选择器
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调

                        String time = format.format(date);
                        lastweek = time + " " + sfm;
                        setLastweek(lastweek);
                        Log.e("前", lastweek);
//                        freshPage(mPosition);
                        tv_date_before.setText(time);
                    }
                }).setDate(instance.getCalendar(tv_date_before.getText().toString()))
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();
                break;
            case R.id.tv_date_after:
                //时间选择器
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        String time = format.format(date);
                        curdate = time + " " + sfm;
                        setCurtime(curdate);
                        freshPage(mPosition);
                        tv_date_after.setText(time);
                        Log.e("后", curdate);
                    }
                }).setDate(instance.getCalendar(tv_date_after.getText().toString()))
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setTitleColor(getResources().getColor(R.color.text_color))//标题文字颜色)//标题文字颜色
                        .setTitleText("查询日期")//标题文字
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isDialog(true)
                        .build();
                pvTime.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(" shengmingonStart","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("shengmingonStop","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(" shengmingonPause","onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(" shengmingonRestart","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPosition = 0;
        Log.e(" shengmingonResume","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPosition = 0;
//        if(haha!=null){
//            haha = null;
//        }
        Log.e(" onDestroy","onDestroy");
    }
}
