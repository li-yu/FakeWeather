package com.liyu.fakeweather.ui.bus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.http.ApiFactory;
import com.liyu.fakeweather.http.BaseBusResponse;
import com.liyu.fakeweather.model.BusLineSearch;
import com.liyu.fakeweather.model.BusNotice;
import com.liyu.fakeweather.ui.MainActivity;
import com.liyu.fakeweather.ui.base.BaseFragment;
import com.liyu.fakeweather.ui.bus.adapter.LineSearchAdapter;
import com.liyu.fakeweather.utils.SimpleSubscriber;
import com.liyu.fakeweather.utils.SizeUtils;
import com.liyu.fakeweather.utils.WebUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    private LineSearchAdapter searchAdapter;
    private View fakeStatusBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_viewpager;
    }

    @Override
    protected void initViews() {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("公交");
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
        initTabLayout();
        inflateMenu();
        initSearchView();
    }

    @Override
    protected void lazyFetchData() {
        ApiFactory
                .getBusController()
                .getBusNotices()
                .subscribeOn(Schedulers.io())
                .map(new Func1<BusNotice, String>() {
                    @Override
                    public String call(BusNotice busNotice) {
                        try {
                            Document doc = Jsoup.connect(busNotice.getData().getItems().getUrl()).timeout(10000).get();
                            Element element = doc.select("div#container").first();
                            Elements imgs = element.getElementsByTag("img");
                            for (Element img : imgs) {
                                img.attr("style", "max-width:100%;height:auto;");
                            }
                            return element.html();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final String msg) {
                        if (!TextUtils.isEmpty(msg)) {
                            final CounterFab counterFab = findView(R.id.fab_msg);
                            counterFab.setVisibility(View.VISIBLE);
                            counterFab.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    counterFab.increase();
                                }
                            }, 500);
                            counterFab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    WebUtils.load(getContext(), "<head>\n" +
                                            "    <title>交通公告</title>\n" +
                                            "  </head><base href=\"http://news.wisesz.cc/\" />" + msg);
                                }
                            });
                        }
                    }
                });
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
                .switchMap(new Func1<CharSequence, Observable<BaseBusResponse<BusLineSearch>>>() {
                    @Override
                    public Observable<BaseBusResponse<BusLineSearch>> call(CharSequence charSequence) {
                        return ApiFactory.getBusController().searchLine(charSequence.toString()).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<BaseBusResponse<BusLineSearch>>() {
                    @Override
                    public void onNext(BaseBusResponse<BusLineSearch> listBaseBusResponse) {
                        searchAdapter.setNewData(listBaseBusResponse.data.getList());
                        showAsDropDown(popupWindow, searchView);
                    }
                });

        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                showAsDropDown(popupWindow, mToolbar);
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
        TextView tvBusAll = contentView.findViewById(R.id.tv_bus_all);
        tvBusAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllLineActivity.class));
            }
        });
        recyclerView = contentView.findViewById(R.id.rv_line_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new LineSearchAdapter(R.layout.item_bus_line_search, null);
        recyclerView.setAdapter(searchAdapter);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void inflateMenu() {
        mToolbar.inflateMenu(R.menu.menu_bus);
        mToolbar.getMenu()
                .findItem(R.id.menu_subway).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                WebUtils.openInternal(getContext(), "http://api.caoliyu.cn/szsubway/index.html");
                getActivity().startActivity(new Intent(getActivity(), PublicBikeActivity.class));
                return false;
            }
        });
    }

    public void showAsDropDown(PopupWindow window, View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            window.setHeight(height);
        }
        popupWindow.showAsDropDown(anchor);
    }

    private void initTabLayout() {
        TabLayout tabLayout = findView(R.id.tabs);
        ViewPager viewPager = findView(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment fragment = new NearbyLineFragment();
        adapter.addFrag(fragment, getString(R.string.bus_nearby_line));

        fragment = new NearbyStationFragment();
        adapter.addFrag(fragment, getString(R.string.bus_nearby_station));

        fragment = new FavoritesFragment();
        adapter.addFrag(fragment, getString(R.string.bus_favorites));

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
