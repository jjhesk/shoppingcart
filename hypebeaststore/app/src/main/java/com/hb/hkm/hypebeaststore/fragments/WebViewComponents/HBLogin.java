package com.hb.hkm.hypebeaststore.fragments.WebViewComponents;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hesk on 2/11/15.
 */
public class HBLogin extends WebViewClient {
    private static final String TAG = "HBloginFB";
    private final WebView mWebView;
    private boolean loadingFinished = true;
    private boolean redirect = false;
    private static final String showHTMLHack = "javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');";
    private static final String blockr_downloadapp = "https://m.facebook.com/click.php?redir_url=market%3A%2F%2Fdetails%3Fid%3Dcom.facebook.katana&app_id=350685531728&cref=mb&no_fw=1&refid=9";
    private static final String blockr_helper = "https://m.facebook.com/help/?refid=9";
    private static final String blockr_createnewaccount = "https://m.facebook.com/r.php?loc=bottom";

    public HBLogin(final WebView fmWebView) {
        mWebView = fmWebView;

    }

    public String convertToString(InputStream inputStream) {
        final StringBuffer string = new StringBuffer();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                string.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return string.toString();
    }

    protected void getURL(final String urlConection) {
        // here you will use the url to access the headers.
        // in this case, the Content-Length one
        URL url;
        URLConnection conexion;
        try {
            url = new URL(urlConection);
            conexion = url.openConnection();
            conexion.setConnectTimeout(3000);
            conexion.connect();
            // get the size of the file which is in the header of the request
            int size = conexion.getContentLength();
        } catch (Exception e) {
        }


        // and here, if you want, you can load the page normally
        String htmlContent = "";

        HttpGet httpGet = new HttpGet(urlConection);
        // this receives the response
        HttpResponse response;
        try {
            /*response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    htmlContent = convertToString(inputStream);
                }
            }*/
        } catch (Exception e) {
        }

        mWebView.loadData(htmlContent, "text/html", "utf-8");
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        //  Tool.trace();
        Log.d(TAG, "errorCode: " + errorCode);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
        if (!loadingFinished) {
            redirect = true;
        }

        if (urlNewString.equalsIgnoreCase(blockr_downloadapp))
            return true;
        if (urlNewString.equalsIgnoreCase(blockr_helper))
            return true;
        if (urlNewString.equalsIgnoreCase(blockr_createnewaccount))
            return true;

        Log.d(TAG, "login: " + urlNewString);
        loadingFinished = false;
        mWebView.loadUrl(urlNewString);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!redirect) {
            loadingFinished = true;
        }

        if (loadingFinished && !redirect) {
            //HIDE LOADING IT HAS FINISHED

        } else {
            redirect = false;
        }
        mWebView.loadUrl(showHTMLHack);
    }
}
