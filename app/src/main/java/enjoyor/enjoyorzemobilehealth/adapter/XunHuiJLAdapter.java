package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiJL;

/**
 * Created by Administrator on 2017/7/19.
 */

public class XunHuiJLAdapter extends BaseAdapter {
    private  Context context;
    private  List<XunHuiJL> data;
    private LayoutInflater mLayoutInflater;

    public XunHuiJLAdapter(Context context, List<XunHuiJL> data){
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
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_xhcx_item,null);
            vHolder.xhcz=(TextView)convertView.findViewById(R.id.xhcz);
            vHolder.xhhs=(TextView)convertView.findViewById(R.id.xhhs);
            vHolder.xhsj=(TextView)convertView.findViewById(R.id.xhsj);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        XunHuiJL xhjl=(XunHuiJL) data.get(position);
        vHolder.xhcz.setText(xhjl.getXunHuiCZ());
        vHolder.xhhs.setText(xhjl.getXunHuiRen());
        vHolder.xhsj.setText(xhjl.getXunHuiSJ());
        return convertView;
    }
    static class  ViewHolder{
        TextView xhcz;
        TextView xhhs;
        TextView xhsj;
    }
}
