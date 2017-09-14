package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.JYJG;

/**
 * Created by Administrator on 2017/6/22.
 */

public class JyjgxqAdapter extends BaseAdapter {
    private Context context;
    private List<JYJG> data;
    public JyjgxqAdapter(Context context, List<JYJG> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vHolder = new ViewHolder();
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.jyjg_bgdxq_item,null);
            vHolder.jyxm=(TextView)convertView.findViewById(R.id.jyxm);
            vHolder.jyjg=(TextView)convertView.findViewById(R.id.jyjg);
            vHolder.ckfw=(TextView)convertView.findViewById(R.id.ckfw);

            convertView.setTag(vHolder);

        }else{
            vHolder = (JyjgxqAdapter.ViewHolder) convertView.getTag();
        }
        JYJG jyjg=(JYJG)data.get(position);

        vHolder.jyxm.setText(jyjg.getJIANYANXM());


        vHolder.jyjg.setText(jyjg.getJIANYANJG());
        vHolder.ckfw.setText(jyjg.getCANKAOFW());
        return convertView;
    }
    static class ViewHolder{
        TextView jyxm ;
        TextView jyjg ;
        TextView ckfw ;
    }
}
