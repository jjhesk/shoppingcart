package com.hb.hkm.hkmeexpandedview;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by hesk on 2/26/2015.
 */
public class toggleWatcher {
    public final static String TAG = "toggleWatcher";
    private final ArrayList<CatelogView> list = new ArrayList<CatelogView>();
    private LinearLayout ly;

    public toggleWatcher() {
        list.clear();
    }

    public void addContainer(LinearLayout v) {
        ly = v;
    }

    public void onSelect(CatelogView v) {
        for (int i = 0; i < ly.getChildCount(); i++) {
            View t = ly.getChildAt(i);
            if (t instanceof CatelogView) {
                CatelogView sample = (CatelogView) t;
                if (!sample.equals(v)) {
                    sample.triggerClose();
                    //Log.d(TAG, "found item catelogview at child: " + i);
                }
            }
        }
    }
}
