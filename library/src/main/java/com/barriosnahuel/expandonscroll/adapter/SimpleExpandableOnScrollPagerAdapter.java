package com.barriosnahuel.expandonscroll.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.barriosnahuel.expandonscroll.ExpandOnScrollHandler;
import com.barriosnahuel.expandonscroll.model.ExpandablePage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * PagerAdapter always has 3 pages in stack unless the user is in the first/last page (in that case
 * it only has the following/previous one).
 * <p/>
 * Created by nbarrios on 1/12/14.
 */
public class SimpleExpandableOnScrollPagerAdapter extends PagerAdapter {

    /**
     * Used for log messages.
     */
    private static final String TAG = "CarouselPagerAdapter";

    private final SparseArray<String> resourcesByPageIndex;
    private final ExpandOnScrollHandler expandOnScrollHandler;

    public SimpleExpandableOnScrollPagerAdapter(SparseArray<String> resourcesByPageIndex, ExpandOnScrollHandler expandOnScrollHandler) {
        this.resourcesByPageIndex = resourcesByPageIndex;
        this.expandOnScrollHandler = expandOnScrollHandler;
    }

    @Override
    public int getCount() {
        return resourcesByPageIndex.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.v(TAG, "instantiateItem... containerId= " + container.getId() + ", position= " + position);

        final ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        final String resourceUrl = resourcesByPageIndex.get(position);
        if (resourceUrl != null) {
            imageView.setId(String.valueOf(position).hashCode());
            container.addView(imageView, 0);

            Glide.with(container.getContext())
                    .load(resourceUrl)
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
     * @param pageIndex   The page index in which to load the given {@code resource}.
     * @param resourceUrl The URL of the resource to fetch from the network.
     */
    public void add(int pageIndex, String resourceUrl) {
        resourcesByPageIndex.append(pageIndex, resourceUrl);
        notifyDataSetChanged();
        Log.d(TAG, "Called: notifyDataSetChanged(). Added pageIndex= " + pageIndex);
    }
}
