package enjoyor.enjoyorzemobilehealth.hulidan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.HuLiDanActivity;
import enjoyor.enjoyorzemobilehealth.adapter.JiChuXiangMuGVAdapter;
import enjoyor.enjoyorzemobilehealth.entities.FenBiao;
import enjoyor.enjoyorzemobilehealth.entities.JiChuXiangMuBean;

/**
 * Created by Administrator on 2017/2/14.
 */

public class JiChuXiangMuFragment extends Fragment {
    private Context context;
    private GridView mGridView;
    private List<FenBiao> mFenBiaoList;
    public List<JiChuXiangMuBean> jiChuXiangMuBeanList=new ArrayList<>();

    private HuLiDanActivity activity;
//    public static JiChuXiangMuFragment newInstance(List<FenBiao> fenBiaoList){
//        JiChuXiangMuFragment fragment=new JiChuXiangMuFragment();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("list", (Serializable) fenBiaoList);
//        fragment.setArguments(bundle);
//        return fragment;
//    }


    public static JiChuXiangMuFragment newInstance(List<JiChuXiangMuBean> jiChuXiangMuBeanList){
        JiChuXiangMuFragment fragment=new JiChuXiangMuFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("list", (Serializable) jiChuXiangMuBeanList);
        fragment.setArguments(bundle);
        Log.i("data","------jiChuXiangMuBeanList"+jiChuXiangMuBeanList.size());
        return fragment;
    }

    public void setData(List<JiChuXiangMuBean> jiChuXiangMuBeanList){
        this.jiChuXiangMuBeanList=jiChuXiangMuBeanList;
        Log.i("data","jiChuXiangMuBeanList------"+jiChuXiangMuBeanList.size());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mFenBiaoList=new ArrayList<>();
        //jiChuXiangMuBeanList=new ArrayList<>();
        Bundle bundle=getArguments();
        if(bundle!=null){
            //mFenBiaoList= (List<FenBiao>) bundle.getSerializable("list");
            jiChuXiangMuBeanList= (List<JiChuXiangMuBean>) bundle.getSerializable("list");
            //Log.i("data","------mFenBiaoList"+mFenBiaoList.size());
            //Log.i("data","------jiChuXiangMuBeanList"+jiChuXiangMuBeanList.size());
        }
        //initData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_hulidan_jichuxiangmu,container,false);
        mGridView= (GridView) view.findViewById(R.id.gv_hulidan_jichuxiangmu);
        activity= (HuLiDanActivity) getActivity();
        jiChuXiangMuBeanList=activity.getJiChuXiangMuBeanList();
//        JiChuXiangMuGVAdapter adapter=new JiChuXiangMuGVAdapter(context,mFenBiaoList);
        JiChuXiangMuGVAdapter adapter=new JiChuXiangMuGVAdapter(context,jiChuXiangMuBeanList);
        //Log.i("data","jiChuXiangMuBeanList------"+jiChuXiangMuBeanList.size());
        mGridView.setAdapter(adapter);
        return view;
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
}
