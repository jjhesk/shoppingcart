package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.hb.hkm.hypebeaststore.controller.Config;

/**
 * Created by hesk on 2/6/15.
 */
public class AddCartManager extends asyclient {
    protected static String TAG = "add to the cart";
    public static String getUrl(final int variant_id, final int quantity) {
        return String.format(Config.wv.add_to_cart_format, variant_id, quantity);
    }

    public AddCartManager(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void GSONParser(String data) {
        Log.d(TAG, data);
    }

    @Override
    protected void ViewConstruction() {

    }
}
