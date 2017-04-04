package com.newtrekwang.customwidgetdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.textview.ExpandTextView;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 展示ExpandTextVew的碎片
 */
public class ExpandTextViewFragment extends Fragment {

    @BindView(R.id.expandTextView_1)
    ExpandTextView expandTextView1;
    Unbinder unbinder;

    public ExpandTextViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expand_text_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        expandTextView1.setText(getString(R.string.textstring));

        expandTextView1.setOnExpandTextViewClicklistener(new ExpandTextView.OnExpandTextViewClicklistener() {
            @Override
            public void onExpandTextViewClick() {
                new ToastBuilder().setContentString("您点击了内容！")
                        .createDefaultTextToast(getActivity())
                        .show();
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
