package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.StoreFront;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;

/**
 * Created by hesk on 2/4/15.
 */
public class WorkerBuilder extends asyclient {
    public WorkerBuilder(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void ViewConstruction() {
        ((StoreFront) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((StoreFront) ctx).getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new GridDisplay())
                        .commit();
            }
        });

    }

}
