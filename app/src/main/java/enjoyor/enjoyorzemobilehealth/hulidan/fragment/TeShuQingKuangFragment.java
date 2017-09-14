package enjoyor.enjoyorzemobilehealth.hulidan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.activity.ZongJieTypeSelectActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/2/14.
 */

public class TeShuQingKuangFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ImageView mIvTypeSelect;
    private TextView mTvType;
    private EditText mEtContent;

    private HuLiDanActivity huLiDanActivity;

    private static final int REQUEST_CODE = 1; // 请求码

    public String zongJieType="";
    public String teShuQingKuang="";

//    public List<String> zongJieList=new ArrayList<>();
    public static TeShuQingKuangFragment newInstance(String zongJieType,String teShuQingKuang){
        TeShuQingKuangFragment fragment=new TeShuQingKuangFragment();
        Bundle bundle=new Bundle();
        bundle.putString("zongJieType",zongJieType);
        bundle.putString("teShuQingKuang",teShuQingKuang);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setData(String zongJieType,String teShuQingKuang){
        this.zongJieType=zongJieType;
        this.teShuQingKuang=teShuQingKuang;
        Log.i("data",zongJieType+"zongJieType----teShuQingKuang"+teShuQingKuang);
    }
//    public void setData(List<String> zongJieList){
//        this.zongJieList=zongJieList;
//        Log.i("0000",zongJieList.get(0).toString()+"zongJieType----teShuQingKuang");
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle=getArguments();
//        if(bundle!=null){
//            zongJieType=bundle.getString("zongJieType");
//            teShuQingKuang=bundle.getString("teShuQingKuang");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_hulidan_teshuqingkuang, container, false);
        mIvTypeSelect= (ImageView) view.findViewById(R.id.iv_zongjie_select);
        mTvType= (TextView) view.findViewById(R.id.tv_zongjie);
        mEtContent= (EditText) view.findViewById(R.id.et_teshuqingkuang_content);
//        Bundle bundle=getArguments();
//        zongJieType=bundle.getString("zongJieType");
//        teShuQingKuang=bundle.getString("teShuQingKuang");
        huLiDanActivity= (HuLiDanActivity) getActivity();
        zongJieType=huLiDanActivity.getZongJieType();
        teShuQingKuang=huLiDanActivity.getTeShuQingKuang();
        //解决Edittext的setText无效问题
        mEtContent.setSaveEnabled(false);

        if(TextUtils.isEmpty(zongJieType)){
            Log.i("data","执行了");
            mTvType.setText("暂无");
        }else {
            mTvType.setText(zongJieType);
        }
        Log.i("data","teShuQingKuang"+teShuQingKuang);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                teShuQingKuang=s.toString();
                //往HuLiDanActivity回传数据
                huLiDanActivity.getTeShuQingKuangFragmentData(zongJieType,teShuQingKuang);
            }
        });
        mEtContent.setText(teShuQingKuang);
        if(!TextUtils.isEmpty(teShuQingKuang)){
            mEtContent.setSelection(teShuQingKuang.length());
        }
        mIvTypeSelect.setOnClickListener(this);

        zongJieType=mTvType.getText().toString();
//        teShuQingKuang=mEtContent.getText().toString();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_zongjie_select:
                Intent intent=new Intent(context, ZongJieTypeSelectActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE){
                String typeSelected=data.getStringExtra("type");
                mTvType.setText(typeSelected);
                zongJieType=mTvType.getText().toString();
                //往HuLiDanActivity回传数据
                huLiDanActivity.getTeShuQingKuangFragmentData(zongJieType,teShuQingKuang);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        huLiDanActivity= (HuLiDanActivity) context;
    }
}
