package com.hb.hkm.hypebeaststore.datamodel.V1;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hb.hkm.hypebeaststore.controller.DataBank;

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

    private int price_range_index = -1;
    private boolean newFilter = false;
    private query q = new query();

    static class query {
        String[] brand = new String[0];
        String[] category = new String[0];
        String[] size = new String[0];
        PriceF[] price = new PriceF[0];

        public query() {
        }
    }

    public void reset(final String title) {
        if (title.equalsIgnoreCase("price")) {
            unsetPrice();
        } else if (title.equalsIgnoreCase("category")) {
            unsetCate();
        } else if (title.equalsIgnoreCase("brand")) {
            unsetBrand();
        } else if (title.equalsIgnoreCase("size")) {
            unsetSize();
        }
    }

    public SubmissionFilter() {
    }

    public int getIndexPrice() {
        return price_range_index;
    }

    public int getIndexCat(String[] ls) {
        return q.category.length > 0 ? getIndex(ls, q.category[0]) : -1;
    }

    public int getIndexBrand(String[] ls) {
        return q.brand.length > 0 ? getIndex(ls, q.brand[0]) : -1;
    }

    public int getIndexSize(String[] ls) {
        return q.size.length > 0 ? getIndex(ls, q.size[0]) : -1;
    }

    private int getIndex(String[] ls, String item) {
        for (int i = 0; i < ls.length; i++) {
            if (ls[i].equalsIgnoreCase(item)) {
                return i;
            }
        }
        return -1;
    }

    public void setPrice(Range r) {
        q.price = new PriceF[]{
                new PriceF(r.getfrom() + "", r.getto() + "")
        };
        newfilter();
    }

    public void setCate(String b, int which) {
        q.category = new String[]{b};
        newfilter();
    }

    public void setSize(String b, int which) {
        q.size = new String[]{b};
        newfilter();
    }

    public void setBrand(String b, int which) {
        q.brand = new String[]{b};
        newfilter();

    }

    private void newfilter() {
        newFilter = true;
        DataBank.result_current_page = 1;
        DataBank.current_product_list.clear();
        DataBank.current_product_list2.clear();
    }

    public String getJson() {
        final GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        String filter = gson.toJson(this.q);
        String filter_url = Uri.encode(filter);
        if (newFilter) newFilter = false;
        return "&filter=" + filter_url;
    }


    public boolean reset() {
        boolean k = !isEmpty();
        if (k) {
            q.brand = new String[0];
            q.category = new String[0];
            q.size = new String[0];
            q.price = new PriceF[0];
            newfilter();
        }
        return k;
    }

    public void unsetBrand() {
        q.brand = new String[0];
    }

    public void unsetCate() {
        q.category = new String[0];
    }

    public void unsetSize() {
        q.size = new String[0];
    }

    public void unsetPrice() {
        q.price = new PriceF[0];
    }

    public boolean justSet() {
        final boolean g = newFilter;
        if (newFilter) newFilter = false;
        return g;
    }

    public boolean isEmpty() {
        final boolean a = q.brand.length == 0;
        final boolean b = q.category.length == 0;
        final boolean c = q.size.length == 0;
        final boolean d = q.price.length == 0;
        return a && b && c && d;
    }

    public boolean isEmptyBrand() {
        return q.brand.length == 0;
    }

    public boolean isEmptyCat() {
        return q.category.length == 0;
    }

    public boolean isEmptySize() {
        return q.size.length == 0;
    }

    public boolean isEmptyPrice() {
        return q.price.length == 0;
    }

    public void dialogSelection(final int which, final String title, String[] ls) {
        if (which > -1) {
            if (title.equalsIgnoreCase("price")) {
                price_range_index = which;
                setPrice(DataBank.filter_price.getRangeAt(which));
            } else if (title.equalsIgnoreCase("category")) {
                setCate(ls[which], which);
            } else if (title.equalsIgnoreCase("brand")) {
                setBrand(ls[which], which);
            } else if (title.equalsIgnoreCase("size")) {
                setSize(ls[which], which);
            }
        }
    }

}
