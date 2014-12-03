package com.gnod.parallaxlistview.ui.expandonscrollheader.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;
import com.gnod.parallaxlistview.ui.expandonscrollheader.model.ExpandablePage;

/**
 * PagerAdapter always has 3 pages in stack unless the user is in the first/last page (in that case
 * it only has the following/previous one).
 * <p/>
 * Created by nbarrios on 1/12/14.
 */
public abstract class AbstractExpandableOnScrollPagerAdapter extends PagerAdapter {

    /**
     * Used for log messages.
     */
    private static final String TAG = "CarouselPagerAdapter";

    private final SparseArray<String> ids;
    private final ExpandOnScrollHandler expandOnScrollHandler;

    public AbstractExpandableOnScrollPagerAdapter(SparseArray<String> ids, ExpandOnScrollHandler expandOnScrollHandler) {
        this.ids = ids;
        this.expandOnScrollHandler = expandOnScrollHandler;
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.v(TAG, "instantiateItem... containerId= " + container.getId() + ", position= " + position);

        final ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String id = ids.get(position);
        if (id != null) {
            imageView.setId(id.hashCode());
            container.addView(imageView, 0);

            Glide.with(container.getContext())
                    .load(getDrawableUrlForPageIndex(position))
                    .asBitmap()
                    .into(new ImageViewTarget<Bitmap>(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            imageView.setImageBitmap(resource);

                            expandOnScrollHandler.addPage(new ExpandablePage(position, imageView));
                        }
                    });
        }

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        expandOnScrollHandler.removePage((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    /**
     * @param position position The page index in which the {@code drawable} will be displayed.
     * @return The url of the image to load in the specified page {@code position}.
     */
    protected abstract String getDrawableUrlForPageIndex(int position);
}
