package com.apsoftware.expandonscroll.model;

import android.widget.ImageView;

import com.apsoftware.expandonscroll.listener.ExpandOnScrollListener;
import com.apsoftware.expandonscroll.listener.impl.ExpandOnScrollListenerImpl;

/**
 * Created by nbarrios on 30/11/14.
 */
public class ExpandablePage {

    /**
     * The page index in the whole carousel.
     */
    private int index;

    /**
     * The listener that will handle the expand effect.
     */
    private ExpandOnScrollListener listener;

    /**
     * The image view related to this carousel's page.
     */
    private ImageView image;

    /**
     * Creates a new page for the given {@code pageIndex} and a specific {@code image}.
     *
     * @param pageIndex See {@link #index}
     * @param image     See {@link #image}
     */
    public ExpandablePage(int pageIndex, ImageView image) {
        this.index = pageIndex;
        this.image = image;
    }

    public int getIndex() {
        return index;
    }

    public ImageView getImageView() {
        return image;
    }

    public ExpandOnScrollListener getListener() {
        return listener;
    }

    public void setListener(ExpandOnScrollListenerImpl listener) {
        this.listener = listener;
    }

    @Override
    public String toString() {
        return "ExpandablePage{" +
                "index=" + index +
                ", listener=" + listener +
                ", image=" + image +
                '}';
    }
}
