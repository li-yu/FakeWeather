package com.liyu.fakeweather.ui.setting;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.event.ModuleChangedEvent;
import com.liyu.fakeweather.model.Module;
import com.liyu.fakeweather.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2018/3/2.
 */

public class ModuleManageActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ModuleAdapter adapter;

    private boolean dataChanged = false;

    private int dragStartPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_manage;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        adapter = new ModuleAdapter(R.layout.item_module, null);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView = findView(R.id.rv_module_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.enableDragItem(itemTouchHelper, R.id.card_root, true);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                dragStartPosition = pos;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                if (dragStartPosition != pos) {
                    dataChanged = true;
                }
            }
        });
    }

    @Override
    protected void loadData() {
        DataSupport.order("index").findAsync(Module.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                adapter.setNewData((List<Module>) t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DataSupport.order("index").findAsync(Module.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<Module> saved = (List<Module>) t;
                for (int i = 0; i < saved.size(); i++) {
                    if (!saved.get(i).equals(adapter.getData().get(i))) {
                        dataChanged = true;
                        break;
                    }
                }
                for (int i = 0; i < adapter.getData().size(); i++) {
                    ContentValues values = new ContentValues();
                    values.put("index", i);
                    values.put("enable", adapter.getItem(i).isEnable());
                    DataSupport.updateAll(Module.class, values, "name = ?", adapter.getItem(i).getName());
                }
                if (dataChanged) {
                    EventBus.getDefault().post(new ModuleChangedEvent());
                }
                ModuleManageActivity.super.onBackPressed();
            }
        });

    }
}
