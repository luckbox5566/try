package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;

/**
 * Created by Administrator on 2017/7/19.
 */


public class BqxhjlAdapter extends BaseAdapter{
    //region Description
    private  Context context;
    //endregion
    //<editor-fold desc="Description">
    private  List<XunHuiJL> data;
    //</editor-fold>
    private LayoutInflater mLayoutInflater;
    public BqxhjlAdapter(Context context,List<XunHuiJL> data){
        this.context = context;
        this.data = data;

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
        ViewHolder vHolder = new ViewHolder();
        if(convertView==null){
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_bqxhcx_item,null);
            vHolder.xhcz=(TextView)convertView.findViewById(R.id.xhcz);
            vHolder.xhhs=(TextView)convertView.findViewById(R.id.xhhs);
            vHolder.xhsj=(TextView)convertView.findViewById(R.id.xhsj);
            vHolder.xm=(TextView)convertView.findViewById(R.id.xm);
            vHolder.ch=(TextView)convertView.findViewById(R.id.ch);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        MyApplication instance =MyApplication.getInstance();
        String chuangweihao="";
        XunHuiJL xhjl=(XunHuiJL) data.get(position);
        for(int i=0;i<instance.getListBRLB().size();i++){
            if(xhjl.getBingRenZYID().equals(instance.getListBRLB().get(i).getBINGRENZYID())){
                chuangweihao=instance.getListBRLB().get(i).getCHUANGWEIHAO();
                break;
            }
        }
        vHolder.xhcz.setText(xhjl.getXunHuiCZ());
        vHolder.xhhs.setText(xhjl.getXunHuiRen());
        vHolder.xhsj.setText(xhjl.getXunHuiSJ());
        vHolder.xm.setText(xhjl.getXingMing());
        vHolder.ch.setText(chuangweihao+"床");
        return convertView;
    }
    static class  ViewHolder{
        TextView xhcz;
        TextView xhhs;
        TextView xhsj;
        TextView xm;
        TextView ch;
    }
}
