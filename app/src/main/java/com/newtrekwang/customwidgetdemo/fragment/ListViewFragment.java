package com.newtrekwang.customwidgetdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.newtrekwang.customwidgetdemo.PullRefreshLayout;
import com.newtrekwang.customwidgetdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListViewFragment extends Fragment {
    @BindView(R.id.listViewFragment_listView)
    ListView listViewFragmentListView;
    @BindView(R.id.listViewFragment_pullRefreshLayout)
    PullRefreshLayout listViewFragmentPullRefreshLayout;
    Unbinder unbinder;

    private List<Map<String,String>> mapList;

    private SimpleAdapter simpleAdapter;

    public ListViewFragment() {
        initData();
    }
    private void initData(){
        mapList=new ArrayList<>();
        for (int i=0;i<20;i++){
            Map<String,String> map=new HashMap<>();
            map.put("key",Integer.toString(i));
            mapList.add(map);
        }



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleAdapter=new SimpleAdapter(getActivity(),mapList,android.R.layout.simple_list_item_1,new String[]{"key"},new int []{android.R.id.text1});
//        queryData();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        listViewFragmentListView.setAdapter(simpleAdapter);
        listViewFragmentPullRefreshLayout.setRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                listViewFragmentListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("key","下拉结果，新增数据");
                        mapList.add(0,map);
                        simpleAdapter.notifyDataSetChanged();
                        listViewFragmentPullRefreshLayout.setRefreshFinished();
                    }
                },3000);
            }

            @Override
            public void loadMoreFinished() {
                listViewFragmentListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("key","上拉结果，新增数据");
                        mapList.add(map);
                        simpleAdapter.notifyDataSetChanged();
                        listViewFragmentPullRefreshLayout.setLoadMoreFinished();
                    }
                },3000);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
