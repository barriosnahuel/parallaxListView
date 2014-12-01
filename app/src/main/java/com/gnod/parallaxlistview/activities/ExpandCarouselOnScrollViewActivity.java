package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.expandonscrollheader.adapter.CarouselPagerAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate...");
        setContentView(R.layout.activity_parallax_carousel_scroll_view);

        CustomScrollView customScrollView = (CustomScrollView) findViewById(R.id.layout_scrollview);
        ViewPager viewPager = (ViewPager) findViewById(R.id.carousel);

        ExpandOnScrollHandler expandOnScrollHandler = new ExpandOnScrollHandler(customScrollView, viewPager);

        SparseArray<String> ids = new SparseArray<String>();
        ids.put(0, "uno");
        ids.put(1, "dos");
        ids.put(2, "tres");
        ids.put(3, "cuatro");
        ids.put(4, "cinco");

        viewPager.setAdapter(new CarouselPagerAdapter(this, ids, expandOnScrollHandler));

        customScrollView.setExpandOnScrollHandler(expandOnScrollHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parallax, menu);
        return true;
    }

}
