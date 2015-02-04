package com.hb.hkm.hypebeaststore.Controllers;

import android.os.Build;

/**
 * Created by hesk on 2/2/15.
 */
public class Config {
    public static class wv {
        public static final String domain_start = "https://store.hypebeast.com/";

    }

    public static String newarrivals = wv.domain_start + "new-arrivals";
    public static String hometech = wv.domain_start + "categories/home-tech";
    public static String clothing = wv.domain_start + "categories/clothing";

    public static class setting {
        public static String useragent = "HypebeastStoreApp/1.0 Android " + Build.VERSION.SDK_INT;
    }
}
