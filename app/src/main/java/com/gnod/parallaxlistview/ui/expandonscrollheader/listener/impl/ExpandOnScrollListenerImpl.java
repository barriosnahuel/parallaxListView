package com.gnod.parallaxlistview.ui.expandonscrollheader.listener.impl;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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

    private final ImageView imageView;
    private final int paddingTop;
    private final int imageViewHeight;
    private final ViewPager viewPager;
    private final int id;
    private final ExpandOnScrollHandler.ResetMethod resetMethod;
    private int drawableMaxHeight = -1;

    /**
     * TODO : Add documentation!!
     *
     * @param imageView
     * @param paddingTop
     * @param imageViewHeight
     * @param drawableMaxHeight
     * @param viewPager
     * @param resetMethod
     */
    public ExpandOnScrollListenerImpl(ImageView imageView, int paddingTop, int imageViewHeight, int drawableMaxHeight, ViewPager viewPager, ExpandOnScrollHandler.ResetMethod resetMethod) {
        this.imageView = imageView;
        this.id = this.imageView.getId();
        this.paddingTop = paddingTop;
        this.imageViewHeight = imageViewHeight;
        this.drawableMaxHeight = drawableMaxHeight;
        this.viewPager = viewPager;
        this.resetMethod = resetMethod;
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.v(TAG, "overScrollBy...");

        if (imageView.getHeight() <= drawableMaxHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (imageView.getHeight() - deltaY / 2 >= imageViewHeight) {
                    int newHeight = imageView.getHeight() - deltaY / 2 < drawableMaxHeight ? imageView.getHeight() - deltaY / 2 : drawableMaxHeight;
                    Log.d(TAG, "Updating height from " + imageView.getHeight() + ", to " + newHeight + ". deltaY= " + deltaY + "; Listener: " + id);
                    updateGUI(newHeight);
                }
            } else {
                if (imageView.getHeight() > imageViewHeight) {
                    int newHeight = imageView.getHeight() - deltaY > imageViewHeight ? imageView.getHeight() - deltaY : imageViewHeight;
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
        imageView.getLayoutParams().height = newHeight;

        if (viewPager == null) {
            imageView.requestLayout();
        } else {
            viewPager.getLayoutParams().height = newHeight;
            viewPager.requestLayout();
        }
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

        if (imageViewHeight - 1 < imageView.getHeight()) {
            final View viewToAnimate = viewPager != null ? viewPager : imageView;

            ResetAnimation animation = new ResetAnimation(viewToAnimate, imageViewHeight);
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
//            firstView = viewPager;
//        } else {
////            Log.d(TAG, "imageViewParent id: " + firstView.getId());
////            Log.d(TAG, "imageView id: " + imageView.getId());
//        }
//
//        // firstView.getTop < getPaddingTop means imageView will be covered by top padding, so we can layout it to make it shorter
//        if (firstView != null && firstView.getTop() < paddingTop && imageView.getHeight() > imageViewHeight) {
//            Log.d(TAG, "This log message never appears... should I delete this method (entirely!)");//  TODO : Delete this method
//
//            imageView.getLayoutParams().height = Math.max(imageView.getHeight() - (paddingTop - firstView.getTop()), imageViewHeight);
//
//            // to set the firstView.mTop to 0, maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
//            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
//            imageView.requestLayout();
//        }
    }
}
