package com.liyu.fakeweather.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/10/31.
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public BaseViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragmentManager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void clear() {
        if (this.mFragmentList != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment f : this.mFragmentList) {
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
        mFragmentList.clear();
        mFragmentTitleList.clear();
        notifyDataSetChanged();

    }
}
