package com.liyu.suzhoubus.ui.bus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseBusResponse;
import com.liyu.suzhoubus.model.BusLineShort;
import com.liyu.suzhoubus.ui.MainActivity;
import com.liyu.suzhoubus.ui.base.BaseFragment;
import com.liyu.suzhoubus.ui.bus.adapter.LineAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/10/31.
 */

public class BusFragment extends BaseFragment {

    private Toolbar mToolbar;
    private MenuItem search;
    private SearchView searchView;
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private LineAdapter searchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bus;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("公交");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        initTabLayout();
        inflateMenu();
        initSearchView();
    }

    @Override
    protected void lazyFetchData() {

    }

    private void initSearchView() {
        search = mToolbar.getMenu()
                .findItem(R.id.menu_search);
        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("输入公交线路...");
        RxSearchView
                .queryTextChanges(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                .switchMap(new Func1<CharSequence, Observable<BaseBusResponse<BusLineShort>>>() {
                    @Override
                    public Observable<BaseBusResponse<BusLineShort>> call(CharSequence charSequence) {
                        return ApiFactory.getBusController().searchLine(charSequence.toString()).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBusResponse<BusLineShort>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBusResponse<BusLineShort> listBaseBusResponse) {
                        searchAdapter.setNewData(listBaseBusResponse.data.getList());
                        popupWindow.showAsDropDown(searchView);
                    }
                });

        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                popupWindow.showAsDropDown(mToolbar);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                popupWindow.dismiss();
                return true;
            }
        });
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.fragment_line_search, null);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.rv_line_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new LineAdapter(R.layout.item_bus_line_search, null);
        recyclerView.setAdapter(searchAdapter);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void inflateMenu() {
        mToolbar.inflateMenu(R.menu.menu_bus);
    }

    private void initTabLayout() {
        TabLayout tabLayout = findView(R.id.tabs);
        ViewPager viewPager = findView(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
        //适合很多tab
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab均分,适合少的tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tab均分,适合少的tab,TabLayout.GRAVITY_CENTER
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment newfragment = new NearbyLineFragment();
        Bundle data = new Bundle();
        data.putInt("id", 1);
        data.putString("title", getString(R.string.bus_nearby_line));
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, getString(R.string.bus_nearby_line));

        newfragment = new NearbyStationFragment();
        data = new Bundle();
        data.putInt("id", 2);
        data.putString("title", getString(R.string.bus_nearby_station));
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, getString(R.string.bus_nearby_station));

        newfragment = new FavoritesFragment();
        data = new Bundle();
        data.putInt("id", 3);
        data.putString("title", getString(R.string.bus_favorites));
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, getString(R.string.bus_favorites));

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
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (MenuItemCompat.isActionViewExpanded(search)) {
                        MenuItemCompat.collapseActionView(search);
                        return true;
                    } else
                        return false;
                }
                return false;
            }
        });
    }
}
