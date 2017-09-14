package com.version.rypg;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
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
    private int fisrt_time=0;
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
        return num;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Map<String,String> textview_map=new HashMap<>();
    Map<String,String> radiogroup_map=new HashMap<>();
    Map<String,String> checkbox_map=new HashMap<>();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //创建最外面的layout，并指定一些参数
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        convertView =View.inflate(context, R.layout.rypg_bben_item, null);
        LinearLayout fater_layout= (LinearLayout) convertView.findViewById(R.id.father);
        //获得分类
        FlBben flBben=map.get(position+1);
        int ryrq_index=0;
        for(int index=0;index<index_s.length;index++){
            //需要添加的一行
            LinearLayout yh_layout=new LinearLayout(context);
            yh_layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            yh_layout.setOrientation(LinearLayout.HORIZONTAL);
            //yh_layout.setBackgroundColor(Color.BLACK);
            //匹配标签
            String no=index_s[index];
            //label
            int times=0;
            for(int i=0;i<flBben.getLabel_fl().size();i++){
                String[] spilt=flBben.getLabel_fl().get(i).getTag().split(":");
                if(no.equals(spilt[2])){
                    if (times==0&&position!=2){
                        TextView textView=new TextView(context);
                        textView.setText(spilt[1]+":");
                        yh_layout.addView(textView);
                        times++;
                    }else if(times==0&&position==2){
                        TextView textView=new TextView(context);
                        textView.setText(spilt[1]);
                        yh_layout.addView(textView);
                        times++;
                    }
                    else if(times!=0&&position==2)
                    {
                        TextView textView=new TextView(context);
                        textView.setText("("+spilt[1]+"):");
                        yh_layout.addView(textView);
                    }

                }
                if(position==0&&no.equals("001")&&index==0&&ryrq_index==0){
                    TextView textView=new TextView(context);
                    textView.setText("入院日期:");
                    yh_layout.addView(textView);
                    ryrq_index=1;
                }
            }
            //checkbox
            ArrayList<String> s_list=new ArrayList<>();
            String key3="";
            for(int i=0;i<flBben.getCheckbox_fl().size();i++){
                String[] spilt=flBben.getCheckbox_fl().get(i).getTag().split(":");

                if(no.equals(spilt[2])){
                    s_list.add(spilt[1]);
                    key3=key3+flBben.getCheckbox_fl().get(i).getTag();
                }
            }
            final String key2=key3;
            String[] items=new String[s_list.size()];
            for(int j=0;j<s_list.size();j++){
                items[j]=s_list.get(j);
            }
            if(s_list.size()>0&&position!=9){
//                Spinner spinner=new Spinner(context);
//                spinner.setBackground(null);
//                spinner.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,items));
//                yh_layout.addView(spinner);
                yh_layout.setOrientation(LinearLayout.VERTICAL);
               final RadioGroup radioGroup=new RadioGroup(context);
                radioGroup.setOrientation(LinearLayout.VERTICAL);
                for(int radio=0;radio<items.length;radio++){
                    RadioButton radioButton=new RadioButton(context);
                    radioButton.setText(items[radio]);
                    radioGroup.addView(radioButton);
                }
                if(fisrt_time==0){
                RadioButton radioButton2=(RadioButton)radioGroup.getChildAt(0);
                radioButton2.setChecked(true);

                }



                yh_layout.addView(radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton= (RadioButton) group.getChildAt(checkedId-1);
                        for(int i=0;i<radioGroup.getChildCount();i++){
                            if(checkedId==radioGroup.getChildAt(i).getId()){
                                int h=i+1;
                                radiogroup_map.put(key2,h+"");
                                break;
                            }
                        }


                    }
                });


                if(radiogroup_map.get(key2)!=null){
                    //radioGroup.check(Integer.parseInt(radiogroup_map.get(key2)));
                    for(int i=0;i<radioGroup.getChildCount();i++){
                        RadioButton radioButton=(RadioButton)radioGroup.getChildAt(i);
                        if((Integer.parseInt(radiogroup_map.get(key2))-1)==i){
                            radioButton.setChecked(true);
                            break;
                        }

                    }
                }

            }else if(s_list.size()>0&&position==9){
                android.widget.CheckBox checkBox=new CheckBox(context);
                checkBox.setText(s_list.get(0));
                if(checkbox_map.get(key2)!=null){
                    checkBox.setChecked(true);
                }
                yh_layout.addView(checkBox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkbox_map.put(key2,"1");
                    }
                });
            }
            //textview
            for(int i=0;i<flBben.getTextbox_fl().size();i++){
                String[] spilt=flBben.getTextbox_fl().get(i).getTag().split(":");
                final String key=flBben.getTextbox_fl().get(i).getTag();
                if(no.equals(spilt[2])){
                    EditText editText=new EditText(context);
                    editText.setLayoutParams(new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));
                    if(textview_map.get(key)!=null){
                        editText.setText(textview_map.get(key));
                    }

                    yh_layout.addView(editText);

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                           textview_map.put(key,s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
            }
            //datetime
            for(int i=0;i<flBben.getDatetime_fl().size();i++){
                String[] spilt=flBben.getDatetime_fl().get(i).getTag().split(":");
                if(no.equals(spilt[2])){

                    final Calendar ca = Calendar.getInstance();
                    final int[] mYear = {ca.get(Calendar.YEAR)};
                    final int[] mMonth = {ca.get(Calendar.MONTH)};
                    final int[] mDay = {ca.get(Calendar.DAY_OF_MONTH)};

                    final TextView textView=new TextView(context);
                    textView.setText(mYear[0] +"年"+ mMonth[0] +"月"+ mDay[0] +"日");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    mYear[0] = year;
                                    mMonth[0] = monthOfYear;
                                    mDay[0] = dayOfMonth;
                                    textView.setText(mYear[0] +"年"+ mMonth[0] +"月"+ mDay[0] +"日");
                                }
                            };

                            DatePickerDialog dialog=new DatePickerDialog(context, mdateListener, mYear[0], mMonth[0], mDay[0]);
                            dialog.show();
                        }
                    });
                   yh_layout.addView(textView);
                }
            }

            fater_layout.addView(yh_layout);

        }
        String s="";
        View view=new View(context);
        view.setBackgroundColor(Color.BLACK);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        fater_layout.addView(view);
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
