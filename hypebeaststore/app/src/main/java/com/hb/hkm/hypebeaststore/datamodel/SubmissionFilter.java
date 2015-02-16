package com.hb.hkm.hypebeaststore.datamodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;

/**
 * Created by hesk on 2/16/15.
 */
public class SubmissionFilter {
    static class PriceF {
        final String from;
        final String to;

        public PriceF(String x1, String x2) {
            from = x1;
            to = x2;
        }
    }

    private String[] brand = new String[0];
    private String[] category = new String[0];
    private String[] size = new String[0];
    private PriceF[] price = new PriceF[0];


    private boolean newFilter = false;

    public SubmissionFilter() {
    }

    public void setPrice(Range r) {
        String from = r.getfrom() + "";
        String to = r.getto() + "";
        price = new PriceF[]{
                new PriceF(r.getfrom() + "", r.getto() + "")
        };
        newfilter();
    }

    public void setCate(String b) {
        category = new String[]{b};
        newfilter();
    }

    public void setSize(String b) {
        size = new String[]{b};
        newfilter();
    }

    public void setBrand(String b) {
        brand = new String[]{b};
        newfilter();
    }

    private void newfilter() {
        newFilter = true;
        DataBank.result_current_page = 1;
    }

    public String getJson() {
        final GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        String filter = gson.toJson(this);
        return "&filter=" + filter;
    }

    public void reset() {
        brand = new String[0];
        category = new String[0];
        size = new String[0];
        price = new PriceF[0];
    }

    public void unsetBrand() {
        brand = new String[0];
    }

    public void unsetCate() {
        category = new String[0];
    }

    public void unsetSize() {
        size = new String[0];
    }

    public void unsetPrice() {
        price = new PriceF[0];
    }

    public boolean justSet() {
        final boolean g = newFilter;
        if (newFilter) newFilter = false;
        return g;
    }

    public boolean isEmpty() {
        final boolean a = brand.length == 0;
        final boolean b = category.length == 0;
        final boolean c = size.length == 0;
        final boolean d = price.length == 0;
        return a && b && c && d;
    }
}
