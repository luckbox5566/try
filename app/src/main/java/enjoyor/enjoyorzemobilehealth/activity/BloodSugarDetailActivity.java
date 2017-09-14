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
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.BLoodSugarDetailAdapter;
import enjoyor.enjoyorzemobilehealth.entities.BloodSugarJL;

/**
 * Created by Administrator on 2017-08-22.
 */

public class BloodSugarDetailActivity extends AppCompatActivity {
    private List<BloodSugarJL> listBLoodSugarJL =new ArrayList<>();
    private ImageView mIvBack;
    private ListView mLvBSDetail;
    private Context context;
    private LinearLayout nodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bloodsugar_detail);
        context=this;
        initData();
        initView();
    }

    private void initData() {
        Intent intent=getIntent();
        listBLoodSugarJL= (List<BloodSugarJL>) intent.getSerializableExtra("listBLoodSugarJL");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLvBSDetail = (ListView) findViewById(R.id.lv_bloodsugar_detail);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        BLoodSugarDetailAdapter adapter=new BLoodSugarDetailAdapter(context,listBLoodSugarJL);
        if(listBLoodSugarJL.get(0).getDate().equals("")){
            nodata.setVisibility(View.VISIBLE);
            mLvBSDetail.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.GONE);
            mLvBSDetail.setVisibility(View.VISIBLE);
            mLvBSDetail.setAdapter(adapter);
            mLvBSDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent();
                    intent.putExtra("position",position);
                    intent.putExtra("cjsj",listBLoodSugarJL.get(position).getDate());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

        }
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
