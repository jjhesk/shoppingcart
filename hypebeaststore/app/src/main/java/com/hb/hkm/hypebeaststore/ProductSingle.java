package com.hb.hkm.hypebeaststore;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.fragments.dialogComponents.RunLDialogs;
import com.hb.hkm.hypebeaststore.fragments.singleComponents.ViewHolder;
import com.hb.hkm.hypebeaststore.tasks.ListResultBuilder;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

/**
 * Created by hesk on 2/5/15.
 */
public class ProductSingle extends ActionBarActivity {
    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_front);
        if (savedInstanceState == null) {

            setContentView(R.layout.product_single);
            vh = new ViewHolder(this, getIntent().getExtras().getInt(Config.single_id, 0));
        } else {
            final int pos = (int) savedInstanceState.getSerializable(Config.single_id);
        }
    }


}
