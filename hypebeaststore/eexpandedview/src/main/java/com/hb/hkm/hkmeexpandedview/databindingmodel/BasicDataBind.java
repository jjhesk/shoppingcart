package com.hb.hkm.hkmeexpandedview.databindingmodel;

/**
 * Created by hesk on 2/25/15.
 */
public class BasicDataBind{
    protected String
            mlabel,
            murl;

    public BasicDataBind() {
    }

    public BasicDataBind(String label, String url) {
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
