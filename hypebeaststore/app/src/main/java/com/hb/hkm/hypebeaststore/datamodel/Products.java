package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Products {
    private int id;
    private String name;
    private String description;
    private String price;
    private String sale_price;
    private TimeS available_on;
    private String sold_out_at;
    private String back_in_stock_at;
    private String slug;
    private boolean has_variants;
    private ArrayList<Variant> variants;
    private ArrayList<Attribute> attributes;
    // ignore options
    private ArrayList<Category> categories;
    private ArrayList<Category> brands;
    private ArrayList<Image> images;
    private ArrayList<simple_product> product_group;
}
