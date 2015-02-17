package com.hb.hkm.hypebeaststore.datamodel;

/**
 * Created by hesk on 2/3/15.
 */
public class Termm {

    private String term = "_";
    private int count = 0;

    public Termm() {
    }

    public Termm(String t) {
        term = t;
    }

    public Termm(String t, int c) {
        count = c;
        term = t;
    }

    public String theTerm() {
        return term;
    }

    public int getTheCount() {
        return count;
    }
}
