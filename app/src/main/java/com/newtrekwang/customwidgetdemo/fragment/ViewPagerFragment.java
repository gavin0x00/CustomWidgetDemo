package com.newtrekwang.customwidgetdemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.ADViewPagerAdapter;
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
public class ViewPagerFragment extends Fragment {


    @BindView(R.id.autoViewPager)
    AutoScrollViewPager autoViewPager;
    Unbinder unbinder;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private ADViewPagerAdapter adViewPagerAdapter;
    private List<String> urlList;

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
}
