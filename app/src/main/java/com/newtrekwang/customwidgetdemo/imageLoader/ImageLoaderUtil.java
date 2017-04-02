package com.newtrekwang.customwidgetdemo.imageLoader;

import android.content.Context;

/**
 * 内部静态类单例ImageLoaderUtil
 */
public class ImageLoaderUtil {
    /**
     * 加载图片大小类型声明
     */
    public enum LoadSizeType{
        PIC_SMALL,
        PIC_NORMAL,
        PIC_BIG
    }
    /**
     * 网络加载类型声明
     */
    public enum NetLoadType {
        LOAD_STRATEGY_NORMAL,
        LOAD_STRATEGY_ONLY_WIFI
    }
    private BaseImageLoaderStrategy imageLoaderStrategy;

    private ImageLoaderUtil() {

    }

    /**
     * 获取单例
     */
    public static ImageLoaderUtil getInstance() {
        return ImageLoaderUtilHolder.INSTANCE;
    }

    /**
     * 提供单例
     */
    private static class ImageLoaderUtilHolder {
        private static ImageLoaderUtil INSTANCE = new ImageLoaderUtil();
    }

    /**
     * 设置图片加载策略
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
        this.imageLoaderStrategy = imageLoaderStrategy;
    }
    public void loadImage(Context context,ImageLoader imageLoader){
        if (this.imageLoaderStrategy==null){
            return;
        }
            this.imageLoaderStrategy.loadImage(context,imageLoader);
    }
}
