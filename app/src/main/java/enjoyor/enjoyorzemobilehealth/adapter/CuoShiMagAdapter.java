package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.base.CommonAdapter;
import enjoyor.enjoyorzemobilehealth.base.ViewHolder;
import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;
import enjoyor.enjoyorzemobilehealth.entities.CheckBoxBean;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;

/**
 * Created by admin on 2017/8/5.
 */

public class CuoShiMagAdapter extends CommonAdapter<CheckBoxBean> {

    private boolean[] state_yachuang = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private boolean[] state_vte = {false, false, false, false, false, false, false, false, false, false, false};
    private boolean[] state_unplan = {false, false};
    private boolean[] state_taping = {false, false, false, false, false, false};
    private boolean[] state_fallbed = {false, false, false, false, false, false, false, false, false, false};
    private final String s1;
    private final String s0;
    private String csxx;

    public CuoShiMagAdapter(Context context, List<CheckBoxBean> mDatas, String tag, int layoutId, String csxx) {
        super(context, mDatas, tag, layoutId);
        this.csxx = csxx;
        String[] split = tag.split("#");
        s0 = split[0];
        s1 = split[1];
        init();
    }

    private void init() {
        if (s1.equals("vte")) {
            for (int i = 0; i < state_vte.length; i++) {
                state_vte[i] = true;
            }
        } else if (s1.equals("unplan")) {
            for (int i = 0; i < state_unplan.length; i++) {
                state_unplan[i] = true;
            }
        } else if (s1.equals("taping")) {
            for (int i = 0; i < state_taping.length; i++) {
                state_taping[i] = true;
            }
        } else if (s1.equals("fallbed")) {
            for (int i = 0; i < state_fallbed.length; i++) {
                state_fallbed[i] = true;
            }
        } else if (s1.equals("yachuang")) {

            for (int i = 0; i < state_yachuang.length; i++) {
                state_yachuang[i] = true;
            }


        }


    }

    @Override
    public void convert(final ViewHolder holder, final List<CheckBoxBean> datas, final String tag, final int position) {
        final CheckBoxBean bean = datas.get(position);
        holder.setText(R.id.tv_cuishi_name, bean.getName());
        final LinearLayout item = holder.getView(R.id.ll_cuoshimag);
        final ImageView image = holder.getView(R.id.iv_cuoshimag);

        if (s0.equals(Constant.UPDATA)) {
            String csxx = bean.getCsxx();
            if (csxx.equals("0")) {
                bean.setChecked(false);
            } else if (csxx.equals("1")) {
                bean.setChecked(true);
            }
        }

        if (bean.isChecked()) {//状态选中
            image.setImageResource(R.drawable.btn_choose_on);
        } else {
            image.setImageResource(R.drawable.btn_choose_null);
        }

        //展示措施信息
        for (int i = 0; i < datas.size(); i++) {
            if (bean.getMsg().equals("1")) {
                image.setImageResource(R.drawable.btn_choose_on);
            }
        }

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (mListener != null) {
                        if (s1.equals("vte")) {
                            if (s0.equals(Constant.UPDATA)) {
                                if (bean.getCsxx().equals("1")) {
                                    state_vte[position] = false;
                                    bean.setCsxx("0");
                                }
                            }
                            zaiciDianji(bean, state_vte[position]);
                            state_vte[position] = !state_vte[position];
                        } else if (s1.equals("unplan")) {
                            if (s0.equals(Constant.UPDATA)) {
                                for (CheckBoxBean bean : datas) {//全部设为未选中
                                    bean.setChecked(false);
                                    bean.setMsg("0");
                                    bean.setCsxx("0");
                                }
                                bean.setMsg("1");
                                bean.setChecked(true);//点击的设为选中
                            }

                            for (CheckBoxBean bean : datas) {//全部设为未选中
                                bean.setChecked(false);
                                bean.setMsg("0");
                            }
                            bean.setMsg("1");
                            bean.setChecked(true);//点击的设为选中
                        } else if (s1.equals("taping")) {
                            if (s0.equals(Constant.UPDATA)) {
                                if (bean.getCsxx().equals("1")) {
                                    state_taping[position] = false;
                                    bean.setCsxx("0");
                                }
                            }
                            zaiciDianji(bean, state_taping[position]);
                            state_taping[position] = !state_taping[position];
                        } else if (s1.equals("fallbed")) {
                            if (s0.equals(Constant.UPDATA)) {
                                if (bean.getCsxx().equals("1")) {
                                    state_fallbed[position] = false;
                                    bean.setCsxx("0");
                                }
                            }
                            zaiciDianji(bean, state_fallbed[position]);
                            state_fallbed[position] = !state_fallbed[position];
                        } else if (s1.equals("yachuang")) {
                            if (s0.equals(Constant.UPDATA)) {
                                if (bean.getCsxx().equals("1")) {
                                    state_yachuang[position] = false;
                                    bean.setCsxx("0");
                                }
                            }
                            zaiciDianji(bean, state_yachuang[position]);
                            state_yachuang[position] = !state_yachuang[position];
                        }
                        notifyDataSetChanged();
                        mListener.onCheckClick(bean.getName(), position, bean.isChecked());
                    }
                }
            }
        });


    }



    private void zaiciDianji(CheckBoxBean bean, boolean b) {
        if (b) {
            Log.e("beanoooo", bean.isChecked() + "" + b);
            bean.setChecked(true);//点击的设为选中
            bean.setMsg("1");
        } else {
            Log.e("111111", bean.isChecked() + "" + b);
            bean.setChecked(false);//再次点击取消
            bean.setMsg("0");
        }
    }


    //这里，我们定义一个接口
    public interface OnCheckClickListener {
        void onCheckClick(String name, int position, boolean isCheck);
    }

    private OnCheckClickListener mListener;

    public void setCheckListener(CuoShiMagAdapter.OnCheckClickListener mListener) {
        this.mListener = mListener;
    }
}
