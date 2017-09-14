package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.UnPlanActivity;
import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.views.CoustomGridView;
import enjoyor.enjoyorzemobilehealth.views.CoustomListView;

/**
 * Created by admin on 2017/8/7.
 */

public class RcyMoreAdapter extends CommonAdapter<CbMoreBean> {

    private LvCbAdapter lvAdapter;
    private GvCbAdapter gvAdapter;
    private List<CbMoreBean> list;
    private String pfxx;

    private String tag;


    public RcyMoreAdapter(Context context, int layoutId, List<CbMoreBean> datas, String tag, String pfxx) {
        super(context, layoutId, datas);
        this.list = datas;
        this.pfxx = pfxx;

        this.tag = tag;
    }

    @Override
    protected void convert(ViewHolder holder, final CbMoreBean bean, int position) {
        holder.setText(R.id.tv_more, bean.getTitle());

        if (bean.getTag().equals("0")) {
            CoustomGridView gridview = holder.getView(R.id.gv_more);
            gvAdapter = new GvCbAdapter(mContext, bean.getCbMoreBean(), R.layout.gv_item_checkbox);
            gridview.setAdapter(gvAdapter);
            gvAdapter.setCheckListener(new GvCbAdapter.OnCheckClickListener() {
                @Override
                public void onCheckClick(String pfxxTag, int sorce, int position) {
                    if (mListener != null) {
                        mListener.onCheckClick(pfxxTag, sorce, position, bean.getTitle());
                    }
                }
            });
            if (tag.equals(Constant.UPDATA)) {
                switch (bean.getType()) {
                    case "yachuang":
                        yachuang();
                        break;
                    case "fallbed":
                        fallbed();
                        break;
                    case "adl":
                        adl();
                        break;
                    case "ziping":
                        ziping();
                        break;
                    default:
                        break;
                }
            }
        } else if (bean.getTag().equals("1")) {
            CoustomListView listview = holder.getView(R.id.lv_more);
            lvAdapter = new LvCbAdapter(mContext, bean.getCbMoreBean(), R.layout.lv_item_checkbox);
            listview.setAdapter(lvAdapter);
            lvAdapter.setCheckListener(new LvCbAdapter.OnCheckClickListener() {
                @Override
                public void onCheckClick(String pfxxTag, int sorce, int position) {
                    if (mListener != null) {
                        mListener.onCheckClick(pfxxTag, sorce, position, bean.getTitle());
                    }
                }
            });
            if (tag.equals(Constant.UPDATA)) {
                switch (bean.getType()) {
                    case "unplan":
                        unplan();
                        break;
                    case "taping":
                        taping();
                        break;

                    default:
                        break;
                }
            }

        }
    }

    //疼痛自评
    private void ziping() {
        try {
            String pf1 = pfxx.substring(0, 33);//17
            String pf2 = pfxx.substring(34, 53);//10
            String pf3 = pfxx.substring(54, 79);//13
            String pf4 = pfxx.substring(80, 83);//2
            String pf5 = pfxx.substring(84, 87);//2
            String pf6 = pfxx.substring(88, 97);//5
            String pf7 = pfxx.substring(98, pfxx.length());//7
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5, pf6, pf7};
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyAdapter--疼痛自评", e.toString());
        }

    }

    //自理能力ADL
    private void adl() {
        try {
            String pf1 = pfxx.substring(0, 5);//3
            String pf2 = pfxx.substring(6, 9);//2
            String pf3 = pfxx.substring(10, 13);//2
            String pf4 = pfxx.substring(14, 19);//3

            String pf5 = pfxx.substring(20, 25);//3
            String pf6 = pfxx.substring(26, 31);//3
            String pf7 = pfxx.substring(32, 37);//3

            String pf8 = pfxx.substring(38, 45);//4
            String pf9 = pfxx.substring(46, 53);//4
            String pf10 = pfxx.substring(54, pfxx.length());//3
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5, pf6, pf7, pf8, pf9, pf10};
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyAdapter--fallbed", e.toString());
        }

    }

    //跌倒坠床
    private void fallbed() {
        try {
            String pf1 = pfxx.substring(0, 3);//2
            String pf2 = pfxx.substring(4, 7);//2
            String pf3 = pfxx.substring(8, 11);//2
            String pf4 = pfxx.substring(12, 15);//2
            String pf5 = pfxx.substring(16, 27);//6
            String pf6 = pfxx.substring(28, pfxx.length());//5
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5, pf6};
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyAdapter--fallbed", e.toString());
        }
    }

    //压疮
    private void yachuang() {
        try {
            String pf1 = pfxx.substring(0, 7);//4
            String pf2 = pfxx.substring(8, 15);//4
            String pf3 = pfxx.substring(16, 23);//4
            String pf4 = pfxx.substring(24, 31);//4
            String pf5 = pfxx.substring(32, 39);//4
            String pf6 = pfxx.substring(40, pfxx.length());//3
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5, pf6};
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyAdapter--yachuang", e.toString());
        }
    }

    private void showPfxx(String[] pf) {
        for (int i = 0; i < list.size(); i++) {
            Log.e("mAvaList.size()", list.size() + "" + i);
            for (int j = 0; j < list.get(i).getCbMoreBean().size(); j++) {
                Log.e("j----", list.get(i).getCbMoreBean().size() + "--" + j);
                String[] split = pf[i].split("#");
                if (split[j].equals("1")) {
                    list.get(i).getCbMoreBean().get(j).setMsg("1");
                }
            }
        }
    }

    //疼痛他评
    private void taping() {
        try {
            String pf1 = pfxx.substring(0, 5);//3
            String pf2 = pfxx.substring(6, 11);//3
            String pf3 = pfxx.substring(12, 17);//3
            String pf4 = pfxx.substring(18, 23);//3
            String pf5 = pfxx.substring(24, pfxx.length());//3
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5};
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyAdapter--severepain", e.toString());
        }
    }

    //未计划先拔管
    private void unplan() {
        try {
            String pf1 = pfxx.substring(0, 3);//2
            String pf2 = pfxx.substring(4, 17);//7
            String pf3 = pfxx.substring(18, 33);//8
            String pf4 = pfxx.substring(34, 37);//2
            String pf5 = pfxx.substring(38, pfxx.length());//2
            String[] pf = new String[]{pf1, pf2, pf3, pf4, pf5};
            Log.e("RcyMoreAdapter--unplan", pf1 + "  " + pf2 + "  " + pf3 + "  " + pf4 + "  " + pf5);
            showPfxx(pf);
        } catch (Exception e) {
            Log.e("RcyMoreAdapter--unplan", e.toString());
        }
    }


    public interface OnCheckClickListener {
        void onCheckClick(String pfxxTag, int sorce, int position, String title);
    }

    private OnCheckClickListener mListener;

    public void setCheckListener(RcyMoreAdapter.OnCheckClickListener mListener) {
        this.mListener = mListener;
    }
}
