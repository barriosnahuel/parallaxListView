package com.gnod.parallaxlistview.activities.variants;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;

import com.barriosnahuel.expandonscroll.ExpandOnScrollHandler;
import com.barriosnahuel.expandonscroll.adapter.SimpleExpandableOnScrollPagerAdapter;
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
        ids.put(0, "http://losseguros.com.ar/wp-content/uploads/Poliza-automotor-todo-riesgo-premiun.jpg");

        SimpleExpandableOnScrollPagerAdapter adapter = new SimpleExpandableOnScrollPagerAdapter(ids, expandOnScrollHandler);

        viewPager.setAdapter(adapter);

        adapter.add(1, "http://www.solostocks.com/img/bermudas-para-hombres-5235674z1.jpg");
        adapter.add(2, "http://bucket3.clanacion.com.ar/anexos/fotos/95/smartphones-1691995w645.jpg");
        adapter.add(3, "http://carupanero.com/sitioweb/wp-content/uploads/2014/07/Celular-samsung-2.jpg");
        adapter.add(4, "http://www.tuverde.com/imagenes/2009/10/billetera-baumm-tienda.jpg");
    }

}
