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

public class RuLiangFragment extends Fragment {
    private LinearLayout llContent;
    private TextView mTvAdd;
    private Context context;
    private HuLiDanActivity activity;

    private ListView lvContent;
    private HuLiDanRuLiangChuLiangAdapter mAdapter;
//    private EditText etContent;
//    private EditText etValue;


    public List<RuLiangAndChuLiangBean> data=new ArrayList<>();
    public List<JiChuXiangMuBean> ruLiangList=new ArrayList<>();
    private View view;
    //    private List<RuLiangAndChuLiangBean> mList;
//    private HuLiDanRuLiangChuLiangAdapter mAdapter;

//    public static RuLiangFragment newInstance(List<RuLiangAndChuLiangBean> ruLiangList){
//        RuLiangFragment fragment=new RuLiangFragment();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("list", (Serializable) ruLiangList);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static RuLiangFragment newInstance(List<JiChuXiangMuBean> ruLiangList) {
        RuLiangFragment fragment = new RuLiangFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) ruLiangList);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(List<JiChuXiangMuBean> ruLiangList){
        this.ruLiangList=ruLiangList;
        if(this.ruLiangList.size()>0){
            Log.i("data","ruLiangList"+this.ruLiangList.get(0).getShuZhi());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ruLiangList = new ArrayList<>();
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            ruLiangList = (List<JiChuXiangMuBean>) bundle.getSerializable("list");
//        }
        Log.i("data","onCreate"+"---------------");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hulidan_ruliang_chuliang_bak, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        Log.i("data","onCreateView"+"---------------");
        return view;
    }

//    private void initData() {
//        if(ruLiangList.size()!=0){
//            String value=ruLiangList.get(0).getShuZhi();
//            Log.i("data","value------"+value);
//            if(value.contains("*")){
//                splitString(value);
//            }else {
//                String[] str=value.split("\\$");
//                view = LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview, null);
//                EditText etContent = (EditText) view.findViewById(R.id.et_content);
//                EditText etValue = (EditText) view.findViewById(R.id.et_value);
//                Log.i("data","------"+str[0]+"---"+str[1]);
//                etContent.setText(str[0]);
//                etValue.setText(str[1]);
//                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
//                data.add(bean);
////                etContent.addTextChangedListener(new MyEdittextTextWatcher(etContent,bean));
////                etValue.addTextChangedListener(new MyEdittextTextWatcher(etValue,bean));
//                llContent.addView(view);
//            }
//        }
//    }

    private void initData() {
        //清空数据源
        data.clear();
        if(this.ruLiangList.size()>0){
            Log.i("data","ruLiangList"+this.ruLiangList.get(0).getShuZhi());
        }
        activity= (HuLiDanActivity) getActivity();
        ruLiangList=activity.getRuLiangData();
        if(ruLiangList.size()!=0){
            String value=ruLiangList.get(0).getShuZhi();
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
        mAdapter=new HuLiDanRuLiangChuLiangAdapter(context,data,0);
        lvContent.setAdapter(mAdapter);
    }

    /**
     * 分割string并添加view
     * @param str
     */
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
//        llContent = (LinearLayout) view.findViewById(R.id.ll_content);
        mTvAdd = (TextView) view.findViewById(R.id.tv_add);
        lvContent= (ListView) view.findViewById(R.id.lv_ruliang_chuliang_content);
    }

    private void initListener() {
//        mTvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View view = LayoutInflater.from(context).inflate(R.layout.item_hulidan_addview, null);
//                EditText etContent = (EditText) view.findViewById(R.id.et_content);
//                EditText etValue = (EditText) view.findViewById(R.id.et_value);
//                RuLiangAndChuLiangBean bean=new RuLiangAndChuLiangBean();
//                data.add(bean);
////                JiChuXiangMuBean bean = new JiChuXiangMuBean();
////                ruLiangList.add(bean);
//                etContent.addTextChangedListener(new MyEdittextTextWatcher(etContent, bean));
//                etValue.addTextChangedListener(new MyEdittextTextWatcher(etValue, bean));
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
        Log.i("data","onActivityCreated"+"---------------");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity= (HuLiDanActivity) context;
        Log.i("data","onAttach"+"---------------");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("data","onStart"+"---------------");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("data","onResume"+"---------------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("data","onPause"+"---------------");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("data","onStop"+"---------------");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("data","onDestroyView"+"---------------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("data","onDestroy"+"---------------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("data","onDetach"+"---------------");
    }

//    private class MyEdittextTextWatcher implements TextWatcher {
//        private EditText editText;
//        private RuLiangAndChuLiangBean bean;
//        //private JiChuXiangMuBean bean;
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
}
