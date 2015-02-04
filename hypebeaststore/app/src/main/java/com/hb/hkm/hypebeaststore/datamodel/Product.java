package com.hb.hkm.hypebeaststore.datamodel;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private String price;
    private String sale_price;
    private TimeWrap available_on;
    //private String sold_out_at;
    //private String back_in_stock_at;
    private String slug;
    private boolean has_variants;
    private ArrayList<Variant> variants;
    private ArrayList<Attribute> attributes;
    // ignore options
    private ArrayList<Category> categories;
    private ArrayList<Category> brands;
    private ArrayList<Image> images;
    private ArrayList<simple_product> product_group;

    public Product() {
    }

    public String getTitle() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String get_cover_image() {
        return images.get(0).getM_url();
    }
}
