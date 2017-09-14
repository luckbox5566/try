package enjoyor.enjoyorzemobilehealth.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.ShengMingTiZhengLuRuActivity;
import enjoyor.enjoyorzemobilehealth.entities.CanShu;
import enjoyor.enjoyorzemobilehealth.entities.ShengMingTiZhengLuRuBean;
import enjoyor.enjoyorzemobilehealth.views.MyGridView;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ShengMingTiZhengLuRuAdapter extends BaseAdapter {
    private ShengMingTiZhengLuRuActivity context;
    private List<ShengMingTiZhengLuRuBean> mDatas;
    private List<CanShu> mCanShuList;

    public ShengMingTiZhengLuRuAdapter(ShengMingTiZhengLuRuActivity context, List<ShengMingTiZhengLuRuBean> mDatas, List<CanShu> mCanShuList) {
        this.context = context;
        this.mDatas = mDatas;
        this.mCanShuList=mCanShuList;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_smtz_content,null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_title);
            holder.gvContent = (MyGridView) convertView.findViewById(R.id.gv_smtz_lv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(mDatas.get(position).getTitle());

        SMTZLuRuContentAdapter contentAdapter = new SMTZLuRuContentAdapter(context, mDatas.get(position).getItemContent(),mCanShuList);
        holder.gvContent.setAdapter(contentAdapter);
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        MyGridView gvContent;
    }
}
