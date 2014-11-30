package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.ui.expandonscrollheader.ExpandOnScrollHandler;
import com.gnod.parallaxlistview.ui.view.CustomScrollView;

/**
 * Created by nbarrios on 27/11/14.
 */
public class ExpandImageOnScrollViewActivity extends Activity {

    /**
     * Used for log messages.
     */
    private static final String TAG = "ExpandImageOnScrollViewActivity";

    private ExpandOnScrollHandler expandOnScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate...");
        setContentView(R.layout.activity_parallax_scroll_view);

        CustomScrollView customScrollView = (CustomScrollView) findViewById(R.id.layout_scrollview);

        expandOnScrollHandler = new ExpandOnScrollHandler(customScrollView);
        expandOnScrollHandler.addImage(0, (ImageView) findViewById(R.id.layout_header_image));

        customScrollView.setExpandOnScrollHandler(expandOnScrollHandler);
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
            Log.d(TAG, "Calling setViewsBounds from onWindowFocusChanged...");
            expandOnScrollHandler.setViewsBounds(ExpandOnScrollHandler.ZOOM_X2);
        }
    }

}
