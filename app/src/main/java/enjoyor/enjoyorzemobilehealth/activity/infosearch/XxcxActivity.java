package enjoyor.enjoyorzemobilehealth.activity.infosearch;

/**
 * Created by Administrator on 2017/7/14.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.HomePageActivity;
import enjoyor.enjoyorzemobilehealth.entities.HomePageEntity;

public class XxcxActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    List<HomePageEntity> list=new ArrayList<>();
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private ImageView back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xxcx);
        layout1= (LinearLayout) findViewById(R.id.brxxcx);
        layout2= (LinearLayout) findViewById(R.id.jyjgcx);
        layout3= (LinearLayout) findViewById(R.id.ssjlcx);
        layout4= (LinearLayout) findViewById(R.id.jcjgcx);

        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XxcxActivity.this,HomePageActivity.class));
                finish();
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XxcxActivity.this,BrxxcxActivity.class));
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XxcxActivity.this,JyjgcxActivity.class));
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XxcxActivity.this,SsjlcxActivity.class));
            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XxcxActivity.this,JcjgcxActivity.class));
            }
        });



    }

}

