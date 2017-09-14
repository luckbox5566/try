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
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.BRLBBC;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class BrxxActivity extends BaseActivity {
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
    TextView ry;
    ImageView imageView;
    List<BRLB> listBRLB=new ArrayList<>();
    ProgressDialog progressDialog;
    BRLB brlb=null;
    LinearLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brxx);
        ButterKnife.bind(this);
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(this, mColor, 0);
        layout= (LinearLayout) findViewById(R.id.top);
        tx= (ImageView) findViewById(R.id.tx);
        xm= (TextView) findViewById(R.id.mz);
        ll= (TextView) findViewById(R.id.nl);
        dh= (TextView) findViewById(R.id.dh);
        ch= (TextView) findViewById(R.id.ch);
        hl= (TextView) findViewById(R.id.hl);
        ks= (TextView) findViewById(R.id.ks);
        zz= (TextView) findViewById(R.id.zz);
        gm= (TextView) findViewById(R.id.gm);
        bq= (TextView) findViewById(R.id.bq);
        xf= (TextView) findViewById(R.id.xf);
        ry= (TextView) findViewById(R.id.ry);
        brlb=MyApplication.getInstance().getOther_brlb();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("which","1");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent=new Intent(BrxxActivity.this,BrlbActivity.class);
                intent.putExtra("which","1");
                startActivity(intent);
                finish();
            }
        });

        if(brlb!=null){
            imageView= (ImageView) findViewById(R.id.back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    if(brlb==null){
                        startActivity(new Intent(BrxxActivity.this,HomePageActivity.class));
                    }else{
                        MyApplication.getInstance().setOther_brlb(null);
                        startActivity(new Intent(BrxxActivity.this,BrlbActivity.class));
                    }
                }
            });
            if(brlb.getXINGBIE().equals("男")){
                tx.setImageResource(R.drawable.icon_men);
            }else{
                tx.setImageResource(R.drawable.icon_women);
            }
            xm.setText(brlb.getXINGMING());
            ll.setText(brlb.getNIANLING()+"岁");
            dh.setText(brlb.getLIANXIFS());
            ch.setText(brlb.getCHUANGWEIHAO()+"床");
            hl.setText(brlb.getHULIDJ());
            ks.setText(brlb.getKESHIDM());
            zz.setText(brlb.getZHUZHIYS());
            gm.setText(brlb.getGUOMINSHI());
            bq.setText(brlb.getZHENDUAN());
            xf.setText(brlb.getYIXIAOFEI());
            ry.setText("入院时间"+brlb.getRUYUANSJ());
            MyApplication.getInstance().setOther_brlb(null);
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
                    parser.parse(data);
                }

                @Override
                public void fail(String info) {
                    // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
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
                    imageView= (ImageView) findViewById(R.id.back);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if(brlb==null){
                                startActivity(new Intent(BrxxActivity.this,HomePageActivity.class));
                            }else{
                                MyApplication.getInstance().setOther_brlb(null);
                                startActivity(new Intent(BrxxActivity.this,BrlbActivity.class));
                            }

                        }
                    });
                    if(brlb.getXINGBIE().equals("男")){
                        tx.setImageResource(R.drawable.icon_men);
                    }else{
                        tx.setImageResource(R.drawable.icon_women);
                    }
                    xm.setText(brlb.getXINGMING());
                    ll.setText(brlb.getNIANLING()+"岁");
                    dh.setText(brlb.getLIANXIFS());
                    ch.setText(brlb.getCHUANGWEIHAO()+"床");
                    hl.setText(brlb.getHULIDJ());
                    ks.setText(brlb.getKESHIDM());
                    zz.setText(brlb.getZHUZHIYS());
                    gm.setText(brlb.getGUOMINSHI());
                    bq.setText(brlb.getZHENDUAN());
                    xf.setText(brlb.getYIXIAOFEI());
                    ry.setText("入院时间"+brlb.getRUYUANSJ());
                    MyApplication.getInstance().setOther_brlb(null);
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
}
