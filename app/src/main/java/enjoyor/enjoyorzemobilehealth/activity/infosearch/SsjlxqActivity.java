package enjoyor.enjoyorzemobilehealth.activity.infosearch;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;


/**
 * Created by Administrator on 2017/7/3.
 */

public class SsjlxqActivity extends BaseActivity {
    TextView xm;
    TextView sj;
    TextView ssmc;
    TextView zd;
    TextView mzry;
    TextView sqzd;
    TextView shzd;
    ImageView xb;
    TextView ssmx;
    TextView ch;
    TextView zksq;
    ImageView back;
    ImageView zk;
    LinearLayout layout;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssjlxq);
        definedata();
        clickData();
        initData();
    }
    private  void definedata(){

        xm=(TextView) findViewById(R.id.xm);
        sj=(TextView) findViewById(R.id.sj);
        ssmc=(TextView) findViewById(R.id.ssmc);
        zd=(TextView) findViewById(R.id.zd);
        mzry=(TextView) findViewById(R.id.mzry);
        sqzd=(TextView) findViewById(R.id.sqzd);
        shzd=(TextView) findViewById(R.id.shzd);
        ssmx=(TextView) findViewById(R.id.ssmx);
        xb=(ImageView) findViewById(R.id.tx);
        ch=(TextView) findViewById(R.id.ch);
        zksq=(TextView) findViewById(R.id.zksq);
        back=(ImageView) findViewById(R.id.back);
        zk=(ImageView) findViewById(R.id.zk);
        layout= (LinearLayout) findViewById((R.id.expand_button));


    }
    private void clickData(){
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SsjlxqActivity.this,SsjlcxActivity.class));
                finish();
            }
        });
        layout.setOnClickListener(new View.OnClickListener(){
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag=false;
                    // ssmx.setEllipsize(null);
                    ssmx.setMaxLines(50);
                    zksq.setText("收起");
                    zk.setImageResource(R.drawable.ic_normal);
                }else{
                    flag=true;
                    //  ssmx.setEllipsize(TextUtils.TruncateAt.END);
                    ssmx.setMaxLines(3);
                    zksq.setText("展开");
                    zk.setImageResource(R.drawable.ic_expand);
                }
            }

        });
    }
    private void initData(){

        Intent intent=getIntent();
        xm.setText(intent.getStringExtra("xm"));
        sj.setText(intent.getStringExtra("sj"));
        ssmc.setText(intent.getStringExtra("ssmc"));
        zd.setText(intent.getStringExtra("zd"));
        mzry.setText(intent.getStringExtra("mzry"));
        sqzd.setText(intent.getStringExtra("sqzd"));
        shzd.setText(intent.getStringExtra("shzd"));
        ch.setText(intent.getStringExtra("ch")+"床");
        ssmx.setText(intent.getStringExtra("ssmx"));
        // Speaad.toggleEllipsize(ssmx,intent.getStringExtra("ssmx"));
        if(intent.getStringExtra("xb").equals("男")){
            xb.setImageResource(R.drawable.icon_men);
        }else{
            xb.setImageResource(R.drawable.icon_women);
        }

        ViewTreeObserver observer = ssmx.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = ssmx.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                Log.d("testsffs", String.valueOf(ssmx.getLineCount()));
                if(ssmx.getLineCount()>3){
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}


