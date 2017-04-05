package com.newtrekwang.customwidgetdemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.newtrekwang.customwidgetdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleViewHolder_2 extends RecyclerView.ViewHolder {

    @BindView(R.id.simpleItem_2_img)
    public ImageView simpleItem2Img;

    public SimpleViewHolder_2(View itemView) {
        super(itemView);
      ButterKnife.bind(this,itemView);
    }
}
