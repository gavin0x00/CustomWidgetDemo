package com.newtrekwang.customwidgetdemo.imageLoader;

import android.widget.ImageView;

import com.newtrekwang.customwidgetdemo.R;
/**
 * 负责保存加载参数的ImageLoader类，采用Builder模式
 * 因为这些参数在图片加载框架里是通用的，所以把他们抽离出来
 */
public class ImageLoader {
    private String imageUrl;//需要解析的图片url
    private ImageLoaderUtil.LoadSizeType type;//类型（大图，中图，小图）
    private int placeHolder;//当没有成功加载的时候显示的图片
    private ImageView imageView;//imageView对象
    private ImageLoaderUtil.NetLoadType wifiStrategy;//加载策略，是否在wifi下加载

    private ImageLoader (Builder builder){
        this.imageUrl=builder.imageUrl;
        this.imageView=builder.imageView;
        this.type=builder.type;
        this.placeHolder=builder.placeHolder;
        this.wifiStrategy=builder.wifiStrategy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public ImageLoaderUtil.LoadSizeType getType() {
        return type;
    }

    public void setType(ImageLoaderUtil.LoadSizeType type) {
        this.type = type;
    }

    public ImageLoaderUtil.NetLoadType getWifiStrategy() {
        return wifiStrategy;
    }

    public void setWifiStrategy(ImageLoaderUtil.NetLoadType wifiStrategy) {
        this.wifiStrategy = wifiStrategy;
    }

    public static class Builder {
        private String imageUrl;//需要解析的图片url
        private ImageLoaderUtil.LoadSizeType type;//类型（大图，中图，小图）
        private int placeHolder;//当没有成功加载的时候显示的图片
        private ImageView imageView;//imageView对象
        private ImageLoaderUtil.NetLoadType wifiStrategy;//加载策略，是否在wifi下加载

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setType(ImageLoaderUtil.LoadSizeType type) {
            this.type = type;
            return this;
        }

        public Builder setPlaceHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder setWifiStrategy(ImageLoaderUtil.NetLoadType wifiStrategy) {
            this.wifiStrategy = wifiStrategy;
            return this;
        }
    /**
     * 初始化默认参数
     */
        public Builder(){
            this.type=ImageLoaderUtil.LoadSizeType.PIC_NORMAL;
            this.imageUrl="";
            this.imageView=null;
            this.placeHolder= R.mipmap.ic_launcher;
            this.wifiStrategy= ImageLoaderUtil.NetLoadType.LOAD_STRATEGY_NORMAL;
        }

        /**
         * 构建ImageLoader并返回
         */
        public ImageLoader create(){
            return new ImageLoader(this);
        }

    }
}
