package com.hb.hkm.hypebeaststore.datamodel.V1;

/**
 * Created by hesk on 2/3/15.
 */
public class TermWrap {

    private String term = "";
    private int count = 0;

    public TermWrap() {
    }

    public TermWrap(String t) {
        term = t;
    }

    public TermWrap(String t, int c) {
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
