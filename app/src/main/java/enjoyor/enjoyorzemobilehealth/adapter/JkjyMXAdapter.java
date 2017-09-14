package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.RyjdActivity;
import enjoyor.enjoyorzemobilehealth.entities.KMMX;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;

/**
 * Created by Administrator on 2017/7/25.
 */

public class JkjyMXAdapter extends BaseAdapter {
    private final Context context;
    private final List<KMMX> data;
    public static int flag=-1;
    public static String itemId="";
    private final List<KMMX> listNomal;
    private String item_id;


    public JkjyMXAdapter(Context context, List<KMMX> data,List<KMMX> listNomal){
        this.context = context;
        this.data = data;
        this.listNomal = listNomal;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder= null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_jkjy_xmmx,null);
            vHolder=new ViewHolder();
            vHolder.xmmx = (TextView) convertView.findViewById(R.id.xmmx);
            vHolder.select_xm = (ImageView) convertView.findViewById(R.id.select_xm) ;
            vHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item) ;
            convertView.setTag(vHolder);
        }else{
            vHolder = (ViewHolder) convertView.getTag();
        }
        final KMMX kmmx=data.get(position);
        for(int i=0;i<RyjdActivity.listKMMX.size();i++){
            if(RyjdActivity.listKMMX.get(i).getITEM_ID().equals(kmmx.getITEM_ID())){
                if(RyjdActivity.listKMMX.get(i).isChecked()){
                    vHolder.select_xm.setImageResource(R.drawable.btn_select01_on2x);
                }else{
                    vHolder.select_xm.setImageResource(R.drawable.btn_select01_null2x);
                }
            }
        }

        for(int i=0;i<listNomal.size();i++){
            if(kmmx.getITEM_ID().equals(listNomal.get(i).getITEM_ID())){
                vHolder.select_xm.setImageResource(R.drawable.btn_select01_nomal2x);

            }
        }


        vHolder.xmmx.setText(kmmx.getITEM_NAME());
        vHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               item_id = kmmx.getITEM_ID();
                for(int i=0;i<listNomal.size();i++){
                    if(kmmx.getITEM_ID().equals(listNomal.get(i).getITEM_ID())){
                        return;

                    }
                }
                for(int i=0;i<RyjdActivity.listKMMX.size();i++){
                    if(RyjdActivity.listKMMX.get(i).getITEM_ID().equals(item_id)){
                        if(RyjdActivity.listKMMX.get(i).isChecked()){
                            RyjdActivity.listKMMX.get(i).setChecked(false);
                        }else{
                            RyjdActivity.listKMMX.get(i).setChecked(true);
                        }
                    }
                }
                RyjdActivity.adapter.notifyDataSetChanged();
                //ToastUtils.makeToast(context,position+"---"+ item_id);
            }

        });
        return convertView;
    }
    static class ViewHolder{
        TextView xmmx;
        ImageView select_xm;
        LinearLayout ll_item;
    }

}
