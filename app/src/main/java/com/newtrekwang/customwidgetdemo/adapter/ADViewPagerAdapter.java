package com.newtrekwang.customwidgetdemo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoader;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class ADViewPagerAdapter extends PagerAdapter {
    private List<String> urlLists;
public ADViewPagerAdapter(Context context){
    this.urlLists=new ArrayList<>();
}


    @Override
    public int getCount() {
        return urlLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView ,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView= (ImageView) object;
        container.removeView(imageView);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        ImageView imageView= (ImageView) object;
        ImageLoader imageLoader=new ImageLoader.Builder().setImageUrl(urlLists.get(position)).setImageView(imageView).create();
        ImageLoaderUtil.getInstance().loadImage(container.getContext(),imageLoader);
    }

    public List<String> getUrlLists() {
        return urlLists;
    }

    public void setUrlLists(List<String> urlLists) {
        this.urlLists = urlLists;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }
}
