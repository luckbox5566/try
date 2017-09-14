package enjoyor.enjoyorzemobilehealth.hulidan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.adapter.HuLiDanRuLiangChuLiangAdapter;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;
import enjoyor.enjoyorzemobilehealth.entities.RuLiangAndChuLiangBean;

/**
 * Created by Administrator on 2017/2/14.
 */

public class ChuLiangFragment extends Fragment {
    private LinearLayout llContent;
    private TextView mTvAdd;
    private Context context;
    private HuLiDanActivity activity;

//    private EditText etContent;
//    private EditText etValue;

    private ListView lvContent;
    private HuLiDanRuLiangChuLiangAdapter mAdapter;

    public List<RuLiangAndChuLiangBean> data=new ArrayList<>();
    public List<JiChuXiangMuBean> chuLiangList=new ArrayList<>();
//    private List<RuLiangAndChuLiangBean> mList;
//    private HuLiDanRuLiangChuLiangAdapter mAdapter;

//    public static ChuLiangFragment newInstance(List<RuLiangAndChuLiangBean> chuLiangList){
//        ChuLiangFragment fragment=new ChuLiangFragment();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("list", (Serializable) chuLiangList);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static ChuLiangFragment newInstance(List<JiChuXiangMuBean> chuLiangList){
        ChuLiangFragment fragment=new ChuLiangFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("list", (Serializable) chuLiangList);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(List<JiChuXiangMuBean> chuLiangList){
        this.chuLiangList=chuLiangList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        data=new ArrayList<>();
        //chuLiangList=new ArrayList<>();
//        Bundle bundle=getArguments();
//        if(bundle!=null){
//            chuLiangList= (List<JiChuXiangMuBean>) bundle.getSerializable("list");
//            if(chuLiangList.size()>0){
//                Log.i("data","------chuLiangList"+chuLiangList.get(0).getShuZhi());
//            }
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hulidan_ruliang_chuliang_bak, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

//    private void initData() {
//        if(chuLiangList.size()!=0){
//            String value=chuLiangList.get(0).getShuZhi();
//            if(value.contains("*")){
//                splitString(value);
//            }else {
//                String[] str=value.split("\\$");
//                View view = LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview, null);
//                etContent = (EditText) view.findViewById(R.id.et_content);
//                etValue = (EditText) view.findViewById(R.id.et_value);
//                etContent.setText(str[0]);
//                etValue.setText(str[1]);
//                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
//                data.add(bean);
//                etContent.addTextChangedListener(new MyEdittextTextWatcher(etContent,bean));
//                etValue.addTextChangedListener(new MyEdittextTextWatcher(etValue,bean));
//                llContent.addView(view);
//            }
//        }
//    }

    private void initData() {
        //清空数据源
        data.clear();
        chuLiangList=activity.getChuLiangData();
        if(chuLiangList.size()!=0){
            String value=chuLiangList.get(0).getShuZhi();
            Log.i("data","value------"+value);
            if(value.contains("*")){
                splitString(value);
            }else {
                String[] str=value.split("\\$");
                Log.i("data","------"+str[0]+"---"+str[1]);
                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
                bean.setContent(str[0]);
                bean.setValue(str[1]);
                data.add(bean);
            }
        }
        mAdapter=new HuLiDanRuLiangChuLiangAdapter(context,data,1);
        lvContent.setAdapter(mAdapter);
    }

    /**
     * 分割string并添加view
     * @param str
     */
//    private void splitString(String str) {
//        String[] outStr=str.split("\\*");
//        for(int i=0;i<outStr.length;i++){
//            String[] inStr=outStr[i].split("\\$");
//            View view = LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview, null);
//            etContent = (EditText) view.findViewById(R.id.et_content);
//            etValue = (EditText) view.findViewById(R.id.et_value);
//            etContent.setText(inStr[0]);
//            etValue.setText(inStr[1]);
//            RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
//            data.add(bean);
//            etContent.addTextChangedListener(new MyEdittextTextWatcher(etContent,bean));
//            etValue.addTextChangedListener(new MyEdittextTextWatcher(etValue,bean));
//            llContent.addView(view);
//        }
//    }
    private void splitString(String str) {
        String[] outStr=str.split("\\*");
        for(int i=0;i<outStr.length;i++){
            String[] inStr=outStr[i].split("\\$");
            Log.i("data","------"+inStr[0]+"---"+inStr[1]);
            RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
            bean.setContent(inStr[0]);
            bean.setValue(inStr[1]);
            data.add(bean);
        }
    }

    private void initView(View view) {
//        llContent= (LinearLayout) view.findViewById(R.id.ll_content);
        mTvAdd= (TextView) view.findViewById(R.id.tv_add);
        lvContent= (ListView) view.findViewById(R.id.lv_ruliang_chuliang_content);
    }

    private void initListener() {
//        mTvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view=LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview,null);
//                etContent= (EditText) view.findViewById(R.id.et_content);
//                etValue= (EditText) view.findViewById(R.id.et_value);
//                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
//                data.add(bean);
//                etContent.addTextChangedListener(new MyEdittextTextWatcher(etContent,bean));
//                etValue.addTextChangedListener(new MyEdittextTextWatcher(etValue,bean));
//                llContent.addView(view);
//            }
//        });

        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
                data.add(bean);
//                mAdapter=new HuLiDanRuLiangChuLiangAdapter(context,data);
//                lvContent.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        activity= (HuLiDanActivity) context;
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
//            String text=s.toString();
//            //设置光标始终在文字右边
//            editText.setSelection(text.length());
//            switch (editText.getId()){
//                case R.id.et_content:
//                    bean.setContent(text);
//                    break;
//                case R.id.et_value:
//                    bean.setValue(text);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
}
