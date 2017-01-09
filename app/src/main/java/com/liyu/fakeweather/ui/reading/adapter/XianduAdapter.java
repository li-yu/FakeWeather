package com.liyu.fakeweather.ui.reading.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.model.XianduItem;
import com.liyu.fakeweather.utils.WebviewUtils;

import java.util.List;

/**
 * Created by liyu on 2016/12/9.
 */

public class XianduAdapter extends RecyclerView.Adapter<XianduAdapter.XianViewHolder> {

    private List<XianduItem> xiandus;
    private Context context;
    private LayoutInflater inflater;

    public XianduAdapter(Context context, List<XianduItem> list) {
        this.context = context;
        this.xiandus = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setNewData(List<XianduItem> data) {
        this.xiandus = data;
        notifyDataSetChanged();
    }

    public List<XianduItem> getData() {
        return xiandus;
    }

    public void addData(int position, List<XianduItem> data) {
        this.xiandus.addAll(position, data);
        this.notifyItemRangeInserted(position, data.size());
    }

    @Override
    public XianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_xiandu, parent, false);
        XianViewHolder holder = new XianViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final XianViewHolder holder, int position) {
        final XianduItem item = xiandus.get(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewUtils.open(context, item.getUrl());
            }
        });
        holder.tv_name.setText(String.format("%s. %s", position + 1, item.getName()));
        holder.tv_info.setText(item.getUpdateTime() + " • " + item.getFrom());
        Glide.with(context).load(item.getIcon()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return xiandus == null ? 0 : xiandus.size();
    }

    @Override
    public long getItemId(int position) {
        return xiandus.get(position).hashCode();
    }

    class XianViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_info;
        ImageView iv;
        View rootView;

        public XianViewHolder(View view) {
            super(view);
            rootView = view;
            iv = (ImageView) view.findViewById(R.id.iv_xiantu_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_xiandu_name);
            tv_info = (TextView) view.findViewById(R.id.tv_xiandu_info);
        }

    }

}
