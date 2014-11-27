package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.view.ZoomHeaderScrollView;

public class ParallaxOnScrollViewActivity extends Activity {

    private ZoomHeaderScrollView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_scroll_view);

        mListView = (ZoomHeaderScrollView) findViewById(R.id.layout_scrollview);
        mListView.setParallaxImageView((ImageView) findViewById(R.id.layout_header_image));
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
            mListView.setViewsBounds(ZoomHeaderScrollView.ZOOM_X2);
        }
    }

}
