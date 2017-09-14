package enjoyor.enjoyorzemobilehealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.SSCX;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class SscxActivity extends BaseActivity {
    ZhierCall zhierCall;
    ImageView imageView;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    TextView textView11;
    TextView textView12;
    List<SSCX> listBRLB=new ArrayList<>();
    ProgressDialog progressDialog;
    LinearLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sscx);
        imageView= (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(SscxActivity.this,HomePageActivity.class));
            }
        });
        layout= (LinearLayout) findViewById(R.id.sscx_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","1");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(SscxActivity.this,BrlbActivity.class);
                intent.putExtra("which","sscx");
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String canshu=preferences2.getString("bqdm","");
        imageView= (ImageView) findViewById(R.id.tx);
        textView1= (TextView) findViewById(R.id.mz);
        textView2= (TextView) findViewById(R.id.nl);
        textView3= (TextView) findViewById(R.id.ch);
        textView4= (TextView) findViewById(R.id.ss1);
        textView5= (TextView) findViewById(R.id.ss2);
        textView6= (TextView) findViewById(R.id.ss3);
        textView7= (TextView) findViewById(R.id.sq);
        textView8= (TextView) findViewById(R.id.sh);
        textView9= (TextView) findViewById(R.id.ss4);
        textView10= (TextView) findViewById(R.id.mzry);
        textView11= (TextView) findViewById(R.id.sssj);
        textView12= (TextView) findViewById(R.id.bl);



        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0401513")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{SSCX.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    Intent intent=getIntent();
                    String ooo=intent.getStringExtra("ooo");
                    if(ooo==null){
                        if(listBRLB.get(0).getXingBie().equals("男")){
                            imageView.setImageResource(R.drawable.icon_men);
                        }else {
                            imageView.setImageResource(R.drawable.icon_women);
                        }
                        textView1.setText(listBRLB.get(0).getBingRenXM());
                        textView2.setText(listBRLB.get(0).getNianLing()+"岁");
                        textView3.setText(listBRLB.get(0).getChuangWeiHao()+"号");
                        textView4.setText(listBRLB.get(0).getShouSuMC());
                        textView5.setText("");
                        textView6.setText(listBRLB.get(0).getShouShuMX());
                        textView7.setText("术前诊断："+listBRLB.get(0).getShuQianZD());
                        textView8.setText("术后诊断："+listBRLB.get(0).getShuHouZD());
                        textView9.setText("手术人员："+listBRLB.get(0).getShouShuRY());
                        textView10.setText("麻醉人员："+listBRLB.get(0).getMaZuiRY());
                        textView11.setText("手术时间："+listBRLB.get(0).getShouShuShi());
                        textView12.setText("病例状态："+listBRLB.get(0).getMaZuiRY());
                    }else{
                        int i=0;
                        for(SSCX x:listBRLB){

                            if(ooo.equals(x.getBingRenZYID())){
                                if(listBRLB.get(i).getXingBie().equals("男")){
                                    imageView.setImageResource(R.drawable.icon_men);
                                }else {
                                    imageView.setImageResource(R.drawable.icon_women);
                                }
                                textView1.setText(listBRLB.get(i).getBingRenXM());
                                textView2.setText(listBRLB.get(i).getNianLing()+"岁");
                                textView3.setText(listBRLB.get(i).getChuangWeiHao()+"号");
                                textView4.setText(listBRLB.get(i).getShouSuMC());
                                textView5.setText("");
                                textView6.setText(listBRLB.get(i).getShouShuMX());
                                textView7.setText("术前诊断："+listBRLB.get(i).getShuQianZD());
                                textView8.setText("术后诊断："+listBRLB.get(i).getShuHouZD());
                                textView9.setText("手术人员："+listBRLB.get(i).getShouShuRY());
                                textView10.setText("麻醉人员："+listBRLB.get(i).getMaZuiRY());
                                textView11.setText("手术时间："+listBRLB.get(i).getShouShuShi());
                                textView12.setText("病例状态："+listBRLB.get(i).getMaZuiRY());
                            }
                            i++;
                        }
                    }


                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLB.add((SSCX) msg.obj);
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
