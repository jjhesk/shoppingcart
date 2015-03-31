package com.hb.hkm.hypebeaststore.components.webviewclients;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by hesk on 3/13/15.
 */
public class ChromeLoader extends WebChromeClient {
    private CircleProgressBar cb;
    private boolean control_webview_show_hide_onload = false;
    private Activity context;

    public ChromeLoader(CircleProgressBar circlebar) {
        cb = circlebar;
        cb.setCircleBackgroundEnabled(true);
        cb.setVisibility(View.VISIBLE);
        cb.setShowProgressText(true);
    }

    public ChromeLoader(Activity c) {
        context = c;
        context.setProgressBarVisibility(true);
    }

    public void setShowHideWebViewOnload(boolean b) {
        control_webview_show_hide_onload = b;
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {
        if (cb != null) {
            if (progress < 100) {
                cb.setProgress(progress);
                if (cb.getVisibility() == View.GONE) {
                    cb.setVisibility(View.VISIBLE);
                }
                if (control_webview_show_hide_onload) view.setVisibility(View.GONE);
            } else {
                cb.setVisibility(View.GONE);
                if (control_webview_show_hide_onload) view.setVisibility(View.VISIBLE);
            }

        }
        if (context != null) {
            context.setTitle("Loading...");
            context.setProgress(progress * 100);
            if (progress == 100)
                context.setTitle("My Cart");
        }

    }

}
