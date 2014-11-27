package com.gnod.parallaxlistview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.zoomheader.listeners.ZoomHeaderListener;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ZoomHeaderScrollView extends ScrollView {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    private ImageView imageView;

    private int headerDefaultHeight;
    private int imageViewHeight = -1;

    private ZoomHeaderListener zoomHeaderListener;

    public ZoomHeaderScrollView(Context context) {
        super(context);
        init(context);
    }

    public ZoomHeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomHeaderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        headerDefaultHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = zoomHeaderListener.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

        return isCollapseAnimation || super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        zoomHeaderListener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        zoomHeaderListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public void setParallaxImageView(ImageView iv) {
        imageView = iv;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setViewsBounds(double zoomRatio) {
        if (imageViewHeight == -1) {
            imageViewHeight = imageView.getHeight();
            if (imageViewHeight <= 0) {
                imageViewHeight = headerDefaultHeight;
            }
            double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getWidth());

            int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

            zoomHeaderListener = new ZoomHeaderListener(imageView, getPaddingTop(), imageViewHeight, drawableMaxHeight);
        }
    }

}
