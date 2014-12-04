package com.gnod.parallaxlistview.activities.variants;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;

import com.barriosnahuel.expandonscroll.ExpandOnScrollHandler;
import com.barriosnahuel.expandonscroll.adapter.AbstractExpandableOnScrollPagerAdapter;
import com.barriosnahuel.expandonscroll.view.CustomScrollView;
import com.gnod.parallaxlistview.R;

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
            protected String getDrawableUrlForPageIndex(int position) {
                String resourceUrl;
                switch (position) {
                    case 0:
                        resourceUrl = "http://losseguros.com.ar/wp-content/uploads/Poliza-automotor-todo-riesgo-premiun.jpg";
                        break;
                    case 1:
                        resourceUrl = "http://www.solostocks.com/img/bermudas-para-hombres-5235674z1.jpg";
                        break;
                    case 2:
                        resourceUrl = "http://mla-s2-p.mlstatic.com/traje-de-bano-silicon-quiksilver-cybermonday-envio-gratis-20212-MLA7252806896_102014-O.jpg";
                        break;
                    case 3:
                        resourceUrl = "http://mla-s2-p.mlstatic.com/traje-de-bano-silicon-quiksilver-cybermonday-envio-gratis-20237-MLA7252806895_102014-O.jpg";
                        break;
                    default:
                        resourceUrl = "http://mla-s2-p.mlstatic.com/traje-de-bano-silicon-quiksilver-cybermonday-envio-gratis-20266-MLA7252806932_102014-O.jpg";
                }

                return resourceUrl;
            }
        });
    }

}
