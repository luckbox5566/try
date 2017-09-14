package enjoyor.enjoyorzemobilehealth.bryz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by chenlikang
 */

public class KoufuFragment extends BaseFragment {

    public static KoufuFragment newInstance() {
        
        Bundle args = new Bundle();
        KoufuFragment fragment = new KoufuFragment();
        fragment.setArguments(args);
        return fragment;

    }
   
}
