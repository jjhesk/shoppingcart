package com.hb.hkm.hypebeaststore.Controllers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.datamodel.Product;

/**
 * Created by hesk on 2/2/15.
 */
public class App extends Application {
    private SharedPreferences SP;
    private String
            licenseKey = "",
            productKey = "",
            mac_id = "",
            login = "",
            pass = "";

    public App() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        SP = getSharedPreferences(Config.save_pref_private_id, Context.MODE_PRIVATE);
        mac_id = Tool.get_mac_address(this);
        login = SP.getString(Config.save_login, "");
        pass = SP.getString(Config.save_password, "");

    }

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
