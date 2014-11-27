package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.view.ZoomHeaderScrollView;
import com.gnod.parallaxlistview.ui.zoomheader.ZoomHeaderGenerator;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ParallaxOnScrollViewActivity extends Activity {

    private ZoomHeaderScrollView zoomHeaderScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_scroll_view);

        zoomHeaderScrollView = (ZoomHeaderScrollView) findViewById(R.id.layout_scrollview);
        zoomHeaderScrollView.setParallaxImageView((ImageView) findViewById(R.id.layout_header_image));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parallax, menu);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            zoomHeaderScrollView.setViewsBounds(ZoomHeaderGenerator.ZOOM_X2);
        }
    }

}
