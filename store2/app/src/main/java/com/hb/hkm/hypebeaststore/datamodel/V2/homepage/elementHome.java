package com.hb.hkm.hypebeaststore.datamodel.V2.homepage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asynhkm.utility.Measures;
import com.hb.hkm.hypebeaststore.life.Config;

/**
 * Created by hesk on 3/10/15.
 */
public class elementHome {
    private String name;
    private String slug;
    private int width;
    private int height;
    private String thumbnail;
    private String ipad_landscape_thumbnail;
    private int ipad_landscape_width;
    private int ipad_landscape_height;
    private String ipad_portrait_thumbnail;
    private int ipad_portrait_width;
    private int ipad_portrait_height;

    public elementHome() {
    }

    public boolean fullwidth() {
        return width == 320;
    }

    public LinearLayout.LayoutParams get_element_layout_land(Context context) {
        if (fullwidth()) {
            return new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Measures.pxToDp(height, context)
                    , 1.f);
        } else {
            return new LinearLayout.LayoutParams(
                    Measures.pxToDp(width, context),
                    Measures.pxToDp(height, context)
                    , 1.f);
        }
    }

    public String getFullPath() {
        // brands
        return Config.wv.domain_home + slug;
    }

    public boolean showFilterPage() {
        return slug.equalsIgnoreCase("brands");
    }

    public String getTitle() {
        return name;
    }

    public String getResourceImage() {
        return thumbnail;
    }

}
