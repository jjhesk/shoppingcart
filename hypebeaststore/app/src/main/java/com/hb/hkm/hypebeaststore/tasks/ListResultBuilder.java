package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.StoreFront;
import com.hb.hkm.hypebeaststore.datamodel.output;
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

       /*
         DataBank.product_master_list = g.fromJson(data, new TypeToken<ArrayList<Product>>() {
            }.getType());
        */



        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        output output_time = g.fromJson(reader, output.class);
        Log.d(TAG, output_time.toString());
        DataBank.product_master_list = output_time;
    }
}
