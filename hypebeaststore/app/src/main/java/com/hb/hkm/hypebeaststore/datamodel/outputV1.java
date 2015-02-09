package com.hb.hkm.hypebeaststore.datamodel;

import com.hb.hkm.hypebeaststore.tasks.Filtering;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class outputV1 {
    private ArrayList<Product> products;
    private int current_page;
    private int pages;
    private outputV1wrapAdapter adapter;

    public outputV1() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProduct(final int pos) {
        return products.get(pos);
    }

    public ArrayList<Term> sortedSize() {
        return Filtering.getSorted(adapter.getfacets().getSize().getTerms(), Filtering.SIZE_ORDER_REF);
    }

    /*public ArrayList<Term> sortedBrand() {
        return Filtering.getSorted(adapter.getfacets().getSize().getTerms(), Filtering.);

    }*/

    public ArrayList<Term> sortedCate() {
        return Filtering.getSorted(adapter.getfacets().getCategory().getTerms(), Filtering.CAT_ORDER_REF);

    }
}
