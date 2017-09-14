package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiCZ;
import enjoyor.enjoyorzemobilehealth.views.XunHuiCZDialog;

/**
 * Created by Administrator on 2017/7/18.
 */

public class XunHuiAdapter extends BaseAdapter {
    private Context context;
    private List<XunHuiCZ> data;
    private LayoutInflater mLayoutInflater;
    public XunHuiAdapter(Context context, List<XunHuiCZ> data){
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
        ViewHolder vHolder =new ViewHolder();
        if(convertView==null){
            convertView = mLayoutInflater.from(context).inflate(R.layout.dialog_xhcx_item,null);
            vHolder.xhlx=(TextView)convertView.findViewById(R.id.xhlx);
            vHolder.border =(LinearLayout)convertView.findViewById(R.id.border);
            convertView.setTag(vHolder);
        }else{
            vHolder = (XunHuiAdapter.ViewHolder) convertView.getTag();
        }
        String lx=data.get(0).getLx();
        XunHuiCZ xunhuilx =(XunHuiCZ)data.get(position);
        if(xunhuilx.getXunHuiCZ().equals(lx)){
            vHolder.xhlx.setTextColor(Color.parseColor("#3f90eb"));
            vHolder.border.setBackgroundResource(R.drawable.textview_border_bule);
        }
        vHolder.xhlx.setText(xunhuilx.getXunHuiCZ());
        return convertView;
    }
    static class ViewHolder{
        TextView xhlx;
        LinearLayout border;
    }
}
