package com.hb.hkm.hypebeaststore.datamodel.V2;

import com.asynhkm.productchecker.Util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hesk on 2/18/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private int sale_price;
    private String created_at;
    private String updated_at;

    private connection _links;
    private RelatedGroups _embedded;
    private ArrayList<Image> images = new ArrayList<Image>();
    private ArrayList<Variant> variants = new ArrayList<Variant>();

    public ArrayList<Image> get_product_images() {
        return images;
    }

    public String get_cover_image() {
        return images.get(0).smallImage();
    }

    public String get_brand_name() {
        return _embedded.brands().get(0).getcodename();
    }

    public String getPrice() {
        return the_price(String.valueOf((float) price / (float) 100));
    }

    private String the_price(final String p) {
        return "$" + p + " USD";
    }

    public String getSingleEndPoint() {
        return _links == null ? "" : _links.getself().gethref();
    }

    public String getTitle() {
        return name;
    }

    public String get_desc() {
        return description;
    }

    public String getSalesPrice() {
        return "";
    }

    public HashMap<String, String> getMappedVariants() {
        HashMap<String, String> a = new HashMap<String, String>();
        if (variants.size() == 0 || variants == null) return new HashMap<String, String>();
        try {
            for (Variant s : variants) {
                a.putAll(s.getpair());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tool.sortHashMapByValuesD(a);
    }

}
