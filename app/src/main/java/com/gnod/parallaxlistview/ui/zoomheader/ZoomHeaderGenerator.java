package com.gnod.parallaxlistview.ui.zoomheader;

import android.view.MotionEvent;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ZoomHeaderGenerator {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    private OnOverScrollByListener onOverScrollByListener;
    private OnScrollChangedListener onScrollChangedListener;
    private OnTouchEventListener onTouchEventListener;

    // Interfaces defininition
    // -----------------------

    public interface OnOverScrollByListener {
        boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public interface OnTouchEventListener {
        public void onTouchEvent(MotionEvent ev);
    }

    // Getters & setters
    // -----------------------

    public OnOverScrollByListener getOnOverScrollByListener() {
        return onOverScrollByListener;
    }

    public OnScrollChangedListener getOnScrollChangedListener() {
        return onScrollChangedListener;
    }

    public OnTouchEventListener getOnTouchEventListener() {
        return onTouchEventListener;
    }
}
