package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class filterPrice {
    private String _type;
    private ArrayList<Range> ranges;

    public filterPrice() {
    }

    public ArrayList<Term> getRanges() {
        final ArrayList<Term> newList = new ArrayList<Term>();
        for (Range r : ranges) {
            newList.add(r.getTerm());
        }

        return newList;
    }
}
