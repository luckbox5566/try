package enjoyor.enjoyorzemobilehealth.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.BloodSugarAdapter;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.BloodSugarJL;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;
import enjoyor.enjoyorzemobilehealth.entities.KongJian;
import enjoyor.enjoyorzemobilehealth.entities.MoKuaiFenLei;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;


/**
 * Created by Administrator on 2017/8/9.
 */

public class BloodSugarCheckActivity extends BaseActivity {
    private ZhierCall zhierCall;
    private List<JiChuXiangMuBean> jiChuXiangMuBeanList =new ArrayList<>();
    private List<KongJian> kongJianlist =new ArrayList<>();
    private List<KongJian> listKongJian =new ArrayList<>();
    private List<BloodSugarJL> listBLoodSugarJL =new ArrayList<>();
    private List<BloodSugarJL> listtemp =new ArrayList<>();
    List<List<KongJian>> listBlood = new ArrayList<>();
    private ListView listViewKJ;
    private ImageView iv_bloodsugar_detail;
    private TextView bt_save;
    private TextView sj;
    private ImageView back;
    private ImageView tx;
    private TextView xm;
    private TextView ch;
    private LinearLayout layout;
    private BRLB brlb = null;
    private ProgressDialog progressDialog;
    private String shijian;
    private String lastSJ;
    private static final int REQUEST_CODE = 1; // 请求码
    private String zyid;
    private String bingRenXM;
    private String bingRenXB;
    private String chuangHao;
    private String BingQuDM;
    private String usid;
    public void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.activity_bloodsugarcheck);
        defineData();
        clickData();
        initData();

    }
    private void defineData(){
        listViewKJ = (ListView) findViewById(R.id.kongjian);
        bt_save = (TextView) findViewById(R.id.bt_save);
        sj = (TextView) findViewById(R.id.sj);
        back = (ImageView) findViewById(R.id.back);
        iv_bloodsugar_detail = (ImageView) findViewById(R.id.iv_bloodsugar_detail);
        brlb = MyApplication.getInstance().getOther_brlb();
        layout = (LinearLayout) findViewById(R.id.top);
        tx = (ImageView) findViewById(R.id.tx);
        xm = (TextView) findViewById(R.id.mz);
        ch = (TextView) findViewById(R.id.ch);
    }
    private void clickData(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("which", "BloodSugar");
                MyApplication.getInstance().setOther_brlb(null);
                Intent intent = new Intent(BloodSugarCheckActivity.this, BrlbActivity.class);
                intent.putExtra("which", "BloodSugar");
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(BloodSugarCheckActivity.this,HomePageActivity.class));
                finish();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterDialog centerDialog = new CenterDialog(BloodSugarCheckActivity.this, R.layout.dialog_commit, new int[]{R.id.bt_yes, R.id.bt_no});
                centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CenterDialog dialog, View view) {
                        if (view.getId() == R.id.bt_yes) {
                            netWorkUpdata(jiChuXiangMuBeanList);
                        }

                    }
                });
                centerDialog.show();


            }
        });
        iv_bloodsugar_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(BloodSugarCheckActivity.this,BloodSugarDetailActivity.class);
                detailIntent.putExtra("listBLoodSugarJL", (Serializable) listBLoodSugarJL);
