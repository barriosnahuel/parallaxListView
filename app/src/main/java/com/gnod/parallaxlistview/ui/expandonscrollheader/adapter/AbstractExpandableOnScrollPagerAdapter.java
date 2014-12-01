package com.gnod.parallaxlistview.ui.expandonscrollheader.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gnod.parallaxlistview.R;
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
    public Object instantiateItem(ViewGroup container, int position) {
        Log.v(TAG, "instantiateItem... containerId= " + container.getId() + ", position= " + position);

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ImageView imageView = (ImageView) inflater.inflate(R.layout.carousel_item, container, false);
        imageView.setImageDrawable(getDrawableForPageIndex(position));

        String id = ids.get(position);
        if (id != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setId(id.hashCode());

            container.addView(imageView, 0);

            expandOnScrollHandler.addPage(new ExpandablePage(position, imageView));
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
     * @param position The page index in which the {@code drawable} will be displayed.
     *
     * @return The {@code drawable} to display in the specified page {@code position}.
     */
    protected abstract Drawable getDrawableForPageIndex(int position);
}
