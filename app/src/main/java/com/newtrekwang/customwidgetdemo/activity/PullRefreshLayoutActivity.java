package com.newtrekwang.customwidgetdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newtrekwang.customwidgetdemo.R;

public class PullRefreshLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                
                return true;
            case R.id.pullRefresh_RecyclerView:
                return true;
            case R.id.pullRefresh_ScrollView:
                return true;
            default:
                return false;
        }
    }
}
