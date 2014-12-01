package com.gnod.parallaxlistview.ui.expandonscrollheader;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener;
import com.gnod.parallaxlistview.ui.expandonscrollheader.listener.impl.ExpandOnScrollListenerImpl;
import com.gnod.parallaxlistview.ui.expandonscrollheader.model.ExpandablePage;

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
    private final ResetMethod resetMethod;

    private ViewPager viewPager;
    private View parentView;

    /**
     * The list of pages that this handler must handle.
     */
    private SparseArray<ExpandablePage> pages;

    /**
     * TODO : Add documentation!!
     * <p/>
     * Creates a Zoom generator for a single {@link android.widget.ImageView}.
     *
     * @param parentView The parent view. It will be used to get its top padding. It should your
     *                   custom {@link android.widget.ScrollView}.
     */
    public ExpandOnScrollHandler(View parentView) {
        this(parentView, null, ResetMethod.RESET);
    }

    /**
     * TODO : Add documentation!!
     * <p/>
     * Creates a Zoom generator for a specific {@link android.support.v4.view.ViewPager}. A carousel
     * of images.
     *
     * @param parentView The parent view. It will be used to get its top padding. It should your
     *                   custom {@link android.widget.ScrollView}.
     * @param viewPager  The ViewPager on which we will be working on.
     */
    public ExpandOnScrollHandler(View parentView, ViewPager viewPager, ResetMethod resetMethod) {
        this.parentView = parentView;
        this.viewPager = viewPager;
        this.pages = new SparseArray<ExpandablePage>();
        this.resetMethod = resetMethod;
    }

    /**
     * Add the specified {@code expandablePage} to this handler, and sets the associated {@link com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener} to this {@code expandablePage}.
     *
     * @param expandablePage The page to add.
     */
    public void addPage(ExpandablePage expandablePage) {
        Log.v(TAG, "addPage... pageIndex= " + expandablePage.getIndex());

        pages.put(expandablePage.getIndex(), expandablePage);
        setViewsBounds(ZOOM_X2);
    }

    public void setViewsBounds(final double zoomRatio) {
        int pagesQuantity = pages.size();
        for (int index = 0; index < pagesQuantity; index++) {
            final ExpandablePage eachPage = pages.valueAt(index);
            final ImageView imageView = eachPage.getImageView();

            if (imageView != null) {
                int imageViewHeight = imageView.getHeight();
                if (imageViewHeight <= 0) {
                    imageViewHeight = headerDefaultHeight;
                }

                final int finalImageViewHeight = imageViewHeight;
                ViewTreeObserver vto = imageView.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        Log.v(TAG, "onPreDraw... pageIndex= " + eachPage.getIndex());

                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                        if (eachPage.getListener() == null) {
                            double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getMeasuredWidth());

                            int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

                            Log.d(TAG, "Adding listener for page index: " + eachPage.getIndex());
                            eachPage.setListener(new ExpandOnScrollListenerImpl(imageView, parentView.getPaddingTop(), finalImageViewHeight, drawableMaxHeight, viewPager, resetMethod));
                        } else {
                            Log.d(TAG, "Skipping listener creation for index: " + eachPage.getIndex() + ", because it already exists.");
                        }

                        return true;
                    }
                });
            }
        }
    }

    /**
     * Remove the page that contains the specified {@code imageView}.
     *
     * @param imageView An image view that should be part of a page.
     */
    public void removePage(ImageView imageView) {
        Log.v(TAG, "removePage...");

        int indexInList = -1;
        int pageIndexToRemove = -1;

        int pagesQuantity = pages.size();
        for (int index = 0; index < pagesQuantity; index++) {
            ExpandablePage eachPage = pages.valueAt(index);
            if (eachPage.getImageView().equals(imageView)) {
                indexInList = index;
                pageIndexToRemove = eachPage.getIndex();
                break;
            }
        }

        if (indexInList != -1) {
            pages.removeAt(indexInList);
            Log.d(TAG, "Removed page for index= " + pageIndexToRemove);
        }
    }

    /**
     * Gets the listener for the current page (if we are working with a {@link
     * android.support.v4.view.ViewPager} or the unique listener when working with a single image.
     *
     * @return The corresponding {@link com.gnod.parallaxlistview.ui.expandonscrollheader.listener.ExpandOnScrollListener}.
     */
    public ExpandOnScrollListener getExpandOnScrollListener() {
        ExpandOnScrollListener expandOnScrollListener = null;

        if (viewPager != null) {
            int len = pages.size();
            for (int i = 0; i < len; i++) {
                ExpandablePage eachPage = pages.valueAt(i);
                if (eachPage.getIndex() == viewPager.getCurrentItem()) {
                    expandOnScrollListener = eachPage.getListener();
                    break;
                }
            }
        }

        return expandOnScrollListener;
    }

    /**
     * TODO : Add documentation!!
     */
    public enum ResetMethod {
        /**
         * After scrolling the image's height will be restored to its initial size.
         */
        RESET,

        /**
         * After scrolling nothing happens. The images of all carousel will have the new height till
         * the user scrolls down.
         */
        NO_RESET
    }

}
