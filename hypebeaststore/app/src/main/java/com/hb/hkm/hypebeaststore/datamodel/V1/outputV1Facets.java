package com.hb.hkm.hypebeaststore.datamodel.V1;

import com.hb.hkm.hypebeaststore.datamodel.CTFilter;
import com.hb.hkm.hypebeaststore.datamodel.CTPrice;
import com.hb.hkm.hypebeaststore.datamodel.Termm;

import java.util.ArrayList;

/**
 * Created by hesk on 2/9/15.
 */
public class outputV1Facets {
    private CTFilter size;
    private CTFilter brand;
    private CTFilter category;
    private CTPrice price;

    public outputV1Facets() {
    }

    public CTFilter getSize() {
        return size;
    }

    public ArrayList<Termm> getBrand() {
        return brand.getTerms();
    }

    public CTFilter getCategory() {
        return category;
    }

    public ArrayList<Termm> getPrice() {
        return price.getRanges();
    }

    public CTPrice getPriceFilter() {
        return price;
    }
}
