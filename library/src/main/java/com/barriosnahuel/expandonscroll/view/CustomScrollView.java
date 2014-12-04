package com.barriosnahuel.expandonscroll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.barriosnahuel.expandonscroll.ExpandOnScrollHandler;


/**
 * Created by nbarrios on 27/11/14.
 *
 * Most of the code was taken from {@link com.barriosnahuel.expandonscroll.view.ParallaxScrollListView}.
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
        boolean isCollapseAnimation = expandOnScrollHandler.getExpandOnScrollListener().overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

        return isCollapseAnimation || super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        expandOnScrollHandler.getExpandOnScrollListener().onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        expandOnScrollHandler.getExpandOnScrollListener().onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setExpandOnScrollHandler(ExpandOnScrollHandler expandOnScrollHandler) {
        this.expandOnScrollHandler = expandOnScrollHandler;
    }

}
