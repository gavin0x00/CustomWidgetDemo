package com.newtrekwang.customwidgetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.mainActivty_listView)
    ListView mainActivtyListView;
    private List<Map<String,String>> list=new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        init();
    }

    private void initData() {
        Map<String,String> map=new HashMap<>();
        map.put("title","PullRefreshLayout");
        list.add(map);
    }

    private void init() {
        simpleAdapter=new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,new String[]{"title"}, new int[]{android.R.id.text1});
        mainActivtyListView.setAdapter(simpleAdapter);
        mainActivtyListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            default:
                break;
        }
    }
}
