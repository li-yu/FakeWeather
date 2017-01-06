package com.liyu.fakeweather.ui.girl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;

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
        Fragment fragment = new GankFragment();
        adapter.addFrag(fragment, "Gank");

        fragment = new JiandanFragment();
        adapter.addFrag(fragment, "煎蛋");

        fragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/hot");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "最热");

        fragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/xinggan");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "性感妹子");

        fragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/japan");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "日本妹子");

        fragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/taiwan");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "台湾妹子");

        fragment = new MzituFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/mm");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "清纯妹子");

        fragment = new MzituZiPaiFragment();
        data = new Bundle();
        data.putString("url", "http://www.mzitu.com/share");
        fragment.setArguments(data);
        adapter.addFrag(fragment, "妹子自拍");

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
