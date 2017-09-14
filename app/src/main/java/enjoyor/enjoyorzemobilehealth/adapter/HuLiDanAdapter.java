package enjoyor.enjoyorzemobilehealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HuLiDanAdapter extends FragmentPagerAdapter{
    private String[] tabs;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    public HuLiDanAdapter(FragmentManager fm, String[] tabs, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentManager=fm;
        this.tabs=tabs;
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment fragment= (Fragment) super.instantiateItem(container,position);
//        fragmentManager.beginTransaction().attach(fragment).show(fragment).commit();
//        return fragment;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        Fragment fragment=fragmentList.get(position);
//        fragmentManager.beginTransaction().hide(fragment).commit();
//    }
}
