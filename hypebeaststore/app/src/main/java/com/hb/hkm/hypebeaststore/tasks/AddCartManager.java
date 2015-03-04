package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.hb.hkm.hypebeaststore.datamodel.cookiesupport.PersistentCookieStore;
import com.hb.hkm.hypebeaststore.life.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by hesk on 2/6/15.
 */
public class AddCartManager extends asyclient {
    protected static String TAG = "add to the cart";
    protected int variant, quantity;
    protected static CookieManager cookieManager = new CookieManager();

    public AddCartManager(Context ccc, callback cb) {
        super(ccc, cb);
        cookieManager = new CookieManager(new PersistentCookieStore(ccc), CookiePolicy.ACCEPT_ALL);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
    }

    public void setItem(final int variant_id, final int q) throws Exception {
        variant = variant_id;
        quantity = q;
        setURL(String.format(Config.addtocart, variant_id + "", q + ""));
    }

    @Override
    protected void GSONParser(String data) throws JSONException {
        Log.d(TAG, data);

        JSONObject json = new JSONObject(data);
        if (json.has("error")) {
            setError(json.getString("error"));
        }

    }

    @Override
    protected void ViewConstruction() {

    }
}
