package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.TiWen;

/**
 * Created by Administrator on 2017/6/14.
 */

public class TemperatureDetailAdapter extends BaseAdapter{
    private Context context;
    private List<TiWen> mTiWenList;
    private LayoutInflater inflater;

    public TemperatureDetailAdapter(Context context, List<TiWen> mTiWenList) {
        this.context = context;
        this.mTiWenList = mTiWenList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTiWenList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTiWenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_temperature_detail,null);
            holder.tvRQ= (TextView) convertView.findViewById(R.id.tv_temper_detail_rq);
            holder.tvSJ= (TextView) convertView.findViewById(R.id.tv_temper_detail_sj);
            holder.ivShow= (ImageView) convertView.findViewById(R.id.iv_temper_detail_show);
            holder.tvTemperatureValue= (TextView) convertView.findViewById(R.id.tv_temper_detail_value);
            holder.line=convertView.findViewById(R.id.vertical_line);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        TiWen tiWen=mTiWenList.get(position);
        holder.tvRQ.setText(tiWen.getCaiJiRQ());
        holder.tvSJ.setText(tiWen.getCaiJiSJ());
        holder.tvTemperatureValue.setText(tiWen.getTiWen());
        float temp=Float.parseFloat(tiWen.getTiWen());
        if(temp<37.7){
            holder.ivShow.setImageResource(R.drawable.temperature_nomal);
        }else if(temp>=37.7&&temp<39.0){
            holder.ivShow.setImageResource(R.drawable.temperature_low);
        }else {
            holder.ivShow.setImageResource(R.drawable.temperature_hot);
        }
        if(position ==mTiWenList.size()-1){
            holder.line.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvRQ;
        TextView tvSJ;
        ImageView ivShow;
        TextView tvTemperatureValue;
        View line;
    }
}
