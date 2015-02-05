package com.hb.hkm.hypebeaststore;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.fragments.singleComponents.ViewHolder;

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
           /* sync = new WorkerBuilder(this, new asyclient.callback() {
                @Override
                public void onSuccess(String data) {
                    RunLDialogs.strDemo2(StoreFront.this, data);
                }
                @Override
                public void onFailure(String message) {
                    RunLDialogs.strDemo2(StoreFront.this, message);
                }
                @Override
                public void beforeStart(asyclient task) {
                }
            });
            sync.setURL(Config.hometech).execute();*/
            setContentView(R.layout.product_single);
            vh = new ViewHolder(this, getIntent().getExtras().getInt(Config.single_id, 0));
        } else {
            final int pos = (int) savedInstanceState.getSerializable(Config.single_id);
        }
    }


}
