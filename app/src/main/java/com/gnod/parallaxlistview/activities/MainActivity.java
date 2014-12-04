package com.gnod.parallaxlistview.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gnod.parallaxlistview.R;
import com.gnod.parallaxlistview.activities.variants.ExpandCarouselOnScrollViewActivity;
import com.gnod.parallaxlistview.activities.variants.ExpandImageOnScrollViewActivity;
import com.gnod.parallaxlistview.activities.variants.ParallaxOnListViewActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ListView listView = (ListView) findViewById(R.id.list_view);

        List<String> labels = new ArrayList<>();
        labels.add(getText(R.string.expand_image_list_view).toString());
        labels.add(getText(R.string.expand_image_scroll_view).toString());
        labels.add(getText(R.string.expand_view_pager_scroll_view).toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Class nextActivityClass;

                switch (position) {
                    case 0:
                        nextActivityClass = ParallaxOnListViewActivity.class;
                        break;
                    case 1:
                        nextActivityClass = ExpandImageOnScrollViewActivity.class;
                        break;
                    case 2:
                        nextActivityClass = ExpandCarouselOnScrollViewActivity.class;
                        break;
                    default:
                        throw new IllegalStateException("That option is not yet implemented.");
                }

                startActivity(new Intent(context, nextActivityClass));
            }
        });
    }

}
