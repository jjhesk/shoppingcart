package com.hb.hkm.hypebeaststore;


import android.content.Intent;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SelectView.ACTION_ON_SELECT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                Bundle b = data.getExtras();
                int getPos = b.getInt(SelectView.pos, -1);
                int kind = b.getInt(SelectView.kind, 0);
                String displayVal = b.getString(SelectView.selection_val, "");
                // Do something with the contact here (bigger example below)
                switch (kind) {
                    case SelectView.SIZE:
                        // load = DataBank.filter_list_size;
                        vh.getOptionUIs().setSize(displayVal);
                        break;
                    case SelectView.COLOR:
                        vh.getOptionUIs().setColor(displayVal);
                        // load = DataBank.filter_list_cat;
                        break;
                    case SelectView.QTY:
                        vh.getOptionUIs().setQty(displayVal);
                        //  load = DataBank.filter_list_size;
                        break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        actionAsUp();
    }

}
