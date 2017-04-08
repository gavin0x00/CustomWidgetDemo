package com.newtrekwang.customwidgetdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.fragment.ContentProviderFragment;
import com.newtrekwang.customwidgetdemo.fragment.DialogItemFragment;
import com.newtrekwang.customwidgetdemo.fragment.ExpandTextViewFragment;
import com.newtrekwang.customwidgetdemo.fragment.ViewPagerFragment;

public class CommonActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getSupportFragmentManager();
        initData();
    }

    private void initData() {
        int key=getIntent().getIntExtra("key",100);
        switch (key){
            case 100:
                FragmentTransaction fragmentTransaction_1=fragmentManager.beginTransaction();
                ExpandTextViewFragment expandTextViewFragment=new ExpandTextViewFragment();
                fragmentTransaction_1.replace(android.R.id.content,expandTextViewFragment);
                fragmentTransaction_1.commit();
                break;
            case 101:
                FragmentTransaction fragmentTransaction_2=fragmentManager.beginTransaction();
                ViewPagerFragment viewPagerFragment=new ViewPagerFragment();
                fragmentTransaction_2.replace(android.R.id.content,viewPagerFragment);
                fragmentTransaction_2.commit();
                break;
            case 102:
                FragmentTransaction fragmentTransaction_3=fragmentManager.beginTransaction();
                DialogItemFragment dialogItemFragment=new DialogItemFragment();
                fragmentTransaction_3.replace(android.R.id.content,dialogItemFragment);
                fragmentTransaction_3.commit();
                break;
            case 103:
                FragmentTransaction fragmentTransaction_4=fragmentManager.beginTransaction();
                ContentProviderFragment contentProviderFragment=new ContentProviderFragment();
                fragmentTransaction_4.replace(android.R.id.content,contentProviderFragment);
                fragmentTransaction_4.commit();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.commonactivity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_expandTextView:
                FragmentTransaction fragmentTransaction1=fragmentManager.beginTransaction();
                ExpandTextViewFragment expandTextViewFragment=new ExpandTextViewFragment();
                fragmentTransaction1.replace(android.R.id.content,expandTextViewFragment);
                fragmentTransaction1.commit();
                return true;
            case R.id.menu_item_autoViewPager:
                FragmentTransaction fragmentTransaction_2=fragmentManager.beginTransaction();
                ViewPagerFragment viewPagerFragment=new ViewPagerFragment();
                fragmentTransaction_2.replace(android.R.id.content,viewPagerFragment);
                fragmentTransaction_2.commit();
                return true;
            case R.id.menu_item_dialog:
                FragmentTransaction fragmentTransaction_3=fragmentManager.beginTransaction();
                DialogItemFragment dialogItemFragment=new DialogItemFragment();
                fragmentTransaction_3.replace(android.R.id.content,dialogItemFragment);
                fragmentTransaction_3.commit();
                return true;
            case R.id.menu_item_ContentProvider:
                FragmentTransaction fragmentTransaction_4=fragmentManager.beginTransaction();
                ContentProviderFragment contentProviderFragment=new ContentProviderFragment();
                fragmentTransaction_4.replace(android.R.id.content,contentProviderFragment);
                fragmentTransaction_4.commit();
                return true;
            default:
                return false;
        }
    }
}
