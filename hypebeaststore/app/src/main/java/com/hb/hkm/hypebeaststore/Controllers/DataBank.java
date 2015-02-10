package com.hb.hkm.hypebeaststore.Controllers;

import com.hb.hkm.hypebeaststore.datamodel.CartProduct;
import com.hb.hkm.hypebeaststore.datamodel.Product;
import com.hb.hkm.hypebeaststore.datamodel.Term;
import com.hb.hkm.hypebeaststore.datamodel.outputV2;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class DataBank {
    public static String loaded = "";
    public static outputV2 prodcut_master_list;
    public static Product product_single;
    public static ArrayList<Product> current_product_list;
    public static ArrayList<CartProduct> my_cart = new ArrayList<CartProduct>();
    public static ArrayList<Term> filter_list_size;
    public static ArrayList<Term> filter_list_cat;

    public static int result_total_pages = 0;
    public static int result_current_page = 0;
}
