package com.gnod.parallaxlistview.ui.zoomheader.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.gnod.parallaxlistview.ui.zoomheader.ZoomHeaderGenerator;
import com.gnod.parallaxlistview.ui.zoomheader.animation.ResetAnimation;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ZoomHeaderListener implements ZoomHeaderGenerator.OnScrollChangedListener, ZoomHeaderGenerator.OnOverScrollByListener, ZoomHeaderGenerator.OnTouchEventListener {

    private final ImageView imageView;
    private final int paddingTop;
    private final int imageViewHeight;

    private int drawableMaxHeight = -1;


    public ZoomHeaderListener(ImageView imageView, int paddingTop, int imageViewHeight, int drawableMaxHeight) {
        this.imageView = imageView;
        this.paddingTop = paddingTop;
        this.imageViewHeight = imageViewHeight;
        this.drawableMaxHeight = drawableMaxHeight;
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {

        View firstView = (View) imageView.getParent();
        // firstView.getTop < getPaddingTop means imageView will be covered by top padding,
        // so we can layout it to make it shorter
        if (firstView.getTop() < paddingTop && imageView.getHeight() > imageViewHeight) {
            imageView.getLayoutParams().height = Math.max(imageView.getHeight() - (paddingTop - firstView.getTop()), imageViewHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            imageView.requestLayout();
        }
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (imageView.getHeight() <= drawableMaxHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (imageView.getHeight() - deltaY / 2 >= imageViewHeight) {
                    imageView.getLayoutParams().height = imageView.getHeight() - deltaY / 2 < drawableMaxHeight ?
                            imageView.getHeight() - deltaY / 2 : drawableMaxHeight;
                    imageView.requestLayout();
                }
            } else {
                if (imageView.getHeight() > imageViewHeight) {
                    imageView.getLayoutParams().height = imageView.getHeight() - deltaY > imageViewHeight ?
                            imageView.getHeight() - deltaY : imageViewHeight;
                    imageView.requestLayout();
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (imageViewHeight - 1 < imageView.getHeight()) {
                ResetAnimation animation = new ResetAnimation(imageView, imageViewHeight);
                animation.setDuration(300);
                imageView.startAnimation(animation);
            }
        }
    }
}
