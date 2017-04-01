package com.newtrekwang.customwidgetdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by WJX .
 * Desc: 支持下拉刷新，上拉加载的控件
 * Created on 2017/2/28 12:45.
 * Mail:408030208@qq.com
 */
public class PullRefreshLayout extends ViewGroup {
    //    headerView
    private View mHeader;
    //    headerTextView
    private TextView mHeaderText;
    //    headerImage
    private ImageView mHeaderArrow;
    //    headerProgressBar
    private ProgressBar mHeaderProgressBar;

    //    FooterView
    private View mFooter;
    //    footerTextView
    private TextView mFooterText;
    //    footerProgressBar
    private ProgressBar mFooterProgressBar;

    //      内容高度
    private int mLayoutContentHeight;
    //    headerView高度
    private int mEffectiveHeaderHeight;
    //    footerView高度
    private int mEffictiveFooterHeight;
    //    上次手指移出时y坐标值
    private int mlastMoveY;
    //   拦截时上次记录事件的y
    private int mLastYIntercept;
    //   最后子View的索引
    private int lastChildIndex;
    //    状态
    private Status mStatus = Status.NORMAL;
    //   拉动状态监听器
    private OnRefreshListener mRefreshListener;


    public PullRefreshLayout(Context context) {
        super(context);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 更新状态
     */
    private void updateStatus(Status status) {
        mStatus = status;
    }

    /**
     * 状态枚举
     */
    private enum Status {
        NORMAL, TRY_REFRESH, REFRESHING, TRY_LOADMORE, LOADING
    }

    /**
     * 拉动监听器
     */
    public interface OnRefreshListener {
        //    下拉完调用
        void refreshFinished();

        //    上拉完调用
        void loadMoreFinished();
    }

    /**
     * 设置监听器
     */
    public void setRefreshListener(OnRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }


    /**
     * 当view的所有child从xml中被初始化后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        lastChildIndex = getChildCount() - 1;
        addHeader();
        addFooter();
    }

    /**
     * 添加HeaderView
     */
    private void addHeader() {
        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.pull_header, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mHeader, params);

        mHeaderText = (TextView) findViewById(R.id.header_text);
        mHeaderProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);
        mHeaderArrow = (ImageView) findViewById(R.id.header_arrow);
    }

    /**
     * 添加FooterView
     */
    private void addFooter() {
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.pull_footer, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mFooter, params);
        mFooterText = (TextView) findViewById(R.id.footer_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.footer_progressbar);
    }


    /**
     * 测量，也让子View测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) {
//                如果为Header,把它放在屏幕可视区域上边
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
                mEffectiveHeaderHeight = child.getHeight();
            } else if (child == mFooter) {
//                如果为Footer，把它放在屏幕可视区域下边
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                mEffictiveFooterHeight = child.getHeight();
            } else {
//                否则为内容区域，放在中间
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    if (child instanceof ScrollView) {
                        mLayoutContentHeight += getMeasuredHeight();
                        continue;
                    }
                    mLayoutContentHeight += child.getMeasuredHeight();
                }
            }
        }
    }

    /**
     * 事件消费
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        时间y坐标
        int y = (int) event.getY();

        // 正在刷新或加载更多，就消费事件
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                记录上次点下时的y坐标值
                mlastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
//                更新滑动距离，就是上次点下的y-滑动时的y
                int dy = mlastMoveY - y;
                // 一直在下拉
                if (getScrollY() <= 0 && dy <= 0) {
                    if (mStatus == Status.TRY_LOADMORE) {
                        scrollBy(0, dy / 30);
                    } else {
                        scrollBy(0, dy / 3);
                    }
                }
                // 一直在上拉
                else if (getScrollY() >= 0 && dy >= 0) {
                    if (mStatus == Status.TRY_REFRESH) {
                        scrollBy(0, dy / 30);
                    } else {
                        scrollBy(0, dy / 3);
                    }
                } else {
                    scrollBy(0, dy / 3);
                }

                beforeRefreshing(dy);
                beforeLoadMore();

                break;
            case MotionEvent.ACTION_UP:
//                手放了后，下拉刷新，并且到达有效长度
                if (getScrollY() <= -mEffectiveHeaderHeight) {
                    releaseWithStatusRefresh();
                    if (mRefreshListener != null) {
                        mRefreshListener.refreshFinished();
                    }
                }
                // 手放了后，上拉加载更多，达到有效长度
                else if (getScrollY() >= mEffictiveFooterHeight) {
                    releaseWithStatusLoadMore();
                    if (mRefreshListener != null) {
                        mRefreshListener.loadMoreFinished();
                    }
                } else {
//                    没有达到有效长度，就复位
                    releaseWithStatusTryRefresh();
                    releaseWithStatusTryLoadMore();
                }
                break;
        }
        mlastMoveY = y;
        return super.onTouchEvent(event);
    }
/**
 * 事件拦截
 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int y = (int) event.getY();
//        状态在刷新中，不拦截
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return false;
        }
//        点下不拦截
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
//       拦截时需要记录点击位置，不然下一次滑动会出错
                mlastMoveY = y;
                intercept = false;
                break;
            }
//            移动时要具体判断
            case MotionEvent.ACTION_MOVE: {
//                是手指下滑
                if (y > mLastYIntercept) {
                    View child = getChildAt(0);
                    intercept = getRefreshIntercept(child);
                    if (intercept) {
                        updateStatus(mStatus.TRY_REFRESH);
                    }
                } else if (y < mLastYIntercept) {
//                    是手指上滑
                    View child = getChildAt(lastChildIndex);
                    intercept = getLoadMoreIntercept(child);
                    if (intercept) {
                        updateStatus(mStatus.TRY_LOADMORE);
                    }
                } else {
                    intercept = false;
                }
                break;
            }
//            手指放开不拦截
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }
//        记录上次事件的y值
        mLastYIntercept = y;
        return intercept;
    }

 /**
  * 汇总判断 刷新是否拦截
  */
    private boolean getRefreshIntercept(View child) {
        boolean intercept = false;
        if (child instanceof AdapterView) {
            intercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewRefreshIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewRefreshIntercept(child);
        }
        return intercept;
    }

