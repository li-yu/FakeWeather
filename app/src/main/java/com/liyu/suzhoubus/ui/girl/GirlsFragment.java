package com.liyu.suzhoubus.ui.girl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.ui.MainActivity;
import com.liyu.suzhoubus.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2016/10/31.
 */

public class GirlsFragment extends BaseFragment {

    private Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_viewpager;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("福利");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        initTabLayout();
    }

    @Override
    protected void lazyFetchData() {

    }

    private void initTabLayout() {
        TabLayout tabLayout = findView(R.id.tabs);
        ViewPager viewPager = findView(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle data;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment newfragment = new GankFragment();
        adapter.addFrag(newfragment, "Gank");

        newfragment = new JiandanFragment();
        adapter.addFrag(newfragment, "煎蛋");

        newfragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/hot");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "最热");

        newfragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/xinggan");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "性感妹子");

        newfragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/japan");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "日本妹子");

        newfragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/taiwan");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "台湾妹子");

        newfragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/mm");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "清纯妹子");

        newfragment = new MzituZiPaiFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/share");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "妹子自拍");

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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
    }

}
