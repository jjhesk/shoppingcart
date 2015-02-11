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
import com.hb.hkm.hypebeaststore.datamodel.outputV1;
import com.hb.hkm.hypebeaststore.datamodel.outputV2;

import java.io.StringReader;

/**
 * Created by hesk on 2/4/15.
 */
public class ListResultBuilder extends asyclient {
    private int page, limit;

    public ListResultBuilder(Context ccc, callback cb) {
        super(ccc, cb);
        page = 1;
        limit = Config.setting.single_page_items;
    }

    public ListResultBuilder setPageView(final int page_to_view) {
        page = page_to_view;
        return this;
    }

    public ListResultBuilder setListLimit(final int new_limit) {
        limit = new_limit;
        return this;
    }

    @Override
    public asyclient setURL(String e) {
        final String format_new = "%s?page=%d&limit=%d";
        url = String.format(format_new, e, page, limit);
        return this;
    }

    @Override
    protected void ViewConstruction() {


    }

    public void buildExistingView() {
        ViewConstruction();
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

                DataBank.current_product_list.addAll(output_time.getProducts());

                output_time.sortedSize(DataBank.filter_list_size);
                output_time.sortedCate(DataBank.filter_list_cat);

                DataBank.result_total_pages = output_time.totalpages();
                DataBank.result_current_page = output_time.current_page();

                break;
            case 2:
                outputV2 out = g.fromJson(reader, outputV2.class);
                Log.d(TAG, out.toString());
                //DataBank.product_master_list = out;
                break;
        }
    }
}
