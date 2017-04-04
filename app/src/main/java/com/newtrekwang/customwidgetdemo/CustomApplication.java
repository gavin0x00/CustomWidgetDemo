package com.newtrekwang.customwidgetdemo;

import android.app.Application;

import com.newtrekwang.customwidgetdemo.imageLoader.GlideImageLoaderProvider;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoaderUtil;

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtil.getInstance().setLoadImgStrategy(new GlideImageLoaderProvider());
    }
}
