package com.newtrekwang.customwidgetdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.base.BaseRecyclerViewAdapter;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoader;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoaderUtil;
import com.newtrekwang.customwidgetdemo.viewholder.SimpleViewHolder_2;

public class SimpleAdapter_2 extends BaseRecyclerViewAdapter<SimpleViewHolder_2,String> {
    public SimpleAdapter_2(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.simpleitem_2;
    }

    @Override
    protected RecyclerView.ViewHolder getCustomViewHolder(View view) {
        return new SimpleViewHolder_2(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder_2 holder, int position) {
        super.onBindViewHolder(holder, position);
        ImageLoader imageLoader=new ImageLoader.Builder().setImageUrl(datas.get(position)).setImageView(holder.simpleItem2Img).create();
        ImageLoaderUtil.getInstance().loadImage(context,imageLoader);
    }
}
