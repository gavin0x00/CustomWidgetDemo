package com.newtrekwang.customwidgetdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.imageLoader.GlideImageLoaderProvider;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoader;
import com.newtrekwang.customwidgetdemo.imageLoader.ImageLoaderUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.imageActivity_img)
    ImageView imageActivityImg;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    imageLoader= new ImageLoader.Builder()
                .setImageUrl("https://developer.android.google.cn/images/home/nougat_bg.jpg")
                .setImageView(imageActivityImg).create();
//        这一步可在Application进行设置
        ImageLoaderUtil.getInstance().setLoadImgStrategy(new GlideImageLoaderProvider());
//        先默认以Glide为加载策略显示
        ImageLoaderUtil.getInstance().loadImage(ImageActivity.this,imageLoader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.imageactivity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.imageActivity_menu_glide:
                imageLoader= new ImageLoader.Builder()
                        .setImageUrl("https://developer.android.google.cn/images/home/nougat_bg.jpg")
                        .setImageView(imageActivityImg).create();
                //        这一步可在Application进行设置
                ImageLoaderUtil.getInstance().setLoadImgStrategy(new GlideImageLoaderProvider());
                ImageLoaderUtil.getInstance().loadImage(ImageActivity.this,imageLoader);
                return true;
            case R.id.imageActivity_menu_picasso:
                return true;
            case R.id.imageActivity_menu_fresco:
                return true;
            default:
                return  false;
        }
    }
}
