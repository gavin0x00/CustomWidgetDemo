package com.newtrekwang.customwidgetdemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.ADViewPagerAdapter;
import com.newtrekwang.customwidgetdemo.adapter.SimpleAdapter_2;
import com.newtrekwang.customwidgetdemo.adapter.base.BaseRecyclerViewAdapter;
import com.newtrekwang.customwidgetdemo.recyclerview.StartSnapHelper;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;
import com.newtrekwang.customwidgetdemo.viewpager.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment implements BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<String> {


    @BindView(R.id.autoViewPager)
    AutoScrollViewPager autoViewPager;
    Unbinder unbinder;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.fragment_view_pager_recyclerView)
    RecyclerView fragmentViewPagerRecyclerView;

    private ADViewPagerAdapter adViewPagerAdapter;
    private List<String> urlList;
    private SimpleAdapter_2 simpleAdapter_2;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlList = new ArrayList<>();
        urlList.add("http://uploadfile.deskcity.org/2015/0907/20150907025557427.jpg");
        urlList.add("http://img.67.com/upload/images/2016/05/26/aGV5YW96aG91MTQ2NDI0Njk3NQ==.jpg");
        urlList.add("http://img.anzow.com/picture/2012815/2012081534555863.jpg");

        adViewPagerAdapter = new ADViewPagerAdapter(getActivity());
        adViewPagerAdapter.setUrlLists(urlList);

       simpleAdapter_2=new SimpleAdapter_2(getActivity());
        simpleAdapter_2.setDatas(urlList);
        simpleAdapter_2.setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        autoViewPager.setAdapter(adViewPagerAdapter);
        indicator.setViewPager(autoViewPager);

        autoViewPager.setInterval(3000);
        autoViewPager.startAutoScroll();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        fragmentViewPagerRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentViewPagerRecyclerView.setAdapter(simpleAdapter_2);

        StartSnapHelper startSnapHelper=new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(fragmentViewPagerRecyclerView);
        return view;
    }

    @Override
    public void onPause() {
        if (autoViewPager.isCycle()) {
            autoViewPager.stopAutoScroll();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, String bean) {
        new ToastBuilder().setContentString(bean).createDefaultTextToast(getContext()).show();
    }
}
