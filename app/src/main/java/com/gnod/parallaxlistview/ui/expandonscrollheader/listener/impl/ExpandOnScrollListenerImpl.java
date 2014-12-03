package com.gnod.parallaxlistview.ui.expandonscrollheader.listener.impl;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;
import com.gnod.parallaxlistview.ui.expandonscrollheader.animation.ResetAnimation;
import com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener;

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
        Log.v(TAG, "resetImageToInitialHeight...");

        if (initialHeight - 1 < viewToExpand.getHeight()) {
            final View viewToAnimate = viewToExpand;

            ResetAnimation animation = new ResetAnimation(viewToAnimate, initialHeight);
            animation.setDuration(ANIMATION_DURATION);
            viewToAnimate.startAnimation(animation);
        }
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.v(TAG, "onScrollChanged...");
//        View firstView = (View) imageView.getParent();
//
//        if (firstView == null) {
//            Log.d(TAG, "Error!");
//            firstView = viewToExpand;
//        } else {
////            Log.d(TAG, "imageViewParent id: " + firstView.getId());
////            Log.d(TAG, "imageView id: " + imageView.getId());
//        }
//
//        // firstView.getTop < getPaddingTop means imageView will be covered by top padding, so we can layout it to make it shorter
//        if (firstView != null && firstView.getTop() < paddingTop && imageView.getHeight() > initialHeight) {
//            Log.d(TAG, "This log message never appears... should I delete this method (entirely!)");//  TODO : Delete this method
//
//            imageView.getLayoutParams().height = Math.max(imageView.getHeight() - (paddingTop - firstView.getTop()), initialHeight);
//
//            // to set the firstView.mTop to 0, maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
//            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
//            imageView.requestLayout();
//        }
    }
}
