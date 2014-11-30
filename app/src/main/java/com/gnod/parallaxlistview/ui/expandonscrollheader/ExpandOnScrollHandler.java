package com.gnod.parallaxlistview.ui.expandonscrollheader;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener;
import com.gnod.parallaxlistview.ui.expandonscrollheader.listener.impl.ExpandOnScrollListenerImpl;

/**
 * TODO : Add documentation!!
 * <p/>
 * Created by nbarrios on 27/11/14.
 */
public class ExpandOnScrollHandler {

    /**
     * Used for log messages.
     */
    private static final String TAG = "ExpandOnScrollHandler";

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    /**
     * //  TODO : Use dp2px from another library to transform 160 into x PXs.
     */
    private static final int headerDefaultHeight = 320;
    private ViewPager viewPager;

    private View parentView;

    private SparseArray<ImageView> images;
    private SparseIntArray imagesHeight;
    private SparseArray<ExpandOnScrollListener> listeners;

    /**
     * TODO : Add documentation!!
     * <p/>
     * Creates a Zoom generator for a single {@link android.widget.ImageView}.
     *
     * @param parentView The parent view. It will be used to get its top padding. It should your
     *                   custom {@link android.widget.ScrollView}.
     */
    public ExpandOnScrollHandler(View parentView) {
        this(parentView, null);
    }

    /**
     * TODO : Add documentation!!
     * <p/>
     * Creates a Zoom generator for a specific {@link android.support.v4.view.ViewPager}. A carousel
     * of images.
     *
     * @param parentView The parent view. It will be used to get its top padding. It should your
     *                   custom {@link android.widget.ScrollView}.
     * @param viewPager
     */
    public ExpandOnScrollHandler(View parentView, ViewPager viewPager) {
        this.parentView = parentView;
        this.viewPager = viewPager;
        this.images = new SparseArray<ImageView>();
        this.imagesHeight = new SparseIntArray();
        this.listeners = new SparseArray<ExpandOnScrollListener>();
    }

    public void addImage(int position, ImageView imageView) {
        Log.v(TAG, "addImage... position= " + position);
        if (imageView != null) {
            Log.d(TAG, "Adding image to generator...");
            images.put(position, imageView);
        }
    }

    public void setViewsBounds(final double zoomRatio) {
        Log.v(TAG, "setViewsBounds...");

        int len = images.size();
        for (int index = 0; index < len; index++) {
            final int pageIndex = images.keyAt(index);
            final ImageView imageView = images.get(pageIndex);

            if (imageView != null) {
                int imageViewHeight = imageView.getHeight();
                if (imageViewHeight <= 0) {
                    imageViewHeight = headerDefaultHeight;
                }
                imagesHeight.put(index, imageViewHeight);

                final int finalImageViewHeight = imageViewHeight;
                ViewTreeObserver vto = imageView.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        Log.v(TAG, "onPreDraw... index= " + pageIndex);
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                        if (listeners.get(pageIndex) == null) {
                            double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getMeasuredWidth());

                            int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

                            Log.d(TAG, "Adding listener for page index: " + pageIndex);
                            listeners.put(pageIndex, new ExpandOnScrollListenerImpl(imageView, parentView.getPaddingTop(), finalImageViewHeight, drawableMaxHeight, viewPager));
                        } else {
                            Log.d(TAG, "Skipping listener creation for index: " + pageIndex + ", because it already exists.");
                        }

                        return true;
                    }
                });
            }
        }
    }

    /**
     * Gets the listener for the current page (if we are working with a {@link
     * android.support.v4.view.ViewPager} or the unique listener when working with a single image.
     *
     * @return The corresponding {@link com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener}.
     */
    public ExpandOnScrollListener getExpandOnScrollListener() {
        Log.v(TAG, "getExpandOnScrollListener...");
        ExpandOnScrollListener expandOnScrollListener = null;

        if (viewPager != null) {
            expandOnScrollListener = listeners.get(viewPager.getCurrentItem());
        }

        return expandOnScrollListener != null ? expandOnScrollListener : listeners.get(0);
    }

    public int getCount() {
        return images.size();
    }

    public void removeImage(ImageView imageView) {
        Log.v(TAG, "removeImage...");

        int index = images.indexOfValue(imageView);
        images.remove(index);
        listeners.delete(index);
        Log.d(TAG, "Removed listener for index= " + index);
    }
}
