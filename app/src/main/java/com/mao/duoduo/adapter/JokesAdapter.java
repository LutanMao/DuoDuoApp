package com.mao.duoduo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Mao on 16-12-30.
 */
public class JokesAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public JokesAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null || mFragments.isEmpty()) {
            return 0;
        } else {
            return mFragments.size();
        }
    }
}
