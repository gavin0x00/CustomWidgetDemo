package com.newtrekwang.customwidgetdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.adapter.MyItemRecyclerViewAdapter;
import com.newtrekwang.customwidgetdemo.dialog.CustomContentViewDialogFragment;
import com.newtrekwang.customwidgetdemo.dialog.FireMisslesDialogFragment;
import com.newtrekwang.customwidgetdemo.dialog.ListDialogFragment1;
import com.newtrekwang.customwidgetdemo.dialog.ListDialogFragment2;
import com.newtrekwang.customwidgetdemo.dialog.ListDialogFragment3;
import com.newtrekwang.customwidgetdemo.dialog.ListDialogFragment4;
import com.newtrekwang.customwidgetdemo.fragment.dummy.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class DialogItemFragment extends Fragment {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;

    public DialogItemFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
             recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(getData(), mListener));
        }
        return view;
    }

    private List<DummyItem> getData(){
        List<DummyItem> list=new ArrayList<>();
        DummyItem dummyItem1=new DummyItem("1","AlertDialog","....");
        list.add(dummyItem1);
         DummyItem dummyItem2=new DummyItem("2","ListDialog_1 列表","....");
        list.add(dummyItem2);
        DummyItem dummyItem3=new DummyItem("3","ListDialog_2 永久性多选列表","....");
        list.add(dummyItem3);
        DummyItem dummyItem4=new DummyItem("4","ListDialog_3 永久性单选列表","....");
        list.add(dummyItem4);
        DummyItem dummyItem5=new DummyItem("5","ListDialog_4 列表(自定义adapter)","....");
        list.add(dummyItem5);
        DummyItem dummyItem6=new DummyItem("6","自定义ContentView的Dialog","....");
        list.add(dummyItem6);
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener =new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(DummyItem item,int position) {
              switch (position){
                  case 0:
                      FireMisslesDialogFragment fireMisslesDialogFragment=new FireMisslesDialogFragment();
                     fireMisslesDialogFragment.show(fragmentManager," FireMisslesDialogFragment");
                      break;
                  case 1:
                      ListDialogFragment1 listDialogFragment1=new ListDialogFragment1();
                      listDialogFragment1.show(fragmentManager,"ListDialogFragment1");
                      break;
                  case 2:
                      ListDialogFragment2 listDialogFragment2=new ListDialogFragment2();
                      listDialogFragment2.show(fragmentManager,"ListDialogFragment2");
                      break;
                  case 3:
                      ListDialogFragment3 listDialogFragment3=new ListDialogFragment3();
                      listDialogFragment3.show(fragmentManager,"ListDialogFragment3");
                      break;
                  case 4:
                      ListDialogFragment4 listDialogFragment4=new ListDialogFragment4();
                      listDialogFragment4.show(fragmentManager,"ListDialogFragment4");
                      break;
                  case 5:
                      CustomContentViewDialogFragment  customContentViewDialogFragment=new CustomContentViewDialogFragment();
                      customContentViewDialogFragment.show(fragmentManager,"CustomContentViewDialogFragment");
                      break;
                  default:
                      break;
              }
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        recyclerView=null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item,int positon);
    }
}
