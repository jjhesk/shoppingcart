package com.hb.hkm.hypebeaststore.datamodel;

/**
 * Created by hesk on 2/3/15.
 */
public class Term {

    private String term = "";
    private int count = 0;

    public Term() {
    }

    public Term(String t) {
        term = t;
    }

    public Term(String t, int c) {
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
