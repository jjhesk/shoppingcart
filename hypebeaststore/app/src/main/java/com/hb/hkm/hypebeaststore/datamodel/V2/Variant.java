package com.hb.hkm.hypebeaststore.datamodel.V2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hesk on 2/18/15.
 */
public class Variant {
    private int id;
    private boolean master;
    private ArrayList<OptionsV2> options;
    private int on_hand;
    private int on_hold;

    public Variant() {

    }

    public String stock_logic() {
        int delta = on_hand - on_hold;
        if (delta == 1) {
            return "/ Last One Left";
        } else if (delta == 0) {
            return "/ Out of Stock";
        } else {
            return "";
        }
    }

    public HashMap<String, String> getpair() throws Exception {
        if (options != null && options.size() > 0) {
            return options.get(0).getpair();
        } else
            throw new Exception("no options");

    }


    public String displayLastItem() {
        return countdelta() == 1 ? "Last Item" : "";
    }

    private int countdelta() {
        return on_hand - on_hold;
    }

    public boolean instock() {
        return countdelta() > 0;
    }
}
