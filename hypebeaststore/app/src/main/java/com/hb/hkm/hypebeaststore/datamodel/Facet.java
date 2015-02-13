package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Facet {
    private filter size;
    private filter brand;
    private filter category;
    private filterPrice price;

    public Facet() {
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
