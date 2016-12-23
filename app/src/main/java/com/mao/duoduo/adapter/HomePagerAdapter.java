package com.mao.duoduo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Mao on 16-12-23.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        if (null == mFragments || mFragments.isEmpty()) {
            return 0;
        } else {
            return mFragments.size();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
