package com.gnod.parallaxlistview.ui.zoomheader.listeners;

import android.widget.ImageView;

import com.gnod.parallaxlistview.ui.zoomheader.ZoomHeaderGenerator;

/**
 * Created by nbarrios on 27/11/14.
 */
public class OnOverScrollByListener implements ZoomHeaderGenerator.OnOverScrollByListener {

    private ImageView imageView;
    private int imageViewHeight = -1;
    private int drawableMaxHeight = -1;

    public OnOverScrollByListener(ImageView imageView, int imageViewHeight, int drawableMaxHeight) {
        this.imageView = imageView;
        this.imageViewHeight = imageViewHeight;
        this.drawableMaxHeight = drawableMaxHeight;
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
}
