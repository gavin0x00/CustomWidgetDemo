package com.newtrekwang.customwidgetdemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.newtrekwang.customwidgetdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleViewHolder_1 extends RecyclerView.ViewHolder {
    @BindView(R.id.simpleItem_1_tv)
    public TextView simpleItem1Tv;

    public SimpleViewHolder_1(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
