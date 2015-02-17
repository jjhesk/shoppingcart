package com.hb.hkm.hypebeaststore.datamodel.V1;

import com.hb.hkm.hypebeaststore.datamodel.Product;
import com.hb.hkm.hypebeaststore.datamodel.Termm;
import com.hb.hkm.hypebeaststore.datamodel.V2.outputV1wrapAdapter;
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

    public int totalpages() {
        return pages;
    }

    public int current_page() {
        return current_page;
    }

    public outputV1Facets getFacet() {
        return adapter.getfacets();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void sortedSize(final ArrayList<Termm> existingList) {
        sortingWorker(
                existingList,
                Filtering.getSorted(adapter.getfacets().getSize().getTerms(),
                        Filtering.SIZE_ORDER_REF)
        );
    }

    public void sortedCate(final ArrayList<Termm> existingList) {
        sortingWorker(
                existingList,
                Filtering.getSorted(adapter.getfacets().getCategory().getTerms(),
                        Filtering.CAT_ORDER_REF)
        );
    }

    /*public ArrayList<Term> sortedBrand() {
        return Filtering.getSorted(adapter.getfacets().getSize().getTerms(), Filtering.);

    }*/
    private void sortingWorker(final ArrayList<Termm> existingList,
                               final ArrayList<Termm> mlist) {
        existingList.clear();
        existingList.addAll(mlist);
    }

}