package com.hb.hkm.hypebeaststore.datamodel;

/**
 * Created by hesk on 2/3/15.
 */
public class Range {
    private int from = 0;
    private int to = 0;
    private int count = 0;
    private int min = 0;
    private int max = 0;
    private int total_count = 0;
    private int total = 0;

    //private float meqn;
    public Range() {
    }

    public Term getTerm() {

        String d = "";
        if (from == 0) {
            d = money(to) + " and under";
        } else if (to == 0) {
            d = money(to) + " and above";
        } else {
            d = money(from) + " - " + money(to);
        }

        return new Term(d, count);
    }

    private String money(int n) {
        return "$" + (int) n / 100 + ".00";
    }


}
