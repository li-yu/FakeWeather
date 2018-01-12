package com.liyu.fakeweather.ui.reading;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.XianduCategory;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;
import com.liyu.fakeweather.utils.ACache;
import com.liyu.fakeweather.utils.SizeUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/12/13.
 */

public class ReadingFragment extends BaseFragment {

    private static final String CACHE_XIANDU_CATE = "xiandu_cache";

    private Toolbar mToolbar;

    private ACache mCache;

    private View fakeStatusBar;

    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_viewpager;
    }

    @Override
    protected void initViews() {
        mCache = ACache.get(getActivity());
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("闲读");
        fakeStatusBar = findView(R.id.fakeStatusBar);
        fakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
                int statusBarHeight = SizeUtils.getStatusBarHeight(getActivity());
                layoutParams.height = statusBarHeight;
                fakeStatusBar.setLayoutParams(layoutParams);
            }
        });
        ((MainActivity) getActivity()).initDrawer(mToolbar);
    }

    @Override
    protected void lazyFetchData() {
        List<XianduCategory> xianduCategories = (List<XianduCategory>) mCache.getAsObject(CACHE_XIANDU_CATE);
        if (xianduCategories != null && xianduCategories.size() > 0) {
            initTabLayout(xianduCategories);
            return;
        }
        String host = "http://gank.io/xiandu";
        subscription = Observable.just(host).subscribeOn(Schedulers.io()).map(new Func1<String, List<XianduCategory>>() {
            @Override
            public List<XianduCategory> call(String host) {
                List<XianduCategory> list = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(host).timeout(10000).get();
                    Element cate = doc.select("div#xiandu_cat").first();
                    Elements links = cate.select("a[href]");
                    for (Element element : links) {
                        XianduCategory xiandu = new XianduCategory();
                        xiandu.setName(element.text());
                        xiandu.setUrl(element.attr("abs:href"));
                        list.add(xiandu);
                    }
                } catch (IOException e) {
                    Snackbar.make(getView(), "获取分类失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lazyFetchData();
                        }
                    }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
                }
                if (list.size() > 0) {
                    list.get(0).setUrl(host + "/wow");
                }
                return list;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<XianduCategory>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(getView(), "获取分类失败!", Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lazyFetchData();
                    }
                }).setActionTextColor(getActivity().getResources().getColor(R.color.actionColor)).show();
            }

            @Override
            public void onNext(List<XianduCategory> xianduCategories) {
                mCache.put(CACHE_XIANDU_CATE, (Serializable) xianduCategories);
                initTabLayout(xianduCategories);
            }
        });

    }

    private void initTabLayout(List<XianduCategory> xianduCategories) {
        TabLayout tabLayout = findView(R.id.tabs);
        ViewPager viewPager = findView(R.id.viewPager);
        setupViewPager(viewPager, xianduCategories);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void setupViewPager(ViewPager viewPager, List<XianduCategory> xianduCategories) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        for (XianduCategory xiandu : xianduCategories) {
            Fragment fragment = new CategoryFragment();
            Bundle data = new Bundle();
            data.putString("url", xiandu.getUrl());
            fragment.setArguments(data);
            adapter.addFrag(fragment, xiandu.getName());
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
