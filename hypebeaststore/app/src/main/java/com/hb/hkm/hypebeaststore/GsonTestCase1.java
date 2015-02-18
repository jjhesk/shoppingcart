package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.controller.Config;
import com.hb.hkm.hypebeaststore.controller.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.V1.Termm;
import com.hb.hkm.hypebeaststore.fragments.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.tasks.ListQueryManager;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

public class GsonTestCase1 extends ActionBarActivity implements asyclient.callback {
    private ListQueryManager sync;
    public static String TAG = "store front here";
    private Bundle msavedInstanceState;

    private int page = 1;

    @Override
    public void onSuccess(String data) {
        //RunLDialogs.strDemo2(StoreFront.this, data);
        //DataBank.result_total_pages
        if (page < 3) {
            page++;
            for (final Termm tm : DataBank.filter_list_cat) {
                addTextBody(tm.theTerm());
            }
        } else {
            page = 1;
        }
        loadingList(page);
        Tool.trace(getApplicationContext(), "success page @ " + page);
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(GsonTestCase1.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }


    public void loadingList(final int page) {
        // final Bundle instance = savedInstanceState;
        sync = new ListQueryManager(this);
        sync.setPageView(page)
                .setURL(Config.clothing)
                .execute();
        addTextBody2(Config.clothing);
    }

    private TextView result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_debug_storefront);

        result1 = (TextView) findViewById(R.id.tabsed);
        result2 = (TextView) findViewById(R.id.contented);
        loadingList(page);
        result1.setMovementMethod(new ScrollingMovementMethod());
        result2.setMovementMethod(new ScrollingMovementMethod());
    }

    private void addTextBody2(String mstring) {
        String w1 = result1.getText().toString() + mstring;
        result1.setText(w1);
    }

    private void addTextBody(String mstring) {
        String w1 = result2.getText().toString() + mstring;
        result2.setText(w1);
        result1.setText(mstring);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_front, menu);
        return true;
    }


}
