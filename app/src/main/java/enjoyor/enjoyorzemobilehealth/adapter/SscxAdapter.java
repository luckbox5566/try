package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.SSCX;



public class SscxAdapter extends BaseAdapter{
    private final Context context;
    private final List<SSCX> data;
    private LayoutInflater mLayoutInflater;
    public SscxAdapter(Context context, List<SSCX> data){
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
            System.out.print("loguin50:"+"\n");
            convertView = mLayoutInflater.from(context).inflate(R.layout.activity_ssjlcx_item,null);
            vHolder.sj=(TextView)convertView.findViewById(R.id.sj);
            vHolder.ssmc=(TextView)convertView.findViewById(R.id.ssmc);
            vHolder.sss=(TextView)convertView.findViewById(R.id.sss);
            vHolder.zd=(TextView)convertView.findViewById(R.id.zd);
            vHolder.br=(TextView)convertView.findViewById(R.id.br);
            vHolder.ch=(TextView)convertView.findViewById(R.id.ch);
            System.out.print("loguin502:"+"\n");

            convertView.setTag(vHolder);
        }else{
            vHolder = (SscxAdapter.ViewHolder) convertView.getTag();
        }
        SSCX sscx= (SSCX)data.get(position);
        String shijan =sscx.getKaiShiSJ().substring(2,sscx.getKaiShiSJ().length()-3);
        vHolder.sj.setText(shijan);
        vHolder.ssmc.setText(sscx.getShouSuMC());
        vHolder.sss.setText(sscx.getShouShuShi());
        vHolder.zd.setText(sscx.getShouShuRY());
        vHolder.br.setText(sscx.getBingRenXM());
        vHolder.ch.setText(sscx.getCHUANGWEIHAO()+"床");
        return convertView;
    }
    static class ViewHolder{
        TextView br;
        TextView sj;
        TextView ssmc;
        TextView sss;
        TextView zd;
        TextView ch;
    }
}
