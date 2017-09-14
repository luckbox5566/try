package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.entities.RuLiangAndChuLiangBean;

/**
 * Created by Administrator on 2017/2/23.
 */

public class HuLiDanRuLiangChuLiangAdapter extends BaseAdapter {
    private Context context;
    List<RuLiangAndChuLiangBean> mList;
    private HuLiDanActivity activity;
    private int fromWhich;

    public HuLiDanRuLiangChuLiangAdapter(Context context, List<RuLiangAndChuLiangBean> mList,int fromWhich) {
        this.context = context;
        this.mList = mList;
        this.fromWhich=fromWhich;
        activity= (HuLiDanActivity) context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview, null);
            holder = new ViewHolder();
            holder.etContent = (EditText) convertView.findViewById(R.id.et_content);
            holder.etValue = (EditText) convertView.findViewById(R.id.et_value);
            holder.etContent.setTag(position);
            holder.etValue.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.etContent.setTag(position);
            holder.etValue.setTag(position);
        }
        RuLiangAndChuLiangBean bean = mList.get(position);
        holder.etContent.addTextChangedListener(new MyEdittextTextWatcher(holder,holder.etContent,bean));
        holder.etValue.addTextChangedListener(new MyEdittextTextWatcher(holder,holder.etValue,bean));
        holder.etContent.setText(bean.getContent());
        holder.etValue.setText(bean.getValue());

//        holder.etContent.addTextChangedListener(new MyEdittextTextWatcher(holder.etContent, bean));
//        holder.etValue.addTextChangedListener(new MyEdittextTextWatcher(holder.etValue, bean));


        return convertView;
    }

    class ViewHolder {
        EditText etContent;
        EditText etValue;
    }

    //    private class MyEdittextTextWatcher implements TextWatcher {
//        private EditText editText;
//        private RuLiangAndChuLiangBean bean;
//
//        public MyEdittextTextWatcher(EditText editText,RuLiangAndChuLiangBean bean) {
//            this.editText = editText;
//            this.bean=bean;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            String text = s.toString();
//            Log.i("data","text------"+text);
//            //设置光标始终在文字右边
//            editText.setSelection(text.length());
//            switch (editText.getId()) {
//                case R.id.et_content:
//                    bean.setContent(text);
//                    //bean.setShuZhi(text);
//                    break;
//                case R.id.et_value:
//                    bean.setValue(text);
//                    //bean.setShuZhi(text);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
    private class MyEdittextTextWatcher implements TextWatcher {
        private ViewHolder holder;
        private EditText editText;
        private RuLiangAndChuLiangBean bean;

        public MyEdittextTextWatcher(ViewHolder holder,EditText editText, RuLiangAndChuLiangBean bean) {
            this.holder = holder;
            this.editText=editText;
            this.bean = bean;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            Log.i("data", "text------" + text);
            int position= (int) holder.etContent.getTag();
            switch (editText.getId()) {
                case R.id.et_content:
                    //设置光标始终在文字右边
                    holder.etContent.setSelection(text.length());
                    mList.get(position).setContent(text);
                    break;
                case R.id.et_value:
                    //设置光标始终在文字右边
                    holder.etValue.setSelection(text.length());
                    mList.get(position).setValue(text);
                    break;
                default:
                    break;
            }
            //给HuLiDanActivity传递数据
            activity.getFinalData(mList,fromWhich);
        }
    }
}
