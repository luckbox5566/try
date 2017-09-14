package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;

/**
 * Created by Administrator on 2017/5/31.
 */

public class JiChuXiangMuGVAdapter extends BaseAdapter{
    private Context context;
    private HuLiDanActivity activity;
    //private List<FenBiao> mFenBiaoList;
    private List<JiChuXiangMuBean> jiChuXiangMuBeanList;
    private LayoutInflater inflater;

    private List<JiChuXiangMuBean> tempJiChuXiangMuBeanList;

    private int mTouchItemPosition = 0;
//    private boolean isFirstSetValue;

//    public JiChuXiangMuGVAdapter(Context context, List<FenBiao> mFenBiaoList) {
//        this.context = context;
//        this.mFenBiaoList = mFenBiaoList;
//        inflater=LayoutInflater.from(context);
//    }

    public JiChuXiangMuGVAdapter(Context context, List<JiChuXiangMuBean> jiChuXiangMuBeanList) {
        this.context = context;
        activity= (HuLiDanActivity) context;
        this.jiChuXiangMuBeanList=jiChuXiangMuBeanList;
        inflater=LayoutInflater.from(context);
        try {
            //深拷贝
            tempJiChuXiangMuBeanList = deepCopy(jiChuXiangMuBeanList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        //return mFenBiaoList.size();
        return jiChuXiangMuBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        //return mFenBiaoList.get(position);
        return jiChuXiangMuBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_gv_hulidan_jichuxiangmu,null);
            holder.llOut= (LinearLayout) convertView.findViewById(R.id.ll_out);
            holder.tvType= (TextView) convertView.findViewById(R.id.tv_type);
            holder.etInput= (AppCompatEditText) convertView.findViewById(R.id.et_type_input);
            holder.tvDanWei= (TextView) convertView.findViewById(R.id.tv_type_danwei);
            //给控件设置tag
            holder.etInput.setTag(position);
            holder.etInput.addTextChangedListener(new MyEdittextTextWatcher(holder));
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
            holder.etInput.setTag(position);
        }

//        if(mTouchItemPosition==position){
//            holder.etInput.setEnabled(true);
//        }else {
//            holder.etInput.setEnabled(false);
//        }
//
//        holder.llOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTouchItemPosition=position;
//                AppCompatEditText editText= (AppCompatEditText) v.findViewById(R.id.et_type_input);
//                editText.setEnabled(true);
//                //notifyDataSetChanged();
//            }
//        });

        JiChuXiangMuBean jiChuXiangMuBean=jiChuXiangMuBeanList.get(position);
        holder.tvType.setText(jiChuXiangMuBean.getJiChuXiangMuMC());
        holder.tvDanWei.setText(jiChuXiangMuBean.getDanWei());
        holder.etInput.setText(jiChuXiangMuBean.getShuZhi());

        return convertView;
    }
    class ViewHolder{
        LinearLayout llOut;
        TextView tvType;
        AppCompatEditText etInput;
        TextView tvDanWei;
    }

    private class MyEdittextTextWatcher implements TextWatcher{
        ViewHolder holder = null;
        public MyEdittextTextWatcher(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int position= (int) holder.etInput.getTag();
            String text=s.toString();
            //设置光标始终在文字右边
            holder.etInput.setSelection(text.length());

            JiChuXiangMuBean bean=jiChuXiangMuBeanList.get(position);
            JiChuXiangMuBean tempBean=tempJiChuXiangMuBeanList.get(position);

                if(TextUtils.isEmpty(tempBean.getShuZhi())&&!TextUtils.isEmpty(text)){
                    bean.setShuZhi(text);
                    bean.setPanDuanBZ("1");
                }else if(!TextUtils.isEmpty(tempBean.getShuZhi())&&!TextUtils.equals(tempBean.getShuZhi(),text)){
                    bean.setShuZhi(text);
                    bean.setPanDuanBZ("0");
                }else {
                    bean.setShuZhi(text);
                }
                //往HuLiDanActivity传递修改后的数据
                activity.getJiChuXiangMuData(jiChuXiangMuBeanList);
        }
    }

    public List<JiChuXiangMuBean> deepCopy(List<JiChuXiangMuBean> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<JiChuXiangMuBean> dest = (List<JiChuXiangMuBean>) in.readObject();
        return dest;
    }

}
