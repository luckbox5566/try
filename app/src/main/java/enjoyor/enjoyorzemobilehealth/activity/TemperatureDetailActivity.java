package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.TemperatureDetailAdapter;
import enjoyor.enjoyorzemobilehealth.entities.TiWen;

/**
 * Created by Administrator on 2017/6/7.
 */

public class TemperatureDetailActivity extends AppCompatActivity{
    private Context context;
    private List<TiWen> mTiWenList;
    private ImageView mIvBack;
    private ListView mLvTemperatureDetail;
    private RelativeLayout emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temperature_detail);
        context=this;
        initView();
        getData();
    }

    private void getData() {
        mTiWenList=new ArrayList<>();
        Intent intent=getIntent();
        mTiWenList= (List<TiWen>) intent.getSerializableExtra("temperatureList");
        mLvTemperatureDetail.setAdapter(new TemperatureDetailAdapter(context,mTiWenList));
        mLvTemperatureDetail.setEmptyView(emptyView);
    }

    private void initView() {
        mIvBack= (ImageView) findViewById(R.id.iv_back);
        mLvTemperatureDetail= (ListView) findViewById(R.id.lv_temperature_detail);
        emptyView= (RelativeLayout) findViewById(R.id.rl_empty_view);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
