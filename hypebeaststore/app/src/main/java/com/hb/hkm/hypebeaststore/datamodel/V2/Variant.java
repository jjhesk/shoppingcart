package com.hb.hkm.hypebeaststore.datamodel.V2;

import java.util.ArrayList;

/**
 * Created by hesk on 2/18/15.
 */
public class Variant {
    private int id;
    private boolean master;
    private ArrayList<OptionsV2> options;
    private int on_hand;
    private int on_hold;
    //these are post process and handled by the program and they are not from the json
    private String variant_type;
    private String option_name;
    private String stock_config;
    private boolean instock;

    public Variant() {

    }

    public int getId() {
        return id;
    }

    /**
     * some pre-processing from the data
     *
     * @return
     */
    public Variant init() {
        variant_type = options.get(0).getpair()[0];
        option_name = options.get(0).getpair()[1];

        final int delta = on_hand - on_hold;
        if (delta == 1) {
            stock_config = "/ Last One Left";
        } else if (delta == 0) {
            stock_config = "/ Out of Stock";
        } else {
            stock_config = "";
        }
        instock = delta > 0;

        return this;
    }

    public String getType() {
        return variant_type;
    }

    public String getMetaValueFromType() {
        return option_name;
    }

    public String stock_logic_configuration() {
        return stock_config;
    }

    public String displayLastItem() {
        return instock ? "Last Item" : "";
    }

    public boolean instock() {
        return instock;
    }
}
