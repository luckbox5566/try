package enjoyor.enjoyorzemobilehealth.activity.infosearch;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BaseActivity;
import enjoyor.enjoyorzemobilehealth.adapter.SscxAdapter;
import enjoyor.enjoyorzemobilehealth.entities.SSCX;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * Created by Administrator on 2017/6/27.
 */

public class SsjlcxActivity extends BaseActivity {
    ListView listView;
    SscxAdapter adapter;
    private ImageView back;
    private ZhierCall zhierCall;
    private ProgressDialog progressDialog;
    List<SSCX> listBRLB=new ArrayList<>();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssjlcx);
        listView=(ListView)findViewById(R.id.sslb);
        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SsjlcxActivity.this,XxcxActivity.class));
                finish();
            }
        });
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name=preferences2.getString("id","");
        String canshu=preferences2.getString("bqdm","");
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0401513")
                .setMessage(NetWork.YIZHU_ZHIXING)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("zzzz3",data);
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
                    Log.d("zzzz5","hello");


                    if (listBRLB.size() == 0) {
                        Log.d("zzzz1","1111");
                    }
                    else{
                        adapter=new SscxAdapter(SsjlcxActivity.this,listBRLB);
                        Log.d("login32","准备执行适配器");
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                Intent intent=new Intent(SsjlcxActivity.this,SsjlxqActivity.class);
                                intent.putExtra("xm",listBRLB.get(arg2).getBingRenXM());
                                intent.putExtra("sj",listBRLB.get(arg2).getKaiShiSJ());
                                intent.putExtra("ssmc",listBRLB.get(arg2).getShouSuMC());
                                intent.putExtra("sss",listBRLB.get(arg2).getShouShuShi());
                                intent.putExtra("zd",listBRLB.get(arg2).getShouShuRY());
                                intent.putExtra("mzry",listBRLB.get(arg2).getMaZuiRY());
                                intent.putExtra("sqzd",listBRLB.get(arg2).getShuQianZD());
                                intent.putExtra("shzd",listBRLB.get(arg2).getShuHouZD());
                                intent.putExtra("ssmx",listBRLB.get(arg2).getShouShuMX());
                                intent.putExtra("xb",listBRLB.get(arg2).getXingBie());
                                intent.putExtra("ch",listBRLB.get(arg2).getCHUANGWEIHAO());

                               /* intent5.putExtra("RUYUANSJ",listBRLB.get(arg2).getRUYUANSJ());
                                intent5.putExtra("CHUANGHAO",listBRLB.get(arg2).getCHUANGWEIHAO());
                                intent5.putExtra("XINGMING",listBRLB.get(arg2).getXINGMING());
                                intent5.putExtra("XINGBIE",listBRLB.get(arg2).getXINGBIE());*/

                                startActivity(intent);
                                finish();


                            }
                        });
                        /*
                        List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
                        for(int i=0;i<listBRLB.size();i++){
                            HashMap<String,Object>  item=new HashMap<String,Object>();
                            item.put("xm",listBRLB.get(i).getBingRenXM());
                            item.put("xb",listBRLB.get(i).getXingBie());
                            data.add(item);

                        }
                        Log.d("zzzz1",data.toString());
                        SimpleAdapter simplead = new SimpleAdapter(SsjlcxActivity.this, data,
                            R.layout.activity_ssjlcx_item, new String[] { "xm", "xb" },
                            new int[] {R.id.xm,R.id.xb});


                    listView.setAdapter(simplead);*/

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
