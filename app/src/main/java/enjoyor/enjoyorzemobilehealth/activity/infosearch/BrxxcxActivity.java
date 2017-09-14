package enjoyor.enjoyorzemobilehealth.activity.infosearch;

import android.graphics.Color;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.activity.BrlbActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.views.BarGraphView;
import my_network.NetWork;
import my_network.ZhierCall;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class BrxxcxActivity extends BaseActivity {
    private BarGraphView mBarGraphView;
    float yxf;
    float yjj;
    ZhierCall zhierCall;
    ImageView tx;
    TextView xm;
    TextView ll;
    TextView dh;
    TextView ch;
    TextView hl;
    TextView ks;
    TextView zz;
    TextView gm;
    TextView bq;
    TextView xf;
    TextView yj;
    TextView ye;
    List<BRLB> listBRLB=new ArrayList<>();
    ProgressDialog progressDialog;
    BRLB brlb=null;
    ImageView back;
    LinearLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brxxcx);
        ButterKnife.bind(this);
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(this, mColor, 0);
        layout=(LinearLayout) findViewById(R.id.top);
        tx= (ImageView) findViewById(R.id.tx);
        xm= (TextView) findViewById(R.id.mz);
        //ll= (TextView) findViewById(R.id.nl);
        dh= (TextView) findViewById(R.id.dh);
        ch= (TextView) findViewById(R.id.ch);
        hl= (TextView) findViewById(R.id.hl);
        ks= (TextView) findViewById(R.id.ks);
        zz= (TextView) findViewById(R.id.zz);
        gm= (TextView) findViewById(R.id.gm);
        bq= (TextView) findViewById(R.id.bq);
        xf= (TextView) findViewById(R.id.xf);
        yj= (TextView) findViewById(R.id.yj);
        ye= (TextView) findViewById(R.id.ye);
        brlb=MyApplication.getInstance().getOther_brlb();
        back=(ImageView) findViewById(R.id.back);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","4");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(BrxxcxActivity.this,BrlbActivity.class);
                intent.putExtra("which","4");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(BrxxcxActivity.this,XxcxActivity.class));
                finish();
            }
        });
        if(brlb!=null){
            if(brlb.getXINGBIE().equals("男")){
                tx.setImageResource(R.drawable.icon_men);
            }else{
                tx.setImageResource(R.drawable.icon_women);
            }
            xm.setText(brlb.getXINGMING());
            //ll.setText(brlb.getNIANLING()+"岁");
            dh.setText(brlb.getLIANXIFS());
            ch.setText(brlb.getCHUANGWEIHAO()+"床");
            hl.setText(brlb.getHULIDJ());
            ks.setText(brlb.getKESHIDM());
            zz.setText(brlb.getZHUZHIYS());
            gm.setText(brlb.getGUOMINSHI());
            bq.setText(brlb.getZHENDUAN());
            xf.setText(brlb.getYIXIAOFEI());
            yj.setText(brlb.getYUJIAOJIN());

            String s=brlb.getYIXIAOFEI();
            String v=brlb.getYUJIAOJIN();
            //以缴费，预交金
            if(s.equals("")){
                yxf=0;
            }else{
                yxf = Float.valueOf(brlb.getYIXIAOFEI()).floatValue();
            }
            if(v.equals("")){
                yjj = Float.valueOf(brlb.getYUJIAOJIN()).floatValue();
            }

            float xfye= yjj-yxf;
            ye.setText(String.valueOf(xfye));
            MyApplication.getInstance().setOther_brlb(null);
            initView();
        }else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("加载中...");
            progressDialog.show();
            SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
            String name=preferences2.getString("id","");
            String canshu=preferences2.getString("bqdm","");

            zhierCall = (new ZhierCall())
                    .setId(name)
                    .setNumber("0500101")
                    .setMessage(NetWork.BINGREN_XX)
                    .setCanshu(canshu)
                    .setContext(this)
                    .setPort(5000)
                    .build();

            zhierCall.start(new NetWork.SocketResult() {

                @Override
                public void success(String data) {
                    StringXmlParser parser = new StringXmlParser(xmlHandler,
                            new Class[]{BRLB.class});
                    Log.v("login10",data);
                    parser.parse(data);
                }

                @Override
                public void fail(String info) {
                    // Toast.makeText(LoginActivity.,this info, Toast.LENGTH_LONG).show();
                }
            });
        }



    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    progressDialog.dismiss();
                    final BRLB brlb=listBRLB.get(0);
                    if(brlb.getXINGBIE().equals("男")){
                        tx.setImageResource(R.drawable.icon_men);
                        tx.setMaxHeight(20);
                    }else{
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    xm.setText(brlb.getXINGMING());
                    dh.setText(brlb.getLIANXIFS());
                    ch.setText(brlb.getCHUANGWEIHAO()+"床");
                    hl.setText(brlb.getHULIDJ());
                    ks.setText(brlb.getKESHIDM());
                    zz.setText(brlb.getZHUZHIYS());
                    gm.setText(brlb.getGUOMINSHI());
                    bq.setText(brlb.getZHENDUAN());
                    xf.setText(brlb.getYIXIAOFEI());
                    yj.setText(brlb.getYUJIAOJIN());

                    String s=brlb.getYIXIAOFEI();
                    String v=brlb.getYUJIAOJIN();
                    //以缴费，预交金
                    if(s.equals("")){
                        yxf=0;
                    }else{
                        yxf = Float.valueOf(brlb.getYIXIAOFEI()).floatValue();
                    }
                    if(v.equals("")){
                        yjj = Float.valueOf(brlb.getYUJIAOJIN()).floatValue();
                    }

                    float xfye= yjj-yxf;
                    ye.setText(String.valueOf(xfye));
                    MyApplication.getInstance().setOther_brlb(null);
                    initView();
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLB.add((BRLB) msg.obj);
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
    private void initView(){
        int a,b;
        mBarGraphView = (BarGraphView)findViewById(R.id.custom_view);
        mBarGraphView.setAxisX(90, 9);
        mBarGraphView.setAxisY(70,7);
        if(yjj>yxf){
            a=60;
            b=(int)(60*yxf/yjj);
        }else{
            b=60;
            a=(int)(60*yjj/yxf);
        }
        int columnInfo[][] = new int[][]{{a, Color.parseColor("#FFB90F")},{0,Color.WHITE},{b, Color.parseColor("#EE4000")}};
        mBarGraphView.setColumnInfo(columnInfo);
    }
}
