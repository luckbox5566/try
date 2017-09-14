package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

/**
 * Created by Administrator on 2017/8/15.
 */

public class BloodSugarAdapter extends BaseAdapter{
    private final Context context;
    private final List<List<KongJian>> data;
    private final List<JiChuXiangMuBean> jiChuXiangMuBeanList;

    public BloodSugarAdapter(Context context, List<List<KongJian>> data, List<JiChuXiangMuBean> jiChuXiangMuBeanList){
        this.context=context;
        this.data=data;
        this.jiChuXiangMuBeanList=jiChuXiangMuBeanList;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.bloodsugar_item,null);
            vHolder.listview=(ListView)convertView.findViewById(R.id.kj_listview);
            vHolder.kjmc=(TextView) convertView.findViewById(R.id.kjmc);

            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();

        }
        List<KongJian> listkongjian=data.get(position);
        if(listkongjian.size()==1){
            vHolder.kjmc.setText(listkongjian.get(0).getKongJianMC());
            KongJian kj=listkongjian.get(0);


        }else{
            vHolder.kjmc.setText(listkongjian.get(0).getKongJianMC().substring(0,1));

        }
        BloodSugarKJAdapter adapter =new BloodSugarKJAdapter(context,listkongjian,jiChuXiangMuBeanList);
        vHolder.listview.setAdapter(adapter);


        return convertView;
    }

    static class ViewHolder {
        ListView listview ;
        TextView kjmc;
    }
}
