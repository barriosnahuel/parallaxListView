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
public class CarouselPagerAdapter extends PagerAdapter {

    /**
     * Used for log messages.
     */
    private static final String TAG = "CarouselPagerAdapter";

    private Context context;

    private final ExpandOnScrollHandler expandOnScrollHandler;
    private final SparseArray<String> ids;

    public CarouselPagerAdapter(Context context, SparseArray<String> ids, ExpandOnScrollHandler expandOnScrollHandler) {
        this.context = context;
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
        Log.v(TAG, "destroyItem... position= " + position);
        //  TODO : Fix why the image doens't expand itself when scrolling after removing it from the container (and re instantiating it)
        container.removeView((View) object);
        expandOnScrollHandler.removePage((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    private Drawable getDrawableForPageIndex(int position) {
        int resourceId;
        switch (position) {
            case 0:
                resourceId = R.drawable.imagen;
                break;
            case 1:
                resourceId = R.drawable.imagen2;
                break;
            case 2:
                resourceId = R.drawable.imagen3;
                break;
            case 3:
                resourceId = R.drawable.imagen4;
                break;
            default:
                resourceId = R.drawable.img_header;
        }
        return context.getResources().getDrawable(resourceId);
    }
}
