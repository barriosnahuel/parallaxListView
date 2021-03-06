package com.barriosnahuel.expandonscroll.listener.impl;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.barriosnahuel.expandonscroll.ExpandOnScrollHandler;
import com.barriosnahuel.expandonscroll.animation.ResetAnimation;
import com.barriosnahuel.expandonscroll.listener.ExpandOnScrollListener;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ExpandOnScrollListenerImpl implements ExpandOnScrollListener {

    /**
     * Used for log messages.
     */
    private static final String TAG = "ExpandOnScrollListenerImpl";

    private static final long ANIMATION_DURATION = 300;

    private final int paddingTop;
    private final int initialHeight;
    private final View viewToExpand;
    private final int id;
    private final ExpandOnScrollHandler.ResetMethod resetMethod;
    private int maxHeight = -1;

    /**
     * TODO : Add documentation!!
     *
     * @param paddingTop
     * @param initialHeight
     * @param maxHeight
     * @param viewToExpand
     * @param resetMethod
     */
    public ExpandOnScrollListenerImpl(int id, int paddingTop, int initialHeight, int maxHeight, View viewToExpand, ExpandOnScrollHandler.ResetMethod resetMethod) {
        this.id = id;
        this.paddingTop = paddingTop;
        this.initialHeight = initialHeight;
        this.maxHeight = maxHeight;
        this.viewToExpand = viewToExpand;
        this.resetMethod = resetMethod;
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (viewToExpand.getHeight() <= maxHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (viewToExpand.getHeight() - deltaY / 2 >= initialHeight) {
                    int newHeight = viewToExpand.getHeight() - deltaY / 2 < maxHeight ? viewToExpand.getHeight() - deltaY / 2 : maxHeight;
                    Log.d(TAG, "Updating height from " + viewToExpand.getHeight() + ", to " + newHeight + ". deltaY= " + deltaY + "; Listener: " + id);
                    updateGUI(newHeight);
                }
            } else {
                if (viewToExpand.getHeight() > initialHeight) {
                    int newHeight = viewToExpand.getHeight() - deltaY > initialHeight ? viewToExpand.getHeight() - deltaY : initialHeight;
                    updateGUI(newHeight);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * TODO : Add documentation!!
     *
     * @param newHeight
     */
    private void updateGUI(int newHeight) {
        viewToExpand.getLayoutParams().height = newHeight;
        viewToExpand.requestLayout();
    }

    @Override
    public void onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            switch (resetMethod) {
                case RESET:
                    resetImageToInitialHeight();
                    break;
                case NO_RESET:
                    break;
                default:
                    resetImageToInitialHeight();
            }
        }
    }

    private void resetImageToInitialHeight() {
        Log.v(TAG, "==> resetImageToInitialHeight");

        if (initialHeight - 1 < viewToExpand.getHeight()) {
            final View viewToAnimate = viewToExpand;

            ResetAnimation animation = new ResetAnimation(viewToAnimate, initialHeight);
            animation.setDuration(ANIMATION_DURATION);
            viewToAnimate.startAnimation(animation);
        }
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.v(TAG, "==> onScrollChanged");
        //  TODO : Delete this method
    }
}
