package com.hb.hkm.hypebeaststore;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

import com.hb.hkm.hypebeaststore.life.Config;

/**
 * Created by hesk on 2/11/15.
 */
public class HBLogin extends ActionBarActivity {
    static class clientWebInterface {

        private Context ctx;

        public clientWebInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.fragment_store_demo_webview);
        final WebView webview = (WebView) findViewById(R.id.store_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new clientWebInterface(this), "HtmlViewer");
        webview.setWebViewClient(new com.hb.hkm.hypebeaststore.widgets.webviewclients.HBLogin(webview));
        webview.loadUrl(Config.wv.hb_login);
    }

}
