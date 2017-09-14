package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.HomePageEntity;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;

import com.bben.view.BryzBbenActivity;
import com.example.my_xml.entities.BRLB;
import com.jaeger.library.StatusBarUtil;
import com.version.rypg.RypgBbenActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;

import enjoyor.enjoyorzemobilehealth.activity.infosearch.XhcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.XxcxActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.HomePageEntity;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;

public class HomePageActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    List<HomePageEntity> list = new ArrayList<>();
    private Intent intent;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        setList();
        setStatusBar();
        setToolBar();
        setRecyclerView();
        textView= (TextView) findViewById(R.id.gh);
        textView.setText("当前工号为："+MyApplication.getInstance().getYhgh());
    }

    /**
     * 从资源文件中获得图片资源和名字资源
     */
    private void setList() {
        Resources re = getResources();
        TypedArray name = re.obtainTypedArray(R.array.homepage_name);
        TypedArray drawable = re.obtainTypedArray(R.array.homepage_drawable);
        Log.e("数组长度",re.getStringArray(R.array.homepage_name).length+"--");
        for (int i = 0; i < re.getStringArray(R.array.homepage_name).length; i++)
            list.add(new HomePageEntity(re.getDrawable(drawable.getResourceId(i, 0)), name.getString(i)));
        name.recycle();
        drawable.recycle();
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new CommonAdapter<HomePageEntity>(this, R.layout.homepage_recycler_view_item, list) {
            @Override
            protected void convert(ViewHolder holder, HomePageEntity homePageEntity, int position) {
                holder.setImageDrawable(R.id.image_home, list.get(position).getDrawable());
                holder.setText(R.id.text_home, list.get(position).getName());

                final String name = list.get(position).getName();
                holder.setOnClickListener(R.id.bbb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (name) {
                            case "病人列表":
                                startActivity(new Intent(HomePageActivity.this, BrlbActivity.class));
                                finish();
                                break;
                            case "病人医嘱":
                                List<BRLB> list = MyApplication.getInstance().getListBRLB();
                                intent = new Intent(HomePageActivity.this, BryzBbenActivity.class);
                                intent.putExtra("xingming", list.get(0).getXINGMING());
                                intent.putExtra("xingbie", list.get(0).getXINGBIE());
                                intent.putExtra("chuanghao", list.get(0).getCHUANGWEIHAO());
                                startActivity(intent);
                                finish();
                                break;
                            case "医嘱核对":
                                startActivity(new Intent(HomePageActivity.this, BingQuYiZhuActivity.class));
                                break;
                            case "入院评估":
                                startActivity(new Intent(HomePageActivity.this, RypgBbenActivity.class));
                                break;
                            case "危险评估":
                                intent = new Intent(HomePageActivity.this, DangerActivity.class);
                                intent.putExtra(Constant.TAG,"home");
                                SaveUtils.put(HomePageActivity.this,Constant.IS_COMMIT,"homepage");
                                startActivity(intent);
                                break;
                            case "生命体征录入":
                                startActivity(new Intent(HomePageActivity.this,ShengMingTiZhengLuRuActivity.class));
                                break;
                            case "温度采集":
                                startActivity(new Intent(HomePageActivity.this,TemperatureChartActivity.class));
                                break;
                            case "护理单":
                                startActivity(new Intent(HomePageActivity.this,HuLiDanActivity.class));
                                break;
                            case "信息查询":
                                startActivity(new Intent(HomePageActivity.this,XxcxActivity.class));
                                break;
                            case "巡回记录":
                                startActivity(new Intent(HomePageActivity.this,XhcxActivity.class));
                                break;
                            case "健康教育":
                                startActivity(new Intent(HomePageActivity.this,JkjyActivity.class));
                                break;
                            case "血糖监测":
                                startActivity(new Intent(HomePageActivity.this,BloodSugarCheckActivity.class));
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });

    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(HomePageActivity.this, mColor, 0);
    }

    /**
     * 设置ToolBar
     */
    private void setToolBar() {
        toolbar.setTitle("银江移动医疗");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(HomePageActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_notifications:
                        Toast.makeText(HomePageActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Tool设置菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.findItem(RcyMoreAdapter.id.action_search).getIcon().setBounds(0,0,20,20);
        //getMenuInflater().inflate(RcyMoreAdapter.menu.hone_page_menu, menu);
        return true;
    }

}
