package com.newtrekwang.customwidgetdemo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newtrekwang.customwidgetdemo.PullRefreshLayout;
import com.newtrekwang.customwidgetdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScrollViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScrollViewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.scrollViewFragment_scollView)
    ScrollView scrollViewFragmentScollView;
    @BindView(R.id.scrollViewFragment_pullRefreshLayout)
    PullRefreshLayout scrollViewFragmentPullRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.scrollViewFragment_textView)
    TextView scrollViewFragmentTextView;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    scrollViewFragmentTextView.setText("下拉刷新加载完成！");
                    scrollViewFragmentPullRefreshLayout.setRefreshFinished();
                    break;
                case 200:
                    scrollViewFragmentTextView.setText("上拉刷新加载完成！");
                    scrollViewFragmentPullRefreshLayout.setLoadMoreFinished();
                    break;
                default:
                    break;
            }
        }
    };

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScrollViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScrollViewFragment.
     */
    public static ScrollViewFragment newInstance(String param1, String param2) {
        ScrollViewFragment fragment = new ScrollViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scroll_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        scrollViewFragmentPullRefreshLayout.setRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                handler.sendEmptyMessageDelayed(100,3000);
            }

            @Override
            public void loadMoreFinished() {
                handler.sendEmptyMessageDelayed(200,3000);
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
