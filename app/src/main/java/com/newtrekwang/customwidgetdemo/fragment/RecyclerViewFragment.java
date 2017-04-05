package com.newtrekwang.customwidgetdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtrekwang.customwidgetdemo.PullRefreshLayout;
import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.SimpleAdapter_1;
import com.newtrekwang.customwidgetdemo.adapter.base.BaseRecyclerViewAdapter;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecyclerViewFragment extends Fragment implements BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<String>,PullRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pullRefreshView)
    PullRefreshLayout pullRefreshView;
    Unbinder unbinder;
    private List<String> list;
    private SimpleAdapter_1 simpleAdapter_1;

    public RecyclerViewFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("" + i);
        }
        simpleAdapter_1 = new SimpleAdapter_1(getActivity());
        simpleAdapter_1.setDatas(list);
        simpleAdapter_1.setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(simpleAdapter_1);

        pullRefreshView.setRefreshListener(this);
        return view;
    }

    @Override
    public void onItemClick(View view, String bean) {
        new ToastBuilder().setContentString(bean).createDefaultTextToast(getActivity()).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshFinished() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullRefreshView.setRefreshFinished();
                simpleAdapter_1.insertData(0,"下拉新增数据");
            }
        },3000);
    }

    @Override
    public void loadMoreFinished() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullRefreshView.setLoadMoreFinished();
                simpleAdapter_1.addData("上拉新增数据");
            }
        },3000);
    }
}
