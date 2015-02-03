package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Variant {
    private int id;
    private String presentation;
    private String sku;
    private String price;
    private String sale_price;
    private boolean is_master;
    private int on_hand;
    private int on_hold;
    private boolean in_stock;
    private int product_id;
    private ArrayList<Option> option_values;


}
