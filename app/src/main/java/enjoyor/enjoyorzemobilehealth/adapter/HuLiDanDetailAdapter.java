package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.HuLiJiLu;

/**
 * Created by Administrator on 2017/7/12.
 */

public class HuLiDanDetailAdapter extends BaseAdapter{
    private Context context;
    private List<HuLiJiLu> huLiJiLuList;
    private LayoutInflater inflater;

    public HuLiDanDetailAdapter(Context context, List<HuLiJiLu> huLiJiLuList) {
        this.context = context;
        this.huLiJiLuList = huLiJiLuList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return huLiJiLuList.size();
    }

    @Override
    public Object getItem(int position) {
        return huLiJiLuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_lv_hulidan_detail,parent,false);
            holder.tvHuLiDanHao= (TextView) convertView.findViewById(R.id.tv_detail_hulidanhao);
            holder.tvCaiJiRenName= (TextView) convertView.findViewById(R.id.tv_detail_name);
            holder.tvCaiJiTime= (TextView) convertView.findViewById(R.id.tv_detail_time);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        HuLiJiLu bean=huLiJiLuList.get(position);
        holder.tvHuLiDanHao.setText(bean.getHuLiJiLuDanHao());
        holder.tvCaiJiRenName.setText(bean.getCaiJiRen());
        holder.tvCaiJiTime.setText(bean.getCaiJiSJ());

        return convertView;
    }
    class ViewHolder{
        TextView tvHuLiDanHao;
        TextView tvCaiJiRenName;
        TextView tvCaiJiTime;
    }
}
