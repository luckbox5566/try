package enjoyor.enjoyorzemobilehealth.bryz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by chenlikang
 */

public class FragmentFive extends BaseFragment {
    public static FragmentFive newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentFive fragment = new FragmentFive();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bryz_koufu,container,false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
