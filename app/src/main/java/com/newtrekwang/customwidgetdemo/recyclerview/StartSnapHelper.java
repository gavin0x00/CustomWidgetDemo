package com.newtrekwang.customwidgetdemo.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * recyclerView item对齐显示助手类，方便实现ViewPager效果
 */
public class StartSnapHelper extends LinearSnapHelper {

    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    /**
     * 计算最终对齐要移动的距离，返回一个长度为 2 的int 数组 out，out[0] 为 x 方向移动的距离，out[1] 为 y 方向移动的距离。
     */
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out=new int[2];
//        如果是水平方向滚动的，则计算水平方向需要移动的距离，否则水平方向的移动距离为0
        if (layoutManager.canScrollHorizontally()){
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        }else {
            out[0]=0;
        }

        // 如果是竖直方向滚动的，则计算竖直方向需要移动的距离，否则竖直方向的移动距离为0
        if (layoutManager.canScrollVertically()) {
            out[1] =distanceToStart(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

/**
 * 找到要对齐的目标View, 最终的逻辑在findCenterView 方法里
 *  规则是：循环LayoutManager的所有子元素，计算每个 childView的
 *  中点距离Parent 的中点，找到距离最近的一个，就是需要居中对齐的目标View
 */
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager){
            if (layoutManager.canScrollVertically()) {
                return findStartView(layoutManager, getVerticalHelper(layoutManager));
            } else if (layoutManager.canScrollHorizontally()) {
                return findStartView(layoutManager, getHorizontalHelper(layoutManager));
            }
        }
        return super.findSnapView(layoutManager);
    }

    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {
        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            //需要判断是否是最后一个Item，如果是最后一个则不让对齐，以免出现最后一个显示不完全。
            boolean isLastItem = ((LinearLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1;

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                    return null;
                } else {
                    return layoutManager.findViewByPosition(firstChild + 1);
                }
            }
        }
        return super.findSnapView(layoutManager);
    }

    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;

    }
}
