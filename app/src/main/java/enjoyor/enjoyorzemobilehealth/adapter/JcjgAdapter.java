package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.JCJG;

/**
 * Created by Administrator on 2017/7/7.
 */

public class JcjgAdapter extends BaseAdapter {
    private  List<JCJG> data;
    private  Context context;


    public JcjgAdapter(Context context, List<JCJG> data){
        this.context=context;
        this.data=data;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_jcjgcx_item,null);
            vHolder.jcxm=(TextView)convertView.findViewById(R.id.jcxm);
            vHolder.jcsj=(TextView)convertView.findViewById(R.id.jcsj);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        JCJG jcjg=(JCJG)data.get(position);
        Log.d("111111111111111112",jcjg.getJianChaXM()+"   "+jcjg.getZhenDuanJG());
        vHolder.jcxm.setText(jcjg.getJianChaXM());
        vHolder.jcsj.setText(jcjg.getJianChaSJ());
        return convertView;
    }
    static class ViewHolder {
        TextView jcxm ;
        TextView jcsj;
    }
}
