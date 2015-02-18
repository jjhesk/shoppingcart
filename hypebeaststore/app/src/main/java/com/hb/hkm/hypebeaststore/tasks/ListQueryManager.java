package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hb.hkm.hypebeaststore.controller.Config;
import com.hb.hkm.hypebeaststore.controller.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1Adapter;
import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1ProductWrap;
import com.hb.hkm.hypebeaststore.datamodel.V2.outputV2;
import com.hb.hkm.hypebeaststore.datamodel.gsontool.TermFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by hesk on 2/4/15.
 */
public class ListQueryManager extends asyclient {
    private int page, limit;
    private boolean isReadingMore = false;
    protected static String TAG = "ListQueryManager";
    private final GsonBuilder gb = new GsonBuilder();
    private Gson g;
    //  private GsonTestCase1 cgons;

    public ListQueryManager(Context ccc) {
        super(ccc, (callback) ccc);
        page = 1;
        limit = Config.setting.single_page_items;

        gb.disableHtmlEscaping();
        //gb.registerTypeAdapter(Term.class, new TermDe());
        gb.registerTypeAdapterFactory(new TermFactory());
        gb.registerTypeAdapter(Float.class, new TypeAdapter<Float>() {

            @Override
            public Float read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String stringValue = reader.nextString();
                try {
                    Float value = Float.valueOf(stringValue);
                    return value;
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public void write(JsonWriter writer, Float value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }

        });

        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        //gb.setVersion(1.0);


        g = gb.create();
        // cgons = (GsonTestCase1) ccc;
    }

    public ListQueryManager setPageView(final int page_to_view) {
        page = page_to_view;
        isReadingMore = page_to_view > 1;
        return this;
    }

    public ListQueryManager setListLimit(final int new_limit) {
        limit = new_limit;
        return this;
    }

    @Override
    public asyclient setURL(String e) {
        String format_new = "%s?page=%d&limit=%d";
        url = String.format(format_new, e, page, limit);
        url += DataBank.msubmissionfilter.isEmpty() ? "" : DataBank.msubmissionfilter.getJson();
        Log.d(TAG, url);
        return this;
    }

    @Override
    protected void ViewConstruction() {


    }


    @Override
    protected void GSONParser(final String data) throws JsonSyntaxException, JsonIOException, JsonParseException {


        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        switch (Config.setting.APIversion) {
            case 1:
                final outputV1ProductWrap output_product = g.fromJson(data, outputV1ProductWrap.class);

                DataBank.current_product_list.addAll(output_product.getProducts());
                DataBank.result_total_pages = output_product.totalpages();
                DataBank.result_current_page = output_product.current_page();
                //cgons.addTextBody(data);


                if (!isReadingMore) {
                    outputV1Adapter opadp = g.fromJson(data, outputV1Adapter.class);
                    //  Log.d(TAG, opadp.toString());
                    DataBank.filter_list_price.clear();
                    DataBank.filter_list_brand.clear();
                    DataBank.filter_list_price.addAll(opadp.getFacet().getPrice());
                    DataBank.filter_list_brand.addAll(opadp.getFacet().getBrand());
                    DataBank.filter_price = opadp.getFacet().getPriceFilter();
                    opadp.sortedSize(DataBank.filter_list_size);
                    opadp.sortedCate(DataBank.filter_list_cat);
                }

                break;
            case 2:
                Log.d(TAG, data);
                final outputV2 outpro = g.fromJson(data, outputV2.class);
                DataBank.current_product_list2.addAll(outpro.getProducts());
                DataBank.result_total_pages = outpro.totalpages();
                DataBank.result_current_page = outpro.current_page();
                //cgons.addTextBody(data);
                if (!isReadingMore) {
                    DataBank.filter_list_price.clear();
                    DataBank.filter_list_brand.clear();

                  /*  outputV1Adapter outputadapter = g.fromJson(data, outputV1Adapter.class);

                    DataBank.filter_list_price.clear();
                    DataBank.filter_list_brand.clear();
                    DataBank.filter_list_price.addAll(outputadapter.getFacet().getPrice());
                    DataBank.filter_list_brand.addAll(outputadapter.getFacet().getBrand());
                    DataBank.filter_price = outputadapter.getFacet().getPriceFilter();
                    outputadapter.sortedSize(DataBank.filter_list_size);
                    outputadapter.sortedCate(DataBank.filter_list_cat);*/
                    //  Log.d(TAG, outputadapter.toString());
                }


                break;
        }
    }
}
