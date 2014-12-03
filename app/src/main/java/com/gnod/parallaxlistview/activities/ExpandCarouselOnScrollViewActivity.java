package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;
import com.gnod.parallaxlistview.ui.expandonscrollheader.adapter.AbstractExpandableOnScrollPagerAdapter;
import com.gnod.parallaxlistview.ui.view.CustomScrollView;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ExpandCarouselOnScrollViewActivity extends Activity {

    /**
     * Used for log messages.
     */
    private static final String TAG = "ExpandCarouselOnScrollViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate...");
        setContentView(R.layout.activity_expand_carousel_scroll_view);

        CustomScrollView customScrollView = (CustomScrollView) findViewById(R.id.layout_scrollview);
        ViewPager viewPager = (ViewPager) findViewById(R.id.carousel);

        ExpandOnScrollHandler expandOnScrollHandler = new ExpandOnScrollHandler(customScrollView.getPaddingTop(), viewPager, viewPager, ExpandOnScrollHandler.ResetMethod.NO_RESET);
        customScrollView.setExpandOnScrollHandler(expandOnScrollHandler);

        SparseArray<String> ids = new SparseArray<String>();
        ids.put(0, "uno");
        ids.put(1, "dos");
        ids.put(2, "tres");
        ids.put(3, "cuatro");
        ids.put(4, "cinco");

        viewPager.setAdapter(new AbstractExpandableOnScrollPagerAdapter(ids, expandOnScrollHandler) {
            @Override
            protected Drawable getDrawableForPageIndex(int position) {
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parallax, menu);
        return true;
    }

}
