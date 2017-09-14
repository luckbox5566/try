package com.version.rypg;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.adapter.LeftAdapter;
import enjoyor.enjoyorzemobilehealth.adapter.RightAdapter;
import enjoyor.enjoyorzemobilehealth.entities.CheckBoxBean;
import enjoyor.enjoyorzemobilehealth.entities.FoodData;
import enjoyor.enjoyorzemobilehealth.entities.Label;
import enjoyor.enjoyorzemobilehealth.utlis.BarUtils;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * Created by dantevsyou on 2017/9/13.
 */

public class RypgBbenActivity extends Activity implements View.OnClickListener {
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
    TextView title_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rypg);

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name=preferences2.getString("id","");
        String canshu=preferences2.getString("bqdm","");
        title_textView= (TextView) findViewById(R.id.title);
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
                //修正格式
                String more=data.replaceAll("&lt;","<");
                String more2=more.replaceAll("&gt;",">");
                //Xml转换陈Json
                XmlToJson xmlToJson = new XmlToJson.Builder(more2).build();
                JSONObject jsonObject = xmlToJson.toJson();
                String jsonString = xmlToJson.toString();
                //把Json转换成实体类
                Gson gson = new GsonBuilder().create();
                RypgBben rypgBben = gson.fromJson(jsonString, RypgBben.class);
                RypgBben rypgBben2= JSON.parseObject(jsonString,RypgBben.class );
                List<RypgBben.DSBean.DTBean.DRBean.DCBean> DC=rypgBben2.getDS().getDT().getDR().getDC();
                //解析出来的实体类
                 List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean> CheckBox=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean>();
                 List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean> TextBox=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean>();
                 List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean> Panel=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean>();
                 List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean> Label=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean>();
                 List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean> DateTimePicker=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean>();
                for(RypgBben.DSBean.DTBean.DRBean.DCBean dcBean:DC){
                    RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean lableDocumentBean=dcBean.getLableDocument();
                    if(lableDocumentBean!=null){
                        //checkbox
                        for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean checkBox:lableDocumentBean.getCheckBox()){
                            CheckBox.add(checkBox);
                        }
                        //textbox
                        for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean textBoxBean:lableDocumentBean.getTextBox()){
                            TextBox.add(textBoxBean);
                        }
                        //Panel
                        for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean panelBean:lableDocumentBean.getPanel()){
                            Panel.add(panelBean);
                        }
                        //Label
                        for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean labelBean:lableDocumentBean.getLabel()){
                            Label.add(labelBean);
                        }
                        //DateTime
                        for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean dateTimePickerBean:lableDocumentBean.getDateTimePicker()){
                            DateTimePicker.add(dateTimePickerBean);
                        }
                    }

                }

                ArrayList<String> left_list=new ArrayList<String>();
                ArrayList<String> bj_list=new ArrayList<String>();
                ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean> left_bean_list=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean>();
                for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean labelBean:Label){
                    if(labelBean.getTag().indexOf("*")!=-1){
                        String[] s=labelBean.getTag().split(":");
                        //先添加进去

                        int i=0;
                        for(String bj_s:bj_list){
                            if(bj_s.equals(s[0])){
                                i=1;
                                break;
                            }

                        }
                        bj_list.add(s[0]);
                        if(i==0){
                            String result=s[1].substring(1);
                            for(RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean bj:Label){
                                String[] bj_s=bj.getTag().split(":");
                                if(s[0].equals(bj_s[0])&&!(s[1].equals(bj_s[1]))&&(bj.getTag().indexOf("*")!=-1)){
                                 result=result+bj_s[1].substring(1);
                                }
                            }
                            left_list.add(result);
                        }
                    }
                }

                com.version.rypg.RightAdapter rightAdapter1=new com.version.rypg.RightAdapter(CheckBox,TextBox,Panel,left_list.size(),Label,DateTimePicker,RypgBbenActivity.this);

                init();
                setView();
                //setData();
                initLinstener();
                final String[] left_s;
                   left_s=new String[left_list.size()];
                    for(int i=0;i<left_list.size();i++){
                        left_s[i]=left_list.get(i);
                    }
                    leftAdapter = new LeftAdapter(RypgBbenActivity.this, left_s);
                    lv_menu.setAdapter(leftAdapter);

                lv_home.setAdapter(rightAdapter1);
                lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        leftAdapter = new LeftAdapter(RypgBbenActivity.this,firstVisibleItem, left_s);
                        lv_menu.setAdapter(leftAdapter);
                        lv_menu.setSelection(firstVisibleItem);
                        title_textView.setText(left_s[firstVisibleItem]);
                    }
                });
                lv_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        leftAdapter = new LeftAdapter(RypgBbenActivity.this,position, left_s);
                        lv_menu.setAdapter(leftAdapter);
                        lv_home.setSelection(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                 lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         leftAdapter = new LeftAdapter(RypgBbenActivity.this,position, left_s);
                         lv_menu.setAdapter(leftAdapter);
                         lv_home.setSelection(position);
                         title_textView.setText(left_s[position]);
                     }
                 });

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

    class MyComparator implements Comparator {

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
