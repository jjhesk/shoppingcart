package com.hb.hkm.hkmeexpandedview.list;

/**
 * Created by hesk on 2/25/15.
 */
public class DataBind {
    private String
            mlabel, murl;

    public DataBind(String label, String url) {
        mlabel = label;
        murl = url;
    }

    public String label() {
        return mlabel;
    }

    public String url() {
        return murl;
    }

}