//                startActivity(detailIntent);
                startActivityForResult(detailIntent, REQUEST_CODE);
            }
        });
    }
    private void initData(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();


        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/dd");
        Calendar c = Calendar.getInstance();
       shijian = format.format(c.getTime());
        c.add(Calendar.MONTH, -1);
       lastSJ= format.format(c.getTime());
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        BingQuDM=preferences2.getString("bqdm","");
        usid=preferences2.getString("id","");
        if(brlb==null){
            MyApplication instance = MyApplication.getInstance();
            zyid = instance.getListBRLB().get(0).getBINGRENZYID();
            bingRenXM =instance.getListBRLB().get(0).getXINGMING();
            bingRenXB =instance.getListBRLB().get(0).getXINGBIE();
            chuangHao =instance.getListBRLB().get(0).getCHUANGWEIHAO();
        }else{
            Intent intent =getIntent();
            zyid=intent.getStringExtra("BINGRENZYID");
            chuangHao=intent.getStringExtra("CHUANGHAO");
            bingRenXM=intent.getStringExtra("XINGMING");
            bingRenXB=intent.getStringExtra("XINGBIE");
            MyApplication.getInstance().setOther_brlb(null);

        }
        sj.setText(shijian);
        if (bingRenXB.equals("男")) {
            tx.setImageResource(R.drawable.icon_men);
        } else {
            tx.setImageResource(R.drawable.icon_women);
        }
        xm.setText(bingRenXM);
        ch.setText(chuangHao + "床");
        commitData();

    }
    private void commitData(){
        listBLoodSugarJL.clear();
        kongJianlist.clear();
        listKongJian.clear();
        String canshu=BingQuDM+"¤"+zyid+"¤"+lastSJ+"¤"+shijian;
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0600902")
                .setMessage(NetWork.SMTZ)
                .setCanshu(canshu)
                .setContext(BloodSugarCheckActivity.this)
                .setPort(5000)
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Log.d("zzzz3",data);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BloodSugarJL.class,MoKuaiFenLei.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                Toast.makeText(BloodSugarCheckActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String cjsj=data.getStringExtra("cjsj");

                //设置采集时间
                sj.setText(cjsj);
                shijian=cjsj;
                //重新加载数据，不需要加载方法
                setAllPageData();
                BloodSugarAdapter adapter = new BloodSugarAdapter(BloodSugarCheckActivity.this,listBlood,jiChuXiangMuBeanList);
                listViewKJ.setAdapter(adapter);
            }
        }
    }
    //根据时间来获取控件对应的值
    private void getData(){
        int flag=0;
        for(int i=0;i<listBLoodSugarJL.size();i++){
            BloodSugarJL bsjl = listBLoodSugarJL.get(i);
            if(bsjl.getDate().equals(shijian)){
                getFields(bsjl);
                flag=1;
            }
        }
        if(flag==1){
            for(int i=0;i<jiChuXiangMuBeanList.size();i++){
                JiChuXiangMuBean bean=jiChuXiangMuBeanList.get(i);
                for(int j=0;j<listtemp.size();j++){
                    if(listtemp.get(j).getName().equals("XT"+bean.getJiChuXiangMuID())){
                        bean.setShuZhi(listtemp.get(j).getValue());
                    }
                }
            }
        }
    }
    //加载数据
     private void setAllPageData() {
         jiChuXiangMuBeanList.clear();
         for(int i=0;i<listKongJian.size();i++){
             KongJian kongjian=listKongJian.get(i);
             JiChuXiangMuBean bean=new JiChuXiangMuBean();
             bean.setHuLiJiLuID("");
             bean.setHuLiJiLuDanHao("");
             bean.setJiChuXiangMuID(kongjian.getJiChuXiangMuID());
             bean.setJiChuXiangMuMC(kongjian.getKongJianMC());
             bean.setShuZhi("");
             bean.setJiLuSJ("");
             bean.setDanWei("");
             bean.setCaiJiRen("");
             bean.setCaiJiRQ("");
             bean.setCaiJiSJ("");
             bean.setBingQuID("");
             bean.setBingRenZYID("");
             bean.setBingRenXM("");
             bean.setChuangHao("");
             bean.setCaiJiRenID("");
             bean.setHuanYeBZ("0");
             jiChuXiangMuBeanList.add(bean);

         }
         getData();
   }
    /**
     * 接收从基础项目的adapter传递过来的数据
     * @param mList
     */
    public void getJiChuXiangMuData(List<JiChuXiangMuBean> mList){
        this.jiChuXiangMuBeanList=mList;
    }
    //保存数据
    private void netWorkUpdata(List<JiChuXiangMuBean> listSaveData){

        String xml=null;
        try {
            xml=createXml(listSaveData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = MyApplication.getInstance().getYhxm();
        StringBuilder s=new StringBuilder();
        String str=usid+"¤"+zyid+"¤"+bingRenXM+"¤"+BingQuDM+"¤";
        s.append(str);
        s.append(xml);
        Log.d("----------",s.toString());
        zhierCall = (new ZhierCall())
                .setId(usid)
                .setNumber("0600901")
                .setMessage(NetWork.SMTZ)
                .setCanshu(s.toString())
                .setContext(BloodSugarCheckActivity.this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                Toast.makeText(BloodSugarCheckActivity.this,"成功", Toast.LENGTH_LONG).show();
                commitData();
            }

            @Override
            public void fail(String info) {
                Toast.makeText(BloodSugarCheckActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    //获取保存xml
    public String createXml( List<JiChuXiangMuBean> list) throws Exception{
        Date currentTime =new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyy/MM/dd");
        String date = formatter.format(currentTime);
        StringBuilder s=new StringBuilder();
        //1.头部

        s.append("<?xml version=\"1.0\" encoding=\"utf-16\"?>"+"\r\n");
        s.append("<DS Name=\"33813864\" Num=\"1\">"+"\r\n");
        s.append("<DT Name=\"tab_CaoZuo\" Num=\""+list.size()+"\">"+"\r\n");

        //2.中部
        for(int i=0 ;i<list.size();i++){
            JiChuXiangMuBean bean= list.get(i);
            s.append("<DR Name=\"44191144\" Num=\"17\">"+"\r\n");
            s.append("<DC Name=\"ShengMingTZID\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"JiChuXiangMuID\" Num=\"0\">"+bean.getJiChuXiangMuID()+"</DC>"+"\r\n");
            s.append("<DC Name=\"KongJianMC\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"ShuZhi1\" Num=\"0\">"+bean.getShuZhi()+"</DC>"+"\r\n");
            s.append("<DC Name=\"ShuZhi2\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"ShuZhiDW\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"JiLuSJ\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"CaiJiRen\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"CaiJiRQ\" Num=\"0\">"+shijian+"</DC>"+"\r\n");
            s.append("<DC Name=\"CaiJiSJ\" Num=\"0\">"+shijian+"</DC>"+"\r\n");
            s.append("<DC Name=\"XiuGaiRen\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"XiuGaiSJ\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"XiuGaiBZ\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"BingQuID\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"BingRenZYID\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"BingRenXM\" Num=\"0\"/>"+"\r\n");
            s.append("<DC Name=\"PanDuanBZ\" Num=\"0\">"+bean.getPanDuanBZ()+"</DC>"+"\r\n");
            s.append("</DR>"+"\r\n");
        }

        //3.尾部
        s.append("</DT>"+"\r\n");
        s.append("</DS>");

        return s.toString();
    }

    //通过反射获取属性字段名、值
    private  void getFields(Object object) {
        listtemp.clear();

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            BloodSugarJL temp=new BloodSugarJL();
            try{
                temp.setName(field.getName());
                temp.setValue(String.valueOf(field.get(object)));
                listtemp.add(temp);
            }catch(Exception e){

            }

        }

    }
    MyXmlHandler xmlHandler=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:


                    zhierCall = (new ZhierCall())
                            .setId(usid)
                            .setNumber("0600202")
                            .setMessage(NetWork.SMTZ)
                            .setCanshu(BingQuDM)
                            .setContext(BloodSugarCheckActivity.this)
                            .setPort(5000)
                            .build();

                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            Log.d("zzzz4",data);
                            StringXmlParser parser = new StringXmlParser(xmlHandler1,
                                    new Class[]{MoKuaiFenLei.class,KongJian.class,KongJian.class});
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {
                            Toast.makeText(BloodSugarCheckActivity.this, info, Toast.LENGTH_LONG).show();
                        }
                    });

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBLoodSugarJL.add((BloodSugarJL) msg.obj);
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
    MyXmlHandler xmlHandler1=new MyXmlHandler(this,this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    progressDialog.dismiss();
                    for(int i=0;i<kongJianlist.size();i++){
                        KongJian kj=kongJianlist.get(i);
                        if(kj.getMoKuaiFLID().equals("13")){
                            listKongJian.add(kj);
                        }
                    }
                    setAllPageData();
                    List<KongJian> li =listKongJian;
                    Map map = new HashMap<String,String>();

                    listBlood.clear();
                    List<Integer> liststr=new ArrayList<>();
                    for(int i=0;i<li.size();i++){
                        if(map.containsKey(li.get(i).getKongJianMC().substring(0,1))){
                            List<KongJian> list =new ArrayList<>();

                            for(int j=0;j<listKongJian.size();j++){

                                if(listKongJian.get(j).getKongJianMC().substring(0,1).equals(listKongJian.get(i).getKongJianMC().substring(0,1))){
                                    list.add(listKongJian.get(j));
                                    liststr.add(j);
                                }
                            }
                            listBlood.add(list);
                        }
                        else{
                            map.put(li.get(i).getKongJianMC().substring(0,1),"");

                        }
                    }
                    for(int i= 0;i<listKongJian.size();i++){
                        boolean flag =true;
                        for(int j=0 ;j<liststr.size();j++){
                            if(liststr.get(j)==i){
                                flag=false;
                            }
                        }
                        if(flag){
                            List<KongJian> list1 =new ArrayList<>();
                            list1.add(listKongJian.get(i));
                            listBlood.add(list1);
                        }
                    }
                    BloodSugarAdapter adapter = new BloodSugarAdapter(BloodSugarCheckActivity.this,listBlood,jiChuXiangMuBeanList);
                    listViewKJ.setAdapter(adapter);

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            break;
                        case 1:
                            kongJianlist.add((KongJian) msg.obj);
                            Log.d("kongjianid-----", String.valueOf(kongJianlist.size()));
                            break;
                        case 2:
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
