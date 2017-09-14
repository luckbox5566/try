package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.BloodSugarCheckActivity;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;
import enjoyor.enjoyorzemobilehealth.entities.KongJian;
import enjoyor.enjoyorzemobilehealth.views.DensityUtil;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.getContext;

/**
 * Created by Administrator on 2017/8/15.
 */

public class BloodSugarKJAdapter extends BaseAdapter{
    private final Context context;
    private final List<KongJian> data;
    private final List<JiChuXiangMuBean> jiChuXiangMuBeanList;
    private List<JiChuXiangMuBean> tempJiChuXiangMuBeanList;

    private BloodSugarCheckActivity activity;
    public BloodSugarKJAdapter(Context context, List<KongJian> data,List<JiChuXiangMuBean> jiChuXiangMuBeanList){
        activity= (BloodSugarCheckActivity) context;
        this.context=context;
        this.data=data;
        this.jiChuXiangMuBeanList =jiChuXiangMuBeanList;
        try {
            //深拷贝
            tempJiChuXiangMuBeanList = deepCopy(jiChuXiangMuBeanList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder =new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_bloodsugar_item,null);
            vHolder.kjmc=(TextView)convertView.findViewById(R.id.kjmc);
            vHolder.et_type_input=(AppCompatEditText)convertView.findViewById(R.id.et_type_input);
            vHolder.layout=(LinearLayout) convertView.findViewById(R.id.top);
            vHolder.et_type_input.setTag(position);
            vHolder.et_type_input.addTextChangedListener(new MyEdittextTextWatcher(vHolder));
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
            vHolder.et_type_input.setTag(position);
        }
        KongJian kj=data.get(position);
        ViewGroup.LayoutParams lp;
        if(data.size()==1){
            lp= vHolder.layout.getLayoutParams();
            lp.height= DensityUtil.dip2px(getContext(),60);
            vHolder.layout.setLayoutParams(lp);
            vHolder.kjmc.setVisibility(View.GONE);
        }else{
            lp= vHolder.layout.getLayoutParams();
            lp.height= DensityUtil.dip2px(getContext(),45);
            vHolder.layout.setLayoutParams(lp);
            vHolder.kjmc.setText(kj.getKongJianMC().substring(1,kj.getKongJianMC().length()));


        }
        for(int i=0;i<jiChuXiangMuBeanList.size();i++){
            if(jiChuXiangMuBeanList.get(i).getJiChuXiangMuID().equals(data.get(position).getJiChuXiangMuID())){
                vHolder.et_type_input.setText(jiChuXiangMuBeanList.get(i).getShuZhi());
            }
        }


        return convertView;
    }
    static class ViewHolder {
        TextView kjmc ;
        AppCompatEditText et_type_input;
        LinearLayout layout;
    }
    private class MyEdittextTextWatcher implements TextWatcher {
        ViewHolder holder = null;
        public MyEdittextTextWatcher(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int position= (int) holder.et_type_input.getTag();
            String text=s.toString();
            //设置光标始终在文字右边
            holder.et_type_input.setSelection(text.length());
            for(int i=0;i<jiChuXiangMuBeanList.size();i++){
                if(jiChuXiangMuBeanList.get(i).getJiChuXiangMuID().equals(data.get(position).getJiChuXiangMuID())){
                    JiChuXiangMuBean bean=jiChuXiangMuBeanList.get(i);
                    JiChuXiangMuBean tempBean=tempJiChuXiangMuBeanList.get(i);
                    if(TextUtils.isEmpty(tempBean.getShuZhi())&&!TextUtils.isEmpty(text)){
                        bean.setShuZhi(text);
                        bean.setPanDuanBZ("1");
                    }else if(!TextUtils.isEmpty(tempBean.getShuZhi())&&!TextUtils.equals(tempBean.getShuZhi(),text)){
                        bean.setShuZhi(text);
                        bean.setPanDuanBZ("0");
                    }else {
                        bean.setShuZhi(text);
                    }
                    //往HuLiDanActivity传递修改后的数据
                    activity.getJiChuXiangMuData(jiChuXiangMuBeanList);
                }
            }


        }
    }
    public List<JiChuXiangMuBean> deepCopy(List<JiChuXiangMuBean> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<JiChuXiangMuBean> dest = (List<JiChuXiangMuBean>) in.readObject();
        return dest;
    }

}
