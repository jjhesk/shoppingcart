package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.controller.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.V2.wrap_product;
import com.hb.hkm.hypebeaststore.datamodel.V2.single_wrap;

import java.io.StringReader;

/**
 * Created by hesk on 2/5/15.
 */
public class SingleBuilder extends asyclient {
    public SingleBuilder(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void GSONParser(String data) {
        Log.d(TAG, data);

        final GsonBuilder gb = new GsonBuilder();
        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        final Gson g = gb.create();
        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        // wrap_product o = g.fromJson(reader, wrap_product.class);
        wrap_product o = g.fromJson(reader, wrap_product.class);
        Log.d(TAG, o.toString());
        // DataBank.product_single = o.getp();
        DataBank.product_single2 = o.getp();
    }


    @Override
    protected void ViewConstruction() {

    }
}
