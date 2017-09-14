package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/7/24.
 */

public class JkjyActivity extends BaseActivity {
    private ImageView back;
    private LinearLayout ryjd;
    private LinearLayout zyjd;
    private LinearLayout cyzd;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkjy_login);
        defineData();
        clickData();
    }
    private void defineData(){
        back = (ImageView) findViewById(R.id.back);
        ryjd = (LinearLayout) findViewById(R.id.ryjd);
        zyjd = (LinearLayout) findViewById(R.id.zyjd);
        cyzd = (LinearLayout) findViewById(R.id.cyzd);
    }
    private void clickData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JkjyActivity.this,HomePageActivity.class));
                finish();
            }
        });
        ryjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(JkjyActivity.this,RyjdActivity.class);
                intent.putExtra("eduStyle","入院阶段");
                intent.putExtra("eduStyleID","1");
                startActivity(intent);
                finish();
            }
        });
        zyjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(JkjyActivity.this,RyjdActivity.class);
                intent.putExtra("eduStyle","住院阶段");
                intent.putExtra("eduStyleID","2");
                startActivity(intent);
                finish();
            }
        });
        cyzd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(JkjyActivity.this,RyjdActivity.class);
                intent.putExtra("eduStyle","出院指导");
                intent.putExtra("eduStyleID","3");
                startActivity(intent);
                finish();
            }
        });
    }

}
