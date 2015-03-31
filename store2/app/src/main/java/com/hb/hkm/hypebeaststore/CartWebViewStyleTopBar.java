package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.hb.hkm.hypebeaststore.components.webviewclients.ChromeLoader;
import com.hb.hkm.hypebeaststore.components.webviewclients.HBCart;
import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;

/**
 * Created by hesk on 31/3/15.
 */
public class CartWebViewStyleTopBar extends BasicSupportActionBarHKM {

    //@DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store_top_bar);
        final WebView webview = (WebView) findViewById(R.id.store_webview);
        final HBCart hcart = new HBCart(this, webview);
        final ChromeLoader chromeLoader = new ChromeLoader(this);
        chromeLoader.setShowHideWebViewOnload(false);
        webview.setWebViewClient(hcart);
        webview.setWebChromeClient(chromeLoader);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        //CookieSyncManager.getInstance().stopSync();
        super.onResume();
    }

    @Override
    public void onPause() {
        //  CookieSyncManager.getInstance().sync();
        super.onPause();
    }
}
