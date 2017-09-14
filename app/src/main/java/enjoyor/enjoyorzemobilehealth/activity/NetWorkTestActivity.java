package enjoyor.enjoyorzemobilehealth.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.handlers.MyXmlHandler;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.entities.CaiDan;
import com.example.my_xml.entities.MyserverTime;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.YongHuXX;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

public class NetWorkTestActivity extends AppCompatActivity {
    ZhierCall zhierCall;
    TextView textView;
    
    BRLB brlb;
    List<CaiDan> caidanList;
    YongHuXX yonghuXX;
    MyserverTime myserverTime;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_network_test);
        textView= (TextView) findViewById(R.id.my_text);

        initData();
        
        zhierCall=(new ZhierCall())
                .setId("1000")
                .setNumber("0300801")
                .setMessage(NetWork.SYSTEM_MESSAGE)
                .setCanshu("1000"+","+"123"+",0,20")
                .setContext(this)
                .setPort(5000)
                .build();

       zhierCall.start(new NetWork.SocketResult() {
           @Override
           public void success(String data) {
              StringXmlParser parser = new StringXmlParser(xmlHandler,
                       new Class[] { BRLB.class, CaiDan.class, YongHuXX.class,
                               MyserverTime.class });
               Log.d("login5",data);
               parser.parse(data);
           }

           @Override
           public void fail(String info) {
              Toast.makeText(NetWorkTestActivity.this,info,Toast.LENGTH_LONG).show();
           }
       });
    }

    private void initData() {
        brlb = new BRLB();
        caidanList = new ArrayList<CaiDan>();
        yonghuXX = new YongHuXX();
        myserverTime = new MyserverTime();
    }

    MyXmlHandler xmlHandler =new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    textView.setText("病人列表:"+brlb.getBINGANHAO()+"\n");
                    textView.append("菜单:"+caidanList.get(0).getCaiDanFL()+"\n");
                    textView.append("用户信息:"+yonghuXX.getBuMenID()+"\n");
                    textView.append("服务时间:"+myserverTime.getDR()+"\n");
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            brlb = (BRLB) msg.obj;
                            break;
                        case 1:
                            caidanList.add((CaiDan) msg.obj);
                            break;
                        case 2:
                            yonghuXX = (YongHuXX) msg.obj;
                            //saveToApp(yonghuXX);
                            break;
                        case 3:
                            myserverTime = (MyserverTime) msg.obj;
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
