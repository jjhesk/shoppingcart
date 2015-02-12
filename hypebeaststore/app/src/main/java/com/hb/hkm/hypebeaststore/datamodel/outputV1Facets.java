package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/9/15.
 */
public class outputV1Facets {
    private filter size;
    private filter brand;
    private filter category;
    private filterPrice price;

    public outputV1Facets() {
    }

    public filter getSize() {
        return size;
    }

    public filter getBrand() {
        return brand;
    }

    public filter getCategory() {
        return category;
    }

    public ArrayList<Term> getPrice() {
        return price.getRanges();
    }
}
