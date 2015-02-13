package com.hb.hkm.hypebeaststore.Controllers;

import android.os.Build;

/**
 * Created by hesk on 2/2/15.
 */
public class Config {
    public static class wv {
        public static final String hb_login = "http://hypebeast.com/login/facebook?return_format=json&target_path=http://store.hypebeast.com";
        public static final String domain_start = "https://store.hypebeast.com/";
        public static final String brands = "brands";
        public static String single_request_route = domain_start + brands + "/";
        public static String add_to_cart_format = domain_start + "cart/add/?variant_id=%d&quantity=%d";
    }

    public static String newarrivals = wv.domain_start + "new-arrivals";
    public static String hometech = wv.domain_start + "categories/home-tech";
    public static String clothing = wv.domain_start + "categories/clothing";
    public static String shoes = wv.domain_start + "categories/shoes";

    public static class setting {
        public static double list_expand_factor = 0.65d;
        public static int single_page_items = 15;
        public static int APIversion = 1;
        public static boolean useAPIV2 = false;
        public static String useragent_V2 = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_10 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B411 HypebeastStoreApp/1.0";
        public static String useragent = "HypebeastStoreApp/1.0 Android " + Build.VERSION.SDK_INT;

    }

    public static String save_pref_private_id = "HBAUTHENTICATION";
    public static String save_login = "LOGIN";
    public static String save_password = "PASSWORD";
    public static String single_id = "SINGLE_ID";
}
