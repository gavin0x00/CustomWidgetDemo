package com.newtrekwang.customwidgetdemo.imageLoader;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.newtrekwang.customwidgetdemo.utils.NetWorkUtil;
import com.newtrekwang.customwidgetdemo.utils.SettingUtil;

import java.io.InputStream;
/**
 * 封装Glide提供的加载策略
 */
public class GlideImageLoaderProvider implements BaseImageLoaderStrategy {
    private static final String TAG = "GlideImageLoaderProvide>>>>>>";

    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
            boolean flag= SettingUtil.isOnlyWifiLoadImg();
//       如果没有设置wifi下才加载图片，则直接加载
            if (!flag){
                loadNormal(context,imageLoader);
                return;
            }
//       如果没有设置wifi下才加载图片，则具体再判断
        ImageLoaderUtil.NetLoadType strategy=imageLoader.getWifiStrategy();
        if (strategy== ImageLoaderUtil.NetLoadType.LOAD_STRATEGY_NORMAL){
            loadNormal(context,imageLoader);
        }else if (strategy== ImageLoaderUtil.NetLoadType.LOAD_STRATEGY_ONLY_WIFI){
            if (NetWorkUtil.getAPNType(context)==NetWorkUtil.NETSTATE_WIFI){
//                有wifi,正常加载
                loadNormal(context,imageLoader);
            }else {
//                无wifi,加载缓存
                Log.e(TAG, "loadImage: >>>>>>>>>no wifi" );
                loadCache(context,imageLoader);
            }
        }else {
            loadNormal(context,imageLoader);
        }

    }
    /**
     * load image with Glide
     */
    private void loadNormal(Context context,ImageLoader imageLoader){
        Glide.with(context).load(imageLoader.getImageUrl()).placeholder(imageLoader.getPlaceHolder()).dontAnimate().into(imageLoader.getImageView());
    }
    /**
     * load cache with Glide
     */
    private void loadCache(Context context,ImageLoader imageLoader){
        Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        return null;
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(imageLoader.getImageUrl()).placeholder(imageLoader.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageLoader.getImageView());
    }
}