/**
 * 汇总判断 加载是否拦截
 */
    private boolean getLoadMoreIntercept(View child) {
        boolean intercept = false;
        if (child instanceof AdapterView) {
            intercept = adapterViewLoadMoreIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewLoadMoreIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewLoadMoreIntercept(child);
        }
        return intercept;
    }

  /**
   * 判断AdapterView下拉刷新是否拦截
   */
    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            intercept = false;
        }
        return intercept;
    }

    /**
     * AdapterView加载更多是否拦截
     */
    private boolean adapterViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1 &&
                (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() >= getMeasuredHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断ScrollView刷新是否拦截
     */
    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断ScrollView加载更多是否拦截
     */
    private boolean scrollViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);
        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断RecyclerView刷新是否拦截
     */
    private boolean recyclerViewRefreshIntercept(View child) {
        boolean intercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断RecyclerView加载更多是否拦截
     */
    private boolean recyclerViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 修改header的状态
     */
    public void beforeRefreshing(float dy) {
        //计算旋转角度,根据滑动的距离判断
        int scrollY = Math.abs(getScrollY());

        scrollY = scrollY > mEffectiveHeaderHeight ? mEffectiveHeaderHeight : scrollY;
//        180比例
        float angle = (float) (scrollY * 1.0 / mEffectiveHeaderHeight * 180);
        mHeaderArrow.setRotation(angle);
//        也是判断滑动距离设置提示文字
        if (getScrollY() <= -mEffectiveHeaderHeight) {
            mHeaderText.setText("松开刷新");
        } else {
            mHeaderText.setText("下拉刷新");
        }
    }

    /**
     * 修改Footer的状态
     */
    public void beforeLoadMore() {
        if (getScrollY() >= mEffectiveHeaderHeight) {
            mFooterText.setText("松开加载更多");
        } else {
            mFooterText.setText("上拉加载更多");
        }
    }

    /**
     * 设置下拉刷新结束
     */
    public void setRefreshFinished() {
        scrollTo(0, 0);
        mHeaderText.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(GONE);
        mHeaderArrow.setVisibility(VISIBLE);
        updateStatus(Status.NORMAL);
    }

    /**
     * 设置上拉加载结束
     */
    public void setLoadMoreFinished() {
        mFooterText.setText("上拉加载");
        mFooterProgressBar.setVisibility(GONE);
        scrollTo(0, 0);
        updateStatus(Status.NORMAL);
    }

    /**
     * 下拉达不到有效长度，复位
     */
    private void releaseWithStatusTryRefresh() {
        scrollBy(0, -getScrollY());
        mHeaderText.setText("下拉刷新");
        updateStatus(Status.NORMAL);
    }

    /**
     * 上拉达不到有效长度，复位
     */
    private void releaseWithStatusTryLoadMore() {
        scrollBy(0, -getScrollY());
        mFooterText.setText("上拉加载更多");
        updateStatus(Status.NORMAL);
    }

    /**
     * 下拉达到有效长度，滚动到标准位置
     */
    private void releaseWithStatusRefresh() {
        scrollTo(0, -mEffectiveHeaderHeight);
        mHeaderProgressBar.setVisibility(VISIBLE);
        mHeaderArrow.setVisibility(GONE);
        mHeaderText.setText("正在刷新");
        updateStatus(Status.REFRESHING);
    }

    /**
     * 上拉达到有效长度，滚动到标准位置
     */
    private void releaseWithStatusLoadMore() {
        scrollTo(0, mEffictiveFooterHeight);
        mFooterText.setText("正在加载");
        mFooterProgressBar.setVisibility(VISIBLE);
        updateStatus(Status.LOADING);
    }


}
