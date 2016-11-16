package com.liyu.suzhoubus.ui.bus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.liyu.suzhoubus.R;
import com.liyu.suzhoubus.http.ApiFactory;
import com.liyu.suzhoubus.http.BaseBusResponse;
import com.liyu.suzhoubus.http.api.BusController;
import com.liyu.suzhoubus.model.BusLineDetail;
import com.liyu.suzhoubus.model.StandInfoBean;
import com.liyu.suzhoubus.ui.base.BaseActivity;
import com.liyu.suzhoubus.ui.bus.adapter.LineDetailAdapter;
import com.liyu.suzhoubus.utils.RxDataBase;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by liyu on 2016/11/1.
 */

public class LineDetailActivity extends BaseActivity {

    public static final String KEY_EXTRA_GUID = "GUID";
    public static final String KEY_EXTRA_NAME = "NAME";
    public static final String KEY_EXTRA_DESC = "DESC";

    private RecyclerView recyclerView;
    private LineDetailAdapter adapter;
    private TextView tvLineInfoName;
    private TextView tvLineInfodirection;
    private TextView tvLineInfoSTime;
    private TextView tvLineInfoETime;
    private TextView tvLineInfoTotal;
    private FloatingActionButton fab;

    private boolean isFavorite = false;
    private String guid;

    public static void start(Context context, String guid, String name, String desc) {
        Intent intent = new Intent(context, LineDetailActivity.class);
        intent.putExtra(KEY_EXTRA_GUID, guid);
        intent.putExtra(KEY_EXTRA_NAME, name);
        intent.putExtra(KEY_EXTRA_DESC, desc);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_detail;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_EXTRA_NAME));
        getSupportActionBar().setSubtitle(getIntent().getStringExtra(KEY_EXTRA_DESC));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tvLineInfoName = (TextView) findViewById(R.id.tv_info_name);
        tvLineInfodirection = (TextView) findViewById(R.id.tv_info_direction);
        tvLineInfoSTime = (TextView) findViewById(R.id.tv_info_stime);
        tvLineInfoETime = (TextView) findViewById(R.id.tv_info_etime);
        tvLineInfoTotal = (TextView) findViewById(R.id.tv_info_total);
        recyclerView = (RecyclerView) findViewById(R.id.rv_line_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LineDetailAdapter(R.layout.item_bus_line_detail, null);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite = !isFavorite;
                ContentValues values = new ContentValues();
                values.put("isFavorite", isFavorite);
                DataSupport.updateAll(BusLineDetail.class, values, "LGUID = ?", guid);
                showFabStatus(isFavorite);
            }
        });
    }

    @Override
    protected void loadData() {
        tvLineInfoName.setText(getIntent().getStringExtra(KEY_EXTRA_NAME));
        tvLineInfodirection.setText(getIntent().getStringExtra(KEY_EXTRA_DESC));
        guid = getIntent().getStringExtra(KEY_EXTRA_GUID);
        RxDataBase.getFirst(BusLineDetail.class, "LGUID = ?", guid)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<BusLineDetail>() {
                    @Override
                    public void call(BusLineDetail busLineDetail) {
                        if (busLineDetail != null) {
                            showFabStatus(busLineDetail.isFavorite());
                            tvLineInfoSTime.setText("首班车: " + busLineDetail.getLFStdFTime());
                            tvLineInfoETime.setText("末班车: " + busLineDetail.getLFStdETime());
                            int stations = busLineDetail.getStandInfo() == null ? 0 : busLineDetail.getStandInfo().size();
                            tvLineInfoTotal.setText("总计: " + stations + " 站");
                            adapter.setNewData(busLineDetail.getStandInfo());
                        } else {
                            tvLineInfoSTime.setText("首班车: --");
                            tvLineInfoETime.setText("末班车: --");
                            tvLineInfoTotal.setText("总计: -- 站");
                        }
                    }
                })
                .flatMap(new Func1<BusLineDetail, Observable<BaseBusResponse<BusLineDetail>>>() {
                    @Override
                    public Observable<BaseBusResponse<BusLineDetail>> call(BusLineDetail busLineDetail) {
                        Map<String, String> options = new HashMap<>();
                        options.put("Guid", guid);
                        options.put("uid", BusController.uid);
                        options.put("DeviceID", BusController.deviceID);
                        options.put("sign", BusController.sign);
                        return ApiFactory.getBusController().getLineInfo(options).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBusResponse<BusLineDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBusResponse<BusLineDetail> response) {
//                        tvLineInfoName.setText(response.data.getLName());
//                        tvLineInfodirection.setText(response.data.getLDirection());
                        tvLineInfoSTime.setText("首班车: " + response.data.getLFStdFTime());
                        tvLineInfoETime.setText("末班车: " + response.data.getLFStdETime());
                        tvLineInfoTotal.setText("总计: " + response.data.getStandInfo().size() + " 站");
                        adapter.setNewData(response.data.getStandInfo());
                        List<StandInfoBean> stations = response.data.getStandInfo();
                        DataSupport.saveAll(stations);
                        BusLineDetail line = response.data;
                        line.setStandInfo(stations);
                        line.setFavorite(isFavorite);
                        RxDataBase.deleteThenSave(line, "LGUID = ?", response.data.getLGUID());
                    }
                });
    }

    private void showFabStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }
}
