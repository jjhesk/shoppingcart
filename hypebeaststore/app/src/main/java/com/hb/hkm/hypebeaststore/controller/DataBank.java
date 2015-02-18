package com.hb.hkm.hypebeaststore.controller;

import com.hb.hkm.hypebeaststore.datamodel.V1.CTPrice;
import com.hb.hkm.hypebeaststore.datamodel.V1.CartProduct;
import com.hb.hkm.hypebeaststore.datamodel.V1.SubmissionFilter;
import com.hb.hkm.hypebeaststore.datamodel.V1.Termm;
import com.hb.hkm.hypebeaststore.datamodel.V2.outputV2;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class DataBank {
    public static String loaded = "";
    public static outputV2 prodcut_master_list;
    public static com.hb.hkm.hypebeaststore.datamodel.V2.Product product_single2;
    public static com.hb.hkm.hypebeaststore.datamodel.V1.Product product_single;
    public static final ArrayList<com.hb.hkm.hypebeaststore.datamodel.V2.Product> current_product_list2 = new ArrayList<com.hb.hkm.hypebeaststore.datamodel.V2.Product>();
    public static final ArrayList<com.hb.hkm.hypebeaststore.datamodel.V1.Product> current_product_list = new ArrayList<com.hb.hkm.hypebeaststore.datamodel.V1.Product>();
    public static final ArrayList<CartProduct> my_cart = new ArrayList<CartProduct>();


    public static final ArrayList<Termm> filter_list_size = new ArrayList<Termm>();
    public static final ArrayList<Termm> filter_list_cat = new ArrayList<Termm>();
    public static final ArrayList<Termm> filter_list_price = new ArrayList<Termm>();
    public static final ArrayList<Termm> filter_list_brand = new ArrayList<Termm>();

    public static CTPrice filter_price = new CTPrice();
    public static final SubmissionFilter msubmissionfilter = new SubmissionFilter();

    public static int result_total_pages = 0;
    public static int result_current_page = 0;
}
