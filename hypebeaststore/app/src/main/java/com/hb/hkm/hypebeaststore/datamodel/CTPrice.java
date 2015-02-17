package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class CTPrice {

    private ArrayList<Range> ranges;

    public CTPrice() {
    }

    public Range getRangeAt(int which) {
        return ranges.get(which);
    }

    public ArrayList<Termm> getRanges() {
        final ArrayList<Termm> newList = new ArrayList<Termm>();
        for (Range r : ranges) {
            newList.add(r.getTerm());
        }

        return newList;
    }
}
