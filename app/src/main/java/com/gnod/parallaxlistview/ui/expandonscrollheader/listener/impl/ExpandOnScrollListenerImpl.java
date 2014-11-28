package com.gnod.parallaxlistview.ui.expandonscrollheader.listener.impl;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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
    private int drawableMaxHeight = -1;

    /**
     * TODO : Add documentation!!
     *
     * @param imageView
     * @param paddingTop
     * @param imageViewHeight
     * @param drawableMaxHeight
     * @param viewPager
     */
    public ExpandOnScrollListenerImpl(ImageView imageView, int paddingTop, int imageViewHeight, int drawableMaxHeight, ViewPager viewPager) {
        this.imageView = imageView;
        this.paddingTop = paddingTop;
        this.imageViewHeight = imageViewHeight;
        this.drawableMaxHeight = drawableMaxHeight;
        this.viewPager = viewPager;
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        View firstView = (View) imageView.getParent();

        // firstView.getTop < getPaddingTop means imageView will be covered by top padding, so we can layout it to make it shorter
        if (firstView.getTop() < paddingTop && imageView.getHeight() > imageViewHeight) {
            imageView.getLayoutParams().height = Math.max(imageView.getHeight() - (paddingTop - firstView.getTop()), imageViewHeight);

            // to set the firstView.mTop to 0, maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            imageView.requestLayout();
        }
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (imageView.getHeight() <= drawableMaxHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (imageView.getHeight() - deltaY / 2 >= imageViewHeight) {
                    int newHeight = imageView.getHeight() - deltaY / 2 < drawableMaxHeight ? imageView.getHeight() - deltaY / 2 : drawableMaxHeight;
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
        imageView.requestLayout();

        if (viewPager != null) {
            viewPager.getLayoutParams().height = newHeight;
            viewPager.requestLayout();
        }
    }

    @Override
    public void onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (imageViewHeight - 1 < imageView.getHeight()) {
                final View viewToAnimate = viewPager != null ? viewPager : imageView;

                ResetAnimation animation = new ResetAnimation(viewToAnimate, imageViewHeight);
                animation.setDuration(ANIMATION_DURATION);
                viewToAnimate.startAnimation(animation);
            }
        }
    }
}
