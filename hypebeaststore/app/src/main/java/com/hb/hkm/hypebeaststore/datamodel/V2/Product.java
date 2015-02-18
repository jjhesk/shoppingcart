package com.hb.hkm.hypebeaststore.datamodel.V2;

import java.util.ArrayList;

/**
 * Created by hesk on 2/18/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    // private int sale_price;
    private String brand;
    private String created_at;
    private String updated_at;
    private ArrayList<Image> images = new ArrayList<Image>();
    private ArrayList<Variant> variants = new ArrayList<Variant>();

    public boolean hasVariants() {
        return variants.size() > 0;
    }

    public ArrayList<Image> get_product_images() {
        return images;
    }

    public String get_cover_image() {
        return images.get(0).getPath();
    }

    public String get_brand_name() {
        return brand;
    }

    public String getPrice() {
        return the_price(String.valueOf((float) price / (float) 100));
    }

    private String the_price(final String p) {
        return "$" + p + " USD";
    }

    public String getSingleEndPoint() {
        return "";
    }

    public String getTitle() {
        return "";
    }

    public String get_desc() {
        return "";
    }

    public String getSalesPrice() {
        return "";
    }
/*
    public String[] getSizeVariants() {
        ArrayList<String> a = new ArrayList<String>();
        if (variants.size() == 0 || variants == null) return new String[]{};
        for (Variant s : variants) {
            a.add(s.getOptions().get(0).getVal() + "");
        }
        String[] c = new String[a.size()];
        c = a.toArray(c);
        return c;
    }
*/

}
