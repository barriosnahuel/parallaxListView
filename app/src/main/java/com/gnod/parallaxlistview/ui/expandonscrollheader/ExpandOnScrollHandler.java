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

import java.util.ArrayList;
import java.util.List;

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

    private List<ImageView> images;
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
        this.images = new ArrayList<ImageView>();
        this.imagesHeight = new SparseIntArray();
        this.listeners = new SparseArray<ExpandOnScrollListener>();
    }

    public void addImage(ImageView imageView) {
        Log.v(TAG, "addImage...");
        if (imageView != null) {
            Log.d(TAG, "Adding image to generator...");
            images.add(imageView);
        }
    }

    public void setViewsBounds(final double zoomRatio) {
        Log.v(TAG, "setViewsBounds...");

        int len = images.size();
        for (int index = 0; index < len; index++) {
            final ImageView imageView = images.get(index);

            int imageViewHeight = imagesHeight.get(index);
            if (imageViewHeight == 0) {
                imageViewHeight = imageView.getHeight();
                if (imageViewHeight <= 0) {
                    imageViewHeight = headerDefaultHeight;
                }
                imagesHeight.put(index, imageViewHeight);

                final int finalImageViewHeight = imageViewHeight, finalIndex = index;
                ViewTreeObserver vto = imageView.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                        double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getMeasuredWidth());

                        int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

                        Log.d(TAG, "Adding listener for page index: " + finalIndex);
                        listeners.put(finalIndex, new ExpandOnScrollListenerImpl(imageView, parentView.getPaddingTop(), finalImageViewHeight, drawableMaxHeight, viewPager));
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
}
