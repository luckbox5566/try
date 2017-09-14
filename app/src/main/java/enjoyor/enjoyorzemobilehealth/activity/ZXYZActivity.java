package enjoyor.enjoyorzemobilehealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_xml.entities.BRLB;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.utlis.ZhuanHuanTool;
import my_network.NetWork;
import my_network.ZhierCall;

public class ZXYZActivity extends BaseActivity {
    ZhierCall zhierCall;
    private String time1="2017-04-20 00:00:00";
    private String time2="2017-04-20 23:59:59";
    private String ll;
    ProgressDialog progressDialog;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxyz);

        Intent intent=getIntent();
        String no1=intent.getStringExtra("no1");
        String no2=intent.getStringExtra("no2");

        TextView textView1= (TextView) findViewById(R.id.ff1);
        TextView textView2= (TextView) findViewById(R.id.ff2);
        textView1.setText(no1);
        textView2.setText(no2);

        ImageView imageView1= (ImageView) findViewById(R.id.cc1);
        ImageView imageView2= (ImageView) findViewById(R.id.cc2);
        ImageView imageView3= (ImageView) findViewById(R.id.cc3);
        ImageView imageView4= (ImageView) findViewById(R.id.cc4);
        ImageView imageView5= (ImageView) findViewById(R.id.cc5);
        ImageView imageView6= (ImageView) findViewById(R.id.cc6);

        ArrayList<ImageView> list=new ArrayList<>();
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        list.add(imageView4);
        list.add(imageView5);
        list.add(imageView6);
        /*imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("xxxxxxxxxxxxxxxxxxxxxxx");
                Toast.makeText(ZXYZActivity.this,"xxxxx",Toast.LENGTH_LONG).show();
            }
        });*/
        ImageView imageView= (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ZXYZActivity.this,BryzActivity.class));
                finish();
            }
        });
       for(int i=0;i<6;i++){
            final int finalI = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  progressDialog = new ProgressDialog(ZXYZActivity.this);
                  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                  progressDialog.setMessage("加载中...");
                  progressDialog.show();
                 String s= MyApplication.getInstance().getListZXYZ();

                  final int d= finalI;
                  String x= ZhuanHuanTool.toString1(d);
                  int hh=0;
                  String gg="";
                  switch (d){
                      case 0:
                          hh=6;
                          gg="开始";
                          break;
                      case 1:
                          hh=3;
                          gg="暂停";
                          break;
                      case 2:
                          hh=5;
                          gg="继续";
                          break;
                      case 3:
                          hh=1;
                          gg="结束";
                          break;
                      case 4:
                          hh=2;
                          gg="异常中断";
                          break;
                      case 5:
                          hh=4;
                          gg="停用";
                          break;
                  }
                  System.out.print("xxxxxxxxxxxxxxxxxxxxxxx");
                  String cc=s.replaceAll("<DC Name=\"YiZhuZT\" Num=\"0\">"+MyApplication.getInstance().getChangeYIZHU()+"</DC>","<DC Name=\"YiZhuZT\" Num=\"0\">"+hh+"</DC>");
                  String ff=cc.replaceAll("ThreeMills",gg);
                  //Toast.makeText(ZXYZActivity.this,ff,Toast.LENGTH_LONG).show();
                  List<BRLB> listBRLB=new ArrayList<>();
                  listBRLB=MyApplication.getInstance().getListBRLB();
                  final String ll=listBRLB.get(MyApplication.getInstance().getChoosebr()).getBINGRENZYID();
                 zhierCall = (new ZhierCall())
                          .setId("1000")
                          .setNumber("0400902")
                          .setMessage(NetWork.YIZHU_ZHIXING)
                          .setCanshu(ff)
                          .setContext(ZXYZActivity.this)
                          .setPort(5000)
                          .build();
                  zhierCall.start(new NetWork.SocketResult() {
                      @Override
                      public void success(String data) {
                          Timer timer = new Timer();
                          TimerTask tast = new TimerTask() {
                              @Override
                              public void run() {
                                  //startActivity(localIntent);
                              }
                          };
                          timer.schedule(tast, 5500);

                          progressDialog.dismiss();
                          Intent intent1=new Intent(ZXYZActivity.this,BryzActivity.class);
                          intent1.putExtra("hostory","11");
                          //startActivity(intent1);
                          finish();
                      }

                      @Override
                      public void fail(String info) {
                          progressDialog.dismiss();
                      }
                  });
              }
          });
        }


    }
}
