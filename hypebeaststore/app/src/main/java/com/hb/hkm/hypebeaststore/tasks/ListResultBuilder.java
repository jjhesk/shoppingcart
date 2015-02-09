package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.StoreFront;
import com.hb.hkm.hypebeaststore.datamodel.outputV1;
import com.hb.hkm.hypebeaststore.datamodel.outputV2;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;

import java.io.StringReader;

/**
 * Created by hesk on 2/4/15.
 */
public class ListResultBuilder extends asyclient {
    public ListResultBuilder(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void ViewConstruction() {
        ((StoreFront) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((StoreFront) ctx).getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new GridDisplay())
                        .commit();
            }
        });

    }

    @Override
    protected void GSONParser(final String data) throws JsonSyntaxException, JsonIOException, JsonParseException {
        final GsonBuilder gb = new GsonBuilder();
        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        final Gson g = gb.create();


        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        switch (Config.setting.APIversion) {
            case 1:
                outputV1 output_time = g.fromJson(reader, outputV1.class);
                Log.d(TAG, output_time.toString());
                DataBank.current_product_list = output_time.getProducts();
                DataBank.filter_list_size = output_time.sortedSize();
                DataBank.filter_list_cat = output_time.sortedCate();
                break;
            case 2:
                outputV2 out = g.fromJson(reader, outputV2.class);
                Log.d(TAG, out.toString());
                //DataBank.product_master_list = out;
                break;
        }
    }
}
