package com.newtrekwang.customwidgetdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.base.BaseRecyclerViewAdapter;
import com.newtrekwang.customwidgetdemo.viewholder.SimpleViewHolder_1;

/**
 * 单类型的item
 */
public class SimpleAdapter_1 extends BaseRecyclerViewAdapter<SimpleViewHolder_1, String> {
    public SimpleAdapter_1(Context context) {
        super(context);
    }

    /**
     * 提供item布局文件
     */
    @Override
    protected int getItemViewLayoutId() {
        return R.layout.simpleitem_1;
    }

    /**
     * 提供item的ViewHolder
     */
    @Override
    protected RecyclerView.ViewHolder getCustomViewHolder(View view) {
        return new SimpleViewHolder_1(view);
    }

    /**
     * itemView设置数据
     */
    @Override
    public void onBindViewHolder(SimpleViewHolder_1 holder, int position) {
        super.onBindViewHolder(holder,position);
        holder.simpleItem1Tv.setText(datas.get(position));
    }
}
