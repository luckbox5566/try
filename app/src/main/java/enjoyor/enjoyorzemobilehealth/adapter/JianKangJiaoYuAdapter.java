package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.JiaoYuKM;
import enjoyor.enjoyorzemobilehealth.entities.KMMX;

/**
 * Created by Administrator on 2017/7/25.
 */

public class JianKangJiaoYuAdapter extends BaseAdapter {
    private final Context context;
    private final List<JiaoYuKM> listJiaoYuKM;
    private final List<KMMX> listKMMX;
    private final List<KMMX> listNomal;
    private JkjyMXAdapter adapter;


    List<KMMX> listkmmx=new ArrayList<>();


    public JianKangJiaoYuAdapter(Context context, List<JiaoYuKM> listJiaoYuKM, List<KMMX> listKMMX, List<KMMX> listNomal){
        this.context = context;
        this.listJiaoYuKM = listJiaoYuKM;
        this.listKMMX = listKMMX;
        this.listNomal = listNomal;
    }
    @Override
    public int getCount() {
        return listJiaoYuKM.size();
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
        ViewHolder vHolder=new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_jkjy_item,null);
            vHolder.jkjy_xm = (TextView) convertView.findViewById(R.id.jkjy_xm);
            vHolder.jkjy_xmmx = (ListView) convertView.findViewById(R.id.jkjy_xmmx);
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        JiaoYuKM jykm =(JiaoYuKM)listJiaoYuKM.get(position);
        vHolder.jkjy_xm.setText(jykm.getITEM_SUB_CLASS_NAME());
        listkmmx.clear();
         KMMX kmmx=null;
        for(int i=0;i<listKMMX.size();i++){
            kmmx = new KMMX();
            if(listKMMX.get(i).getITEM_SUB_CLASS_ID().equals(jykm.getITEM_SUB_CLASS_ID())){
                kmmx.setITEM_NAME(listKMMX.get(i).getITEM_NAME());
                kmmx.setITEM_ID(listKMMX.get(i).getITEM_ID());
                listkmmx.add(kmmx);
            }
        }
        adapter =new JkjyMXAdapter(context,listkmmx,listNomal);
        vHolder.jkjy_xmmx.setAdapter(adapter);

        return convertView;
    }
    static class ViewHolder{
        TextView jkjy_xm;
        ListView jkjy_xmmx;

    }


}
