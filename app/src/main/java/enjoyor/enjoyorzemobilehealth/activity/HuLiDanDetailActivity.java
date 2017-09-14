package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.HuLiDanDetailAdapter;
import enjoyor.enjoyorzemobilehealth.entities.HuLiJiLu;

/**
 * Created by Administrator on 2017/7/10.
 */

public class HuLiDanDetailActivity extends AppCompatActivity {
    private ImageView mIvBack;
    private ListView mLvHldDetail;
    private List<HuLiJiLu> huLiJiLuList=new ArrayList<>();

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hulidan_detail);
        context=this;
        initData();
        initView();
    }

    private void initData() {
        Intent intent=getIntent();
        huLiJiLuList= (List<HuLiJiLu>) intent.getSerializableExtra("huLiJiLuList");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLvHldDetail = (ListView) findViewById(R.id.lv_hulidan_detail);
        HuLiDanDetailAdapter adapter=new HuLiDanDetailAdapter(context,huLiJiLuList);
        mLvHldDetail.setAdapter(adapter);
        mLvHldDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("position",position);
                intent.putExtra("cjsj",huLiJiLuList.get(position).getCaiJiSJ());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
