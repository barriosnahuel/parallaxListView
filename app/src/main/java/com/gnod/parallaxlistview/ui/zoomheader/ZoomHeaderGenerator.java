package com.gnod.parallaxlistview.ui.zoomheader;

import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.gnod.parallaxlistview.ui.zoomheader.listener.ZoomHeaderListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ZoomHeaderGenerator {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    private static final int headerDefaultHeight = 160;

    private View parentView;

    private ZoomHeaderListener zoomHeaderListener;

    private List<ImageView> images;
    private SparseIntArray imagesHeight;

    /**
     * TODO : Add documentation!!
     *
     * @param parentView The parent view. It will be used to get its top padding. It should your
     *                   custom {@link android.widget.ScrollView}.
     */
    public ZoomHeaderGenerator(View parentView) {
        this.parentView = parentView;
        this.images = new ArrayList<ImageView>();
        this.imagesHeight = new SparseIntArray();
    }

    public void addImage(ImageView viewById) {
        viewById.setScaleType(ImageView.ScaleType.CENTER_CROP);
        images.add(viewById);
    }

    public void setViewsBounds(double zoomRatio) {
        int len = images.size();
        for (int index = 0; index < len; index++) {
            ImageView imageView = images.get(index);

            int imageViewHeight = imagesHeight.get(index);
            if (imageViewHeight == 0) {
                imageViewHeight = imageView.getHeight();
                if (imageViewHeight <= 0) {
                    imageViewHeight = headerDefaultHeight;
                }
                imagesHeight.put(index, imageViewHeight);


                double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getWidth());
                int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

                zoomHeaderListener = new ZoomHeaderListener(imageView, parentView.getPaddingTop(), imageViewHeight, drawableMaxHeight);
            }
        }
    }

    // Interfaces defininition
    // -----------------------

    public interface OnOverScrollByListener {
        boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public interface OnTouchEventListener {
        void onTouchEvent(MotionEvent ev);
    }

    public ZoomHeaderListener getZoomHeaderLister() {
        return zoomHeaderListener;
    }
}
