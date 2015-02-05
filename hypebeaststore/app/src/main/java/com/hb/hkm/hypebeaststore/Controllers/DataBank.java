package com.hb.hkm.hypebeaststore.Controllers;

import com.hb.hkm.hypebeaststore.datamodel.CartProduct;
import com.hb.hkm.hypebeaststore.datamodel.Product;
import com.hb.hkm.hypebeaststore.datamodel.output;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class DataBank {
    public static String loaded = "";
    public static output product_master_list;
    public static Product product_single;
    public static ArrayList<CartProduct> my_cart = new ArrayList<CartProduct>();
}
