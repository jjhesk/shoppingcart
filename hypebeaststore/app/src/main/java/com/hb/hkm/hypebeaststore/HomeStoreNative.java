package com.hb.hkm.hypebeaststore;

import android.animation.LayoutTransition;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.hb.hkm.hkmeexpandedview.CatelogView;
import com.hb.hkm.hkmeexpandedview.CatelogViewBuilder;
import com.hb.hkm.hkmeexpandedview.list.DataBind;
import com.hb.hkm.hypebeaststore.controller.MaterialColor;
import com.hb.hkm.hypebeaststore.fragments.wheel.TextDrawable;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hesk on 2/11/15.
 */
public class HomeStoreNative extends ActionBarActivity {

    private String[] theItemList = new String[]{
            "pop",
            "handle",
            "spring",
            "summer"
    };


    protected void init_slider() {
        try {
            final SliderLayout sl = (SliderLayout) findViewById(R.id.slider);
            final Map<String, String> url_maps = new HashMap<String, String>();
            for (int i = 0; i < 10; i++) {
                url_maps.put("sample #" + i, "http://media.salon.com/2013/08/shutterstock_64449766.jpg");
            }
            for (Map.Entry<String, String> entry : url_maps.entrySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(entry.getValue())
                        .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                // .setOnSliderClickListener(this);
                //add your extra information
                textSliderView.getBundle()
                        .putString("extra", entry.getKey());
                sl.addSlider(textSliderView);
            }
            sl.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
            sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sl.setCustomAnimation(new DescriptionAnimation());
            sl.setDuration(4000);
        } catch (NullPointerException e) {
            //  RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            // RunLDialogs.strDemo2(context, e.getMessage());
        }
    }

    protected void init_expandtabs() throws Resources.NotFoundException {
        final LinearLayout container = (LinearLayout) findViewById(R.id.expanded_menu_list);
        // Start with two views
        for (int i = 0; i < 10; ++i) {
            ArrayList<DataBind> h = new ArrayList<DataBind>();
            h.add(new DataBind("sfsf6", "Sfs4e"));
            h.add(new DataBind("5s3fsf5", "2Sfs"));
            h.add(new DataBind("grege", "2Sfs"));
            h.add(new DataBind("42gw", "2Sfs"));
            h.add(new DataBind("234regerg", "2Sfs"));
            h.add(new DataBind("7s2ef3", "4Sfs9"));
            h.add(new DataBind("dfg432243", "4Sfs9"));

            CatelogViewBuilder cb = new CatelogViewBuilder();
            cb
                    .preset_src(R.drawable.bike, getResources().getDimension(R.dimen.home_collapsed))
                    .rndColor()
                    .setDataList(h);
            CatelogView c = new CatelogView(this, cb);
            container.addView(c);
        }

       /* addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adding a view will cause a LayoutTransition animation
                container.addView(new ColoredView(context), 1);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container.getChildCount() > 0) {
                    // Removing a view will cause a LayoutTransition animation
                    container.removeViewAt(Math.min(1, container.getChildCount() - 1));
                }
            }
        });*/

        // Note that this assumes a LayoutTransition is set on the container, which is the
        // case here because the container has the attribute "animateLayoutChanges" set to true
        // in the layout file. You can also call setLayoutTransition(new LayoutTransition()) in
        // code to set a LayoutTransition on any container.
        LayoutTransition transition = container.getLayoutTransition();
        // New capability as of Jellybean; monitor the container for *all* layout changes
        // (not just add/remove/visibility changes) and animate these changes as well.
        transition.enableTransitionType(LayoutTransition.CHANGING);
    }

    protected void init_wheel() {
       /*   final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.ex_item, R.id.header_text, theItemList);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.expandablelistview);
        expandableLayoutListView.setHeaderDividersEnabled(true);
        expandableLayoutListView.setAdapter(arrayAdapter);
        final ExpandableLayout single_exp = (ExpandableLayout) findViewById(R.id.single_expanableview);
       */
        //single_exp.setVerticalScrollbarPosition();
       /* final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        final int ITEM_COUNT = 20;

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);
        }

        wheelView.setAdapter(new MaterialColorAdapter(entries));*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        init_slider();
        init_expandtabs();

    }

    static class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        MaterialColorAdapter(List<Map.Entry<String, Integer>> entries) {
            super(entries);
        }

        @Override
        public Drawable getDrawable(int position) {
            Drawable[] drawable = new Drawable[]{
                    createOvalDrawable(getItem(position).getValue()),
                    new TextDrawable(String.valueOf(position))
            };
            return new LayerDrawable(drawable);
        }

        private Drawable createOvalDrawable(int color) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }
    }

    //get the materials darker contrast
    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
