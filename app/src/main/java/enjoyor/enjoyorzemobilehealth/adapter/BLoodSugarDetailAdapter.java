package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.BloodSugarJL;

/**
 * Created by Administrator on 2017-08-22.
 */

public class BLoodSugarDetailAdapter extends BaseAdapter{
    private final Context context;
    private final List<BloodSugarJL> data;
    private final LayoutInflater inflater;

    public BLoodSugarDetailAdapter(Context context, List<BloodSugarJL> data){
        this.context=context;
        this.data=data;
        inflater= LayoutInflater.from(context);

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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.bloodsugar_detail_item,parent,false);

            holder.tvDate= (TextView) convertView.findViewById(R.id.tv_detail_date);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        BloodSugarJL bean=data.get(position);
        holder.tvDate.setText(bean.getDate());
        return convertView;
    }
    class ViewHolder{
        TextView tvDate;
    }
}
