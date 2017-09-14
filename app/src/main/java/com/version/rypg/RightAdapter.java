package com.version.rypg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by dantevsyou on 2017/9/13.
 */

public class RightAdapter  extends BaseAdapter {
    private int num;
    private Context context;
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean> CheckBox=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean> TextBox=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean> Panel=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean> Label=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean> DateTimePicker=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean>();

    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean> checkbox_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean> textbox_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean> panel_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean> label_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean>();
    List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean> datetime_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean>();

    Map<Integer,FlBben> map=new HashMap<>();
    String[] index_s={"001","002","003","004","005","006","007","008","009","010","011","012","013"
            ,"014","015","016","017","018","019","020","021","022","023","024","025","026"};
    private LayoutInflater mInflater=null;

    public RightAdapter(List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean> checkBox, List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean> textBox, List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean> panel, int num, List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean> label, List<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean> dateTimePicker, Context context) {
        CheckBox = checkBox;
        TextBox = textBox;
        Panel = panel;
        this.num = num;
        Label = label;
        DateTimePicker = dateTimePicker;
        this.context = context;
        //fl
        try{
            for(int i=1;i<=num;i++){
                fl(i);
            }
        }catch (Exception e){
            String s=e.toString();
            e.printStackTrace();
        }

       this.mInflater=LayoutInflater.from(context);
       FlBben flBben=map.get(1);
    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建最外面的layout，并指定一些参数
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        convertView =View.inflate(context, R.layout.rypg_bben_item, null);
        LinearLayout fater_layout= (LinearLayout) convertView.findViewById(R.id.father);
        //获得分类
        FlBben flBben=map.get(position+1);
        for(int index=0;index<index_s.length;index++){
            //需要添加的一行
            LinearLayout yh_layout=new LinearLayout(context);
            yh_layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            yh_layout.setOrientation(LinearLayout.HORIZONTAL);
            //yh_layout.setBackgroundColor(Color.BLACK);
            //匹配标签
            String no=index_s[index];
            //label
            for(int i=0;i<flBben.getLabel_fl().size();i++){
                String[] spilt=flBben.getLabel_fl().get(i).getTag().split(":");
                if(no.equals(spilt[2])){
                    TextView textView=new TextView(context);
                    textView.setText(spilt[1]);
                    yh_layout.addView(textView);

                }
            }
            //
            fater_layout.addView(yh_layout);

        }
        String s="";

        return convertView;
    }



    public void fl(int postion){
        //checkbox
        for(int i=0;i<CheckBox.size();i++){
            String[] split=CheckBox.get(i).getTag().split(":");
            if(split.length>=3&&Integer.parseInt(split[0])==postion){
                int index=0;
                if(checkbox_fl.size()>0){
                    for(int j=0;j<checkbox_fl.size();j++){
                        if(checkbox_fl.get(j).getTag().equals(CheckBox.get(i).getTag())){
                           index=1;
                            break;
                        }
                    }
                }

                if(index==0){
                    checkbox_fl.add(CheckBox.get(i));
                }
               
            }
        }
        //textbox
        for(int i=0;i<TextBox.size();i++){
            String[] split=TextBox.get(i).getTag().split(":");
            if(split.length>=3&&Integer.parseInt(split[0])==postion){
                int index=0;
                if(textbox_fl.size()>0){
                    for(int j=0;j<textbox_fl.size();j++){
                        if(textbox_fl.get(j).getTag().equals(TextBox.get(i).getTag())){
                            index=1;
                            break;
                        }
                    }
                }

                if(index==0){
                    textbox_fl.add(TextBox.get(i));
                }
               
            }
        }
        //lable
        for(int i=0;i<Label.size();i++){
            String[] split=Label.get(i).getTag().split(":");
            if(split.length>=3&&Integer.parseInt(split[0])==postion&&Label.get(i).getTag().indexOf("*")==-1){
                int index=0;
                if(label_fl.size()>0){
                    for(int j=0;j<label_fl.size();j++){
                        if(label_fl.get(j).getTag().equals(Label.get(i).getTag())){
                            index=1;
                            break;
                        }
                    }
                }

                if(index==0){
                    label_fl.add(Label.get(i));
                }
                
            }
        }
        //datetime
        for(int i=0;i<DateTimePicker.size();i++){
            String[] split=DateTimePicker.get(i).getTag().split(":");
            if(split.length>=3&&Integer.parseInt(split[0])==postion){
                int index=0;
                if(datetime_fl.size()>0){
                    for(int j=0;j<datetime_fl.size();j++){
                        if(datetime_fl.get(j).getTag().equals(DateTimePicker.get(i).getTag())){
                            index=1;
                            break;
                        }
                    }
                }

                if(index==0){
                    datetime_fl.add(DateTimePicker.get(i));
                }

            }
        }
        FlBben flBben=new FlBben();
        flBben.setCheckbox_fl(checkbox_fl);
        flBben.setTextbox_fl(textbox_fl);
        flBben.setLabel_fl(label_fl);
        flBben.setDatetime_fl(datetime_fl);
        map.put(postion,flBben);
        //clear
        checkbox_fl=null;
        textbox_fl=null;
        label_fl=null;
        datetime_fl=null;
        panel_fl=null;
        checkbox_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.CheckBoxBean>();
        textbox_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.TextBoxBean>();
        panel_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.PanelBean>();
        label_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.LabelBean>();
        datetime_fl=new ArrayList<RypgBben.DSBean.DTBean.DRBean.DCBean.LableDocumentBean.DateTimePickerBean>();
    }
}
