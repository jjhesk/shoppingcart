package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class output {
    private ArrayList<Product> products;
    private int current_page;
    private int pages;

    public output() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProduct(final int pos) {
        return products.get(pos);
    }


}
