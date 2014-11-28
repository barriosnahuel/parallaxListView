package com.gnod.parallaxlistview.ui.expandonscrollheader.listener;

import android.view.MotionEvent;

/**
 * TODO : Add documentation!!
 * <p/>
 * Created by nbarrios on 28/11/14.
 */
public interface ExpandOnScrollListener {

    /**
     * Same as {@link android.view.View#overScrollBy(int, int, int, int, int, int, int, int,
     * boolean)}.
     */
    boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);

    /**
     * Same as {@link android.view.View#onScrollChanged(int, int, int, int)}.
     */
    void onScrollChanged(int l, int t, int oldl, int oldt);

    /**
     * Same as {@link android.widget.ScrollView#onTouchEvent(android.view.MotionEvent)}.
     */
    void onTouchEvent(MotionEvent ev);

}
