package com.hb.hkm.hypebeaststore.life;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.HomeStoreNative;
import com.hb.hkm.hypebeaststore.datamodel.V1.Product;
import com.parse.Parse;
import com.parse.PushService;

import java.net.CookieHandler;
import java.net.CookiePolicy;

/**
 * Created by hesk on 2/2/15.
 */
public class LifeCycleApp extends Application {
    private SharedPreferences SP;
    private String
            licenseKey = "",
            productKey = "",
            mac_id = "",
            login = "",
            pass = "";

    public LifeCycleApp() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "yjvYZqY25oJbfkZCKYdspnFiSjNIg7n9GXm7YKJ0", "eeJlDLPuK5sYS0H4tNuLA18hSDU93EjrW1kWTa2K");// Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, HomeStoreNative.class);

        // enable cookies
        java.net.CookieManager cookieManager = new java.net.CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        /*ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(Config.PARSETAG, "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e(Config.PARSETAG, "failed to subscribe for push", e);
                }
            }
        });*/


        SP = getSharedPreferences(Config.save_pref_private_id, Context.MODE_PRIVATE);
        mac_id = Tool.get_mac_address(this);
        login = SP.getString(Config.save_login, "");
        pass = SP.getString(Config.save_password, "");
        /*  CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        */
    }
    /*

        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        }
    */

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void store_token(final String token) {

    }

    public void read_stored_authen() {
        login = SP.getString(Config.save_login, "");
        pass = SP.getString(Config.save_password, "");
    }

    public void store_authenication(final String _login, final String _password) {
        SP.edit().putString(Config.save_login, _login)
                .putString(Config.save_password, _password).apply();
    }

    public void addNewProductToCart(final Product product) {
//        DataBank.my_cart.add
    }
}
