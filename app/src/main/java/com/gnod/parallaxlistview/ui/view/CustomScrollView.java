package com.gnod.parallaxlistview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;

/**
 * Created by nbarrios on 27/11/14.
 */
public class CustomScrollView extends ScrollView {

    private ExpandOnScrollHandler expandOnScrollHandler;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = expandOnScrollHandler.getZoomHeaderLister().overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

        return isCollapseAnimation || super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        expandOnScrollHandler.getZoomHeaderLister().onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        expandOnScrollHandler.getZoomHeaderLister().onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setExpandOnScrollHandler(ExpandOnScrollHandler expandOnScrollHandler) {
        this.expandOnScrollHandler = expandOnScrollHandler;
    }

}
