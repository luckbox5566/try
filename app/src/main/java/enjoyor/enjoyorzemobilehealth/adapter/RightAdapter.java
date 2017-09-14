package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.AllWXYS;
import enjoyor.enjoyorzemobilehealth.entities.FoodData;
import enjoyor.enjoyorzemobilehealth.views.CenterDialog;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class RightAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<FoodData> foodDatas;
    private List<AllWXYS> list;


    public RightAdapter(List<AllWXYS> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public RightAdapter(Context context, List<FoodData> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    public RightAdapter(Context context, List<FoodData> foodDatas, List<AllWXYS> list) {
        this.context = context;
        this.foodDatas = foodDatas;
        this.list = list;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
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
    public View getView(final int arg0, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_right, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        final FoodData bean = foodDatas.get(arg0);
        final AllWXYS allwxys = list.get(arg0);

//        holder.tv_biaoti.setText(bean.getTitle());//标题

        holder.item_fenshu.setText(allwxys.getZPF());
        holder.item_fengxian.setText(allwxys.getJB());
        holder.tv_pg_date.setText(allwxys.getPGSJ());
        holder.tv_pingguren.setText("评估人：" + allwxys.getPGHS());
        String hlcs = allwxys.getHLCS();
        if (hlcs.equals("")){
            holder.tv_content.setText("暂无护理措施");
        }else {
            String[] s = hlcs.split("#");
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                str = str.append("\n" + s[i]);
            }
            holder.tv_content.setText(str.toString().trim());
        }

        holder.tvHulicuoshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rl_show.setVisibility(View.VISIBLE);
                holder.hide.setVisibility(View.GONE);
            }
        });
        holder.ivDangerClose.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        holder.rl_show.setVisibility(View.GONE);
                                                        holder.hide.setVisibility(View.VISIBLE);
                                                    }
                                                }
        );
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hulicuoshi:
                break;
            case R.id.iv_danger_close:

                break;
            default:
                break;
        }
    }

    private void colse(FoodData bean, int arg0, ViewHolder holder) {
        if (bean.getChild() == arg0) {

        }
    }

    private void chooseChild(FoodData bean, int arg0, ViewHolder holder) {
        if (bean.getChild() == arg0) {
            holder.rl_show.setVisibility(View.VISIBLE);
            holder.hide.setVisibility(View.GONE);
        }
    }

    public interface OnChildClickListener {
        void onCheckClick(String title, int i);
    }

    private OnChildClickListener mListener;

    public void setChildListener(OnChildClickListener mListener) {
        this.mListener = mListener;
    }

    static class ViewHolder {
        @BindView(R.id.tv_hulicuoshi)
        TextView tvHulicuoshi;
        @BindView(R.id.hide)
        RelativeLayout hide;
        @BindView(R.id.iv_danger_close)
        ImageView ivDangerClose;
        @BindView(R.id.rl_show)
        LinearLayout rl_show;
        @BindView(R.id.item_fenshu)
        TextView item_fenshu;
        @BindView(R.id.item_fengxian)
        TextView item_fengxian;
        @BindView(R.id.tv_pg_date)
        TextView tv_pg_date;
        @BindView(R.id.tv_pingguren)
        TextView tv_pingguren;
        @BindView(R.id.tv_content)
        TextView tv_content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
