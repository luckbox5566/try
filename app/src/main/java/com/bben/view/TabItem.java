package com.bben.view;

import enjoyor.enjoyorzemobilehealth.bryz.fragments.BaseFragment;

/**
 * Created by dantevsyou on 2017/8/3.
 */

public class TabItem {
    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int lableResId;
    public Class<? extends BaseFragment>tagFragmentClz;

    public TabItem(int imageResId, int lableResId) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
    }

    public TabItem(int imageResId, int lableResId, Class<? extends BaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }

}
