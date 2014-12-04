package com.barriosnahuel.expandonscroll;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.barriosnahuel.expandonscroll.listener.ExpandOnScrollListener;
import com.barriosnahuel.expandonscroll.listener.impl.ExpandOnScrollListenerImpl;
import com.barriosnahuel.expandonscroll.model.ExpandablePage;

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

    private static int headerDefaultHeight;
    private final ResetMethod resetMethod;

    private int paddingTop;
    private View viewToExpand;
    private ViewPager viewPager;

    /**
     * The list of pages that this handler must handle.
     */
    private SparseArray<ExpandablePage> pages;

    /**
     * Same as {@link #ExpandOnScrollHandler(int, android.view.View, android.support.v4.view.ViewPager,
     * ExpandOnScrollHandler.ResetMethod)} but without using a {@link android.support.v4.view.ViewPager}.
     * You will expand a simple view like an {@link android.widget.ImageView}.
     */
    public ExpandOnScrollHandler(int paddingTop, View viewToExpand, ResetMethod resetMethod) {
        this(paddingTop, viewToExpand, null, resetMethod);
    }

    /**
     * Creates a Zoom generator for a specific {@link android.support.v4.view.ViewPager}. A carousel
     * of images.
     *
     * @param paddingTop   The top padding of the parent view. It should your custom {@link
     *                     android.widget.ScrollView}.
     * @param viewToExpand The ViewPager on which we will be working on.
     * @param viewPager    The {@link android.support.v4.view.ViewPager} that we will be expanding.
     * @param resetMethod  The reset method to use after scrolling up.
     */
    public ExpandOnScrollHandler(int paddingTop, View viewToExpand, ViewPager viewPager, ResetMethod resetMethod) {
        this.paddingTop = paddingTop;
        headerDefaultHeight = (int) viewToExpand.getResources().getDimension(R.dimen.expandable_initial_height);
        this.viewToExpand = viewToExpand;
        this.viewPager = viewPager;
        this.pages = new SparseArray<ExpandablePage>();
        this.resetMethod = resetMethod;
    }

    /**
     * Add the specified {@code expandablePage} to this handler, and sets the associated {@link
     * com.barriosnahuel.expandonscroll.listener.ExpandOnScrollListener} to this {@code
     * expandablePage}.
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

                        if (eachPage.getListener() == null && imageView.getDrawable() != null) {
                            double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getMeasuredWidth());

                            int drawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ? zoomRatio : 1));

                            Log.d(TAG, "Adding listener for page index: " + eachPage.getIndex());
                            eachPage.setListener(new ExpandOnScrollListenerImpl(imageView.getId(), paddingTop, finalImageViewHeight, drawableMaxHeight, viewToExpand, resetMethod));
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
     * @return The corresponding {@link com.barriosnahuel.expandonscroll.listener.ExpandOnScrollListener}.
     */
    public ExpandOnScrollListener getExpandOnScrollListener() {
        ExpandOnScrollListener expandOnScrollListener = null;

        if (viewPager == null) {
            expandOnScrollListener = pages.valueAt(0).getListener();
        } else {
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
