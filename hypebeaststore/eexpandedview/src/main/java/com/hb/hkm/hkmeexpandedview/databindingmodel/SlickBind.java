package com.hb.hkm.hkmeexpandedview.databindingmodel;

/**
 * Created by hesk on 2/27/2015.
 */
public class SlickBind extends BasicDataBind {
    protected String url_img;

    public SlickBind() {
        super();
    }

    public SlickBind(String label, String url, String image_url_icon) {
        super(label, url);
        url_img = image_url_icon;
    }

    public String get_url_img() {
        return url_img;
    }

}
