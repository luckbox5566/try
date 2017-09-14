package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.base.CommonAdapter;
import enjoyor.enjoyorzemobilehealth.base.ViewHolder;
import enjoyor.enjoyorzemobilehealth.entities.CbMoreBean;
import enjoyor.enjoyorzemobilehealth.entities.CheckBoxBean;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;

/**
 * Created by admin on
 */

public class MoreChooseAdapter extends CommonAdapter<CheckBoxBean> {

    public MoreChooseAdapter(Context context, List<CheckBoxBean> mDatas, String tag, int layoutId) {
        super(context, mDatas, tag, layoutId);
    }

    public MoreChooseAdapter(Context context, List<CheckBoxBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    public void convert(final ViewHolder holder, final List<CheckBoxBean> datas, final String tag, final int position) {
        final CheckBoxBean bean = datas.get(position);
        holder.setText(R.id.gv_name, bean.getName()).setText(R.id.gv_sorce, bean.getSorce());
//        final CheckBox checkBox = holder.getView(R.id.cb_gv_1);

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    if (checkBox.isChecked()) {
//                        bean.setChecked(true);//点击的设为选中
//                    } else {
//                        bean.setChecked(false);//再次点击取消
//                    }
//                    mListener.onCheckClick(bean.getName(), position, checkBox, tag);
//                }
//            }
//        });
//        if (tag.equals(Constant.UPDATA)){
//            String csxx = bean.getCsxx();
//            if (csxx.equals("0")) {
//                checkBox.setChecked(false);
//            } else if (csxx.equals("1")) {
//                checkBox.setChecked(true);
//            }
//        }
    }



    //这里，我们定义一个接口
    public interface OnCheckClickListener {
        void onCheckClick(String name, int position, CheckBox checkBox, String tag);
    }

    private OnCheckClickListener mListener;

    public void setCheckListener(OnCheckClickListener mListener) {
        this.mListener = mListener;
    }
}