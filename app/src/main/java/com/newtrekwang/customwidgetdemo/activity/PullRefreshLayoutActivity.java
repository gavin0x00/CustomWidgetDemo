package com.newtrekwang.customwidgetdemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.fragment.ListViewFragment;
import com.newtrekwang.customwidgetdemo.fragment.RecyclerViewFragment;
import com.newtrekwang.customwidgetdemo.fragment.ScrollViewFragment;

import java.util.ArrayList;
import java.util.List;

public class PullRefreshLayoutActivity extends AppCompatActivity implements ScrollViewFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager;
    private List<Fragment>  fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getSupportFragmentManager();
        fragmentList=new ArrayList<>();
        fragmentList.add(new RecyclerViewFragment());
        fragmentList.add(new ListViewFragment());
        fragmentList.add(new ScrollViewFragment());

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content,fragmentList.get(0));
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.pullrefreshlayoutactivity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pullRefresh_ListView:
                FragmentTransaction fragmentTransaction_1=fragmentManager.beginTransaction();
                fragmentTransaction_1.replace(android.R.id.content,fragmentList.get(1));
                fragmentTransaction_1.commit();
                return true;
            case R.id.pullRefresh_RecyclerView:
                FragmentTransaction fragmentTransaction_2=fragmentManager.beginTransaction();
                fragmentTransaction_2.replace(android.R.id.content,fragmentList.get(0));
                fragmentTransaction_2.commit();
                return true;
            case R.id.pullRefresh_ScrollView:
                FragmentTransaction fragmentTransaction_3=fragmentManager.beginTransaction();
                fragmentTransaction_3.replace(android.R.id.content,fragmentList.get(2));
                fragmentTransaction_3.commit();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentList=null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
