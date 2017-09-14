package enjoyor.enjoyorzemobilehealth.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.JYJG;


/**
 * Created by Administrator on 2017/6/22.
 */

public class JyjgAdapter extends BaseAdapter {
    private Context context;
    private List<JYJG> data;
    public static int mItem = -1;
    public  static List<JYJG> listItem=null;
    public static boolean mbItem = false;
    private List<JYJG> listJYJG = new ArrayList<>();
    public static int mParentItem = -1;
    private ProgressDialog progressDialog;
    public static boolean mbShowChild = false;


    public JyjgAdapter(Context context, List<JYJG> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        System.out.print("helloqqq" + data.size() + "\n");
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ViewHolder vHolder = null;
        Log.d("login34", "进入适配器");
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.jyjg_bgd_item, null);
            vHolder.lx = (TextView) convertView.findViewById(R.id.lx);
            vHolder.jysj = (TextView) convertView.findViewById(R.id.jysj);
            vHolder.listViewItem = (ListView) convertView.findViewById(R.id.bgdxq);
            vHolder.jybt = (LinearLayout) convertView.findViewById(R.id.jybt);
            vHolder.ks=(TextView) convertView.findViewById(R.id.kongxi);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        JYJG jyjg = (JYJG) data.get(position);
        vHolder.lx.setText(jyjg.getJIANYANLX());
        vHolder.jysj.setText(jyjg.getJIANYANSJ());
        if (mParentItem == position && mbShowChild) {
            JyjgxqAdapter adapter = new JyjgxqAdapter(context, listItem);
            vHolder.listViewItem.setAdapter(adapter);
            vHolder.jybt.setVisibility(View.VISIBLE);
            vHolder.listViewItem.setVisibility(View.VISIBLE);
            vHolder.ks.setVisibility(View.VISIBLE);

        } else {
            vHolder.jybt.setVisibility(View.GONE);
            vHolder.listViewItem.setVisibility(View.GONE);
            vHolder.ks.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView lx;
        TextView jysj;
        ListView listViewItem;
        LinearLayout jybt;
        TextView ks;
    }

}
