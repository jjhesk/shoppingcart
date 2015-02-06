package com.hb.hkm.hypebeaststore;


import android.os.Bundle;

import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.fragments.singleComponents.ViewHolder;

/**
 * Created by hesk on 2/5/15.
 */
public class ProductSingle extends BasicSupportActionBarHKM {
    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.product_single);
            vh = new ViewHolder(this, getIntent().getExtras().getInt(Config.single_id, -1));
        } else {
            final int pos = (int) savedInstanceState.getSerializable(Config.single_id);
        }
    }


}
