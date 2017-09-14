package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;


import enjoyor.enjoyorzemobilehealth.adapter.RightAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.LeftAdapter;
import enjoyor.enjoyorzemobilehealth.entities.FoodData;
import enjoyor.enjoyorzemobilehealth.entities.Label;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import my_network.NetWork;
import my_network.ZhierCall;


public class RypgActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 左侧菜单
     */
    private ListView lv_menu;
    /**
     * 右侧主菜
     */
    private ListView lv_home;
    private TextView tv__home_titile;
    private TextView tv_con;
    private ImageView iv_denger_add;
    private RelativeLayout rl_home_t;

    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private int currentItem;
    /**
     * 数据源
     */
    private List<FoodData> foodDatas;
    private String data[] = {"压疮", "跌倒", "坠床", "自理能力", "非计划先拔管", "疼痛（自评）", "疼痛（成人）", "疼痛（儿童）"};
    private String guize = "评估规则";
    /**
     * 里面存放右边ListView需要显示标题的条目position
     */

    private ArrayList<String> list1=new ArrayList();
    private ArrayList<String> list2=new ArrayList();
    private ArrayList<String> list3=new ArrayList();
    private ArrayList<String> list4=new ArrayList();

    private ArrayList<Integer> showTitle;

    ZhierCall zhierCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rypg);
        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name=preferences2.getString("id","");
        String canshu=preferences2.getString("bqdm","");
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0306401")
                .setMessage(NetWork.GongYong)
                .setCanshu(canshu+"¤"+"RuYuanPGD")
                .setContext(this)
                .setPort(5000)
                .build();

        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
               String sm=data;
               ArrayList<Label> list5=new ArrayList();
               ArrayList<Label> tb_list=new ArrayList<Label>();
               ArrayList<Label> cb_list=new ArrayList<Label>();
               ArrayList<Label> dtp_list=new ArrayList<Label>();
                //修正格式
               String more=data.replaceAll("&lt;","<");
               String more2=more.replaceAll("&gt;",">");

               int i=more2.indexOf("<LableDocument>");
               int j=more2.indexOf("</LableDocument>");
               String more3=more2.substring(i,j+16);
               String more4=more3.substring(more3.length()-20,more3.length());
                int ii=more3.indexOf("<Label>");
                int jj=more3.indexOf("</Label>");
                String more5=more3.substring(ii,jj+8);
                String more6=more3.substring(jj+8,more3.length());
                list1.add(more5);
                String vv=more6;
                if(data!=""){
                    while (!(vv.indexOf("<Label>")==-1)){
                        int x=vv.indexOf("<Label>");
                        int y=vv.indexOf("</Label>");
                        list1.add(vv.substring(x,y+8));
                        vv=vv.substring(y+8,vv.length());
                    }
                    String kk=more3;
                    while(!(kk.indexOf("<TextBox>")==-1)){
                        int x=kk.indexOf("<TextBox>");
                        int y=kk.indexOf("</TextBox>");
                        list2.add(kk.substring(x,y+10));
                        kk=kk.substring(y+10,kk.length());
                    }
                    String oo=more3;
                    while(!(oo.indexOf("<CheckBox>")==-1)){
                        int x=oo.indexOf("<CheckBox>");
                        int y=oo.indexOf("</CheckBox>");
                        list3.add(oo.substring(x,y+11));
                        oo=oo.substring(y+11,oo.length());
                    }
                    String hh=more3;
                    while(!(hh.indexOf("<DateTimePicker>")==-1)){
                        int x=hh.indexOf("<DateTimePicker>");
                        int y=hh.indexOf("</DateTimePicker>");
                        list4.add(hh.substring(x,y+17));
                        hh=hh.substring(y+17,hh.length());
                    }
                }
                int gb=0;
                for(int m=0;m<list1.size();m++){
                    String dd=list1.get(m).substring(list1.get(m).indexOf("<Tag>")+5,list1.get(m).indexOf("</Tag>"));
                    if(!(dd.equals(""))&&(dd.indexOf(":")>=0)){
                        try{
                            String[] s=dd.split(":");
                            if(s.length==1){
                                Label label=new Label();
                                label.setName(s[0]);
                                label.setTag1("[]");
                                label.setTag2("[]");
                                String sq_x=list1.get(m).substring(list1.get(m).indexOf("<X>")+3,list1.get(m).indexOf("</X>"));
                                String sq_y=list1.get(m).substring(list1.get(m).indexOf("<Y>")+3,list1.get(m).indexOf("</Y>"));
                                label.setX(sq_x);
                                label.setY(sq_y);
                                list5.add(label);
                            }else{
                                Label label=new Label();
                                label.setName(s[1]);
                                label.setTag1(s[0]);
                                label.setTag2(s[2]);
                                String sq_x=list1.get(m).substring(list1.get(m).indexOf("<X>")+3,list1.get(m).indexOf("</X>"));
                                String sq_y=list1.get(m).substring(list1.get(m).indexOf("<Y>")+3,list1.get(m).indexOf("</Y>"));
                                label.setX(sq_x);
                                label.setY(sq_y);
                                list5.add(label);
                            }
                        }catch (Exception e){

                        }
                        int np=0;
                            /*Label label=new Label();
                        try {
                            label.setTag1(dd.substring(0,2));
                        }catch (Exception e){
                            label.setTag1("[]");
                        }

                        label.setTag2(dd.substring(dd.length()-3,dd.length()));*/
                    }
                   /* for(int h=0;h<list5.size();h++)
                    {
                        *//*if(Integer.parseInt(label.getTag1())>Integer.parseInt(list5.get(h).getTag1()))
                        {
                            list7.add(0);

                        }*//*

                    }*/
                }
                ArrayList<Label> list6=new ArrayList<Label>();
                for(int v=0;v<list5.size();v++)
                {
                    if(list5.get(v).getName().indexOf("*")!=-1)
                    {
                        list6.add(list5.get(v));
                    }
                }
                ArrayList<Label> list7=new ArrayList<Label>();
                Object[] users=list6.toArray();
                Arrays.sort(users,new MyComparator());
                for(int g=0;g<users.length;g++)
                {
                    list7.add((Label)users[g]);
                }
                String[] ss= new String[list7.size()];
                for(int z=0;z<list7.size();z++){
                    ss[z]=list7.get(z).getName().substring(1);
                }



                init();
                setView();
                setData();
                initLinstener();
                leftAdapter = new LeftAdapter(RypgActivity.this,ss);
                lv_menu.setAdapter(leftAdapter);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });

    }


    private void init() {
        BarUtils.setColor(this, getResources().getColor(R.color.my_bule), 0);

        // TODO Auto-generated method stub
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        tv__home_titile = (TextView) findViewById(R.id.tv__home_titile);
        tv_con = (TextView) findViewById(R.id.tv_con);
        lv_home = (ListView) findViewById(R.id.lv_home);
        iv_denger_add = (ImageView) findViewById(R.id.iv_denger_add);
        rl_home_t = (RelativeLayout) findViewById(R.id.rl_home_t);
    }

    private void setView() {
        foodDatas = new ArrayList<FoodData>();
        for (int i = 0; i < data.length; i++) {
            foodDatas.add(new FoodData(0, data[0] + i, data[0],i));
        }
        for (int i = 0; i < data.length - 1; i++) {
            foodDatas.add(new FoodData(1, "1" + i, data[1],i));
        }
        for (int i = 0; i < data.length - 2; i++) {
            foodDatas.add(new FoodData(2, "2"+ i, data[2],i));
        }
        for (int i = 0; i < data.length - 3; i++) {
            foodDatas.add(new FoodData(3, "3" + i, data[3],i));
        }
        for (int i = 0; i < data.length - 4; i++) {
            foodDatas.add(new FoodData(4, "4" + i, data[4],i));
        }
        for (int i = 0; i < data.length - 3; i++) {
            foodDatas.add(new FoodData(5, "5" + i, data[5],i));
        }
        for (int i = 0; i < 6; i++) {
            foodDatas.add(new FoodData(6, "6" + i, data[6],i));
        }
        for (int i = 0; i < 7; i++) {
            foodDatas.add(new FoodData(7, "7" + i, data[7],i));
        }
        showTitle = new ArrayList<Integer>();
        for (int i = 0; i < foodDatas.size(); i++) {
            if (i == 0) {
                showTitle.add(i);
                System.out.println(i + "dd");
            } else if (!TextUtils.equals(foodDatas.get(i).getTitle(), foodDatas.get(i - 1).getTitle())) {
                showTitle.add(i);
                System.out.println(i + "dd");
            }
        }
    }

    private void setData() {
        // TODO Auto-generated method stub
        tv__home_titile.setText(foodDatas.get(0).getTitle());
        rightAdapter = new RightAdapter(this, foodDatas);
        lv_home.setAdapter(rightAdapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                leftAdapter.setSelectItem(arg2);
                leftAdapter.notifyDataSetInvalidated();
                lv_home.setSelection(showTitle.get(arg2));
                tv__home_titile.setText(data[arg2]);
            }
        });
        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                //System.out.println("onScrollStateChanged" + "   scrollState" + scrollState);
                this.scrollState = scrollState - 1;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                Log.d("jiejie", "onScroll" + "  firstVisibleItem" + firstVisibleItem
                        + "  visibleItemCount" + visibleItemCount + "  totalItemCount" + totalItemCount);
                int current = showTitle.indexOf(firstVisibleItem);
                System.out.println(current + "dd" + firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv__home_titile.setText(data[current]);
                    leftAdapter.setSelectItem(currentItem);
                    leftAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

    private void initLinstener() {
        tv_con.setOnClickListener(this);
        iv_denger_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_con:
//                CenterDialog centerDialog = new CenterDialog(this);
//                centerDialog.show();
                break;
            case R.id.iv_denger_add:
                //Intent intent = new Intent(DangerActivity.this, ZiLiAbilityActivity.class);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }

    class MyComparator implements Comparator{

        public int compare(Object obj1,Object obj2){
            Label l1= (Label) obj1;
            Label l2= (Label) obj2;
            if(Integer.parseInt(l1.getTag1())>Integer.parseInt(l2.getTag1())){
                return 1;
            }else {
                return -1;
            }
        }
    }
}
