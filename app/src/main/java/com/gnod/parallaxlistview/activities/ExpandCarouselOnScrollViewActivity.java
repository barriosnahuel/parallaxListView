package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;
import com.gnod.parallaxlistview.ui.view.CustomScrollView;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ExpandCarouselOnScrollViewActivity extends Activity {

    /**
     * Used for log messages.
     */
    private static final String TAG = "ExpandCarouselOnScrollViewActivity";

    private ExpandOnScrollHandler expandOnScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate...");
        setContentView(R.layout.activity_parallax_carousel_scroll_view);

        CustomScrollView customScrollView = (CustomScrollView) findViewById(R.id.layout_scrollview);
        ViewPager viewPager = (ViewPager) findViewById(R.id.carousel);

        expandOnScrollHandler = new ExpandOnScrollHandler(customScrollView, viewPager);

        SparseArray<String> ids = new SparseArray<String>();
        ids.put(0, "uno");
        ids.put(1, "dos");
        ids.put(2, "tres");
//        ids.put(3, "cuatro");
//        ids.put(4, "cinco");

        viewPager.setAdapter(new CarouselPagerAdapter(ids, expandOnScrollHandler));

        customScrollView.setExpandOnScrollHandler(expandOnScrollHandler);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.v(TAG, "onWindowFocusChanged...");

//        if (hasFocus) {
//            Log.d(TAG, "Calling setViewsBounds from onWindowFocusChanged...");
//            expandOnScrollHandler.setViewsBounds(ExpandOnScrollHandler.ZOOM_X2);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parallax, menu);
        return true;
    }

    //
    //    ==================================== Utility classes

    /**
     * PagerAdapter always has 3 pages in stack unless the user is in the first/last page (in that
     * case it only has the following/previous one).
     */
    private class CarouselPagerAdapter extends PagerAdapter {

        private final ExpandOnScrollHandler expandOnScrollHandler;
        private final SparseArray<String> ids;

        private CarouselPagerAdapter(SparseArray<String> ids, ExpandOnScrollHandler expandOnScrollHandler) {
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

                expandOnScrollHandler.addImage(position, imageView);
                expandOnScrollHandler.setViewsBounds(ExpandOnScrollHandler.ZOOM_X2);
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.v(TAG, "destroyItem... position= " + position);
            //  TODO : Fix why the image doens't expand itself when scrolling after removing it from the container (and re instantiating it)
            container.removeView((View) object);
            expandOnScrollHandler.removeImage((ImageView) object);
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
            return getResources().getDrawable(resourceId);
        }
    }

}
