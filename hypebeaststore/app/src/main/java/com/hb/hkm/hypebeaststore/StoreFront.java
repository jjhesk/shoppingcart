package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.fragments.GridComponents.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.dialogComponents.RunLDialogs;
import com.hb.hkm.hypebeaststore.tasks.ListResultBuilder;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

public class StoreFront extends ActionBarActivity implements asyclient.callback {
    private ListResultBuilder sync;
    public static String TAG = "store front here";

    @Override
    public void onSuccess(String data) {
        RunLDialogs.strDemo2(StoreFront.this, data);

        final GridDisplay mdisplay = new GridDisplay();
        mdisplay.setGridEvents(new GrideDisplayEvent() {
            @Override
            public void requestmoreitems(int page) {
                if (sync.getStatus() == AsyncTask.Status.FINISHED) {

                }
            }
        });

        StoreFront.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StoreFront.this
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, mdisplay)
                        .commit();
            }
        });
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(StoreFront.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }


    public void loadingList(Bundle savedInstanceState, int page_at) {
        // final Bundle instance = savedInstanceState;
        if (savedInstanceState == null) {
            if (DataBank.current_product_list == null) {
                sync = new ListResultBuilder(this, this);
                sync
                        .setPageView(page_at)
                        .setURL(Config.hometech)
                        .execute();

            } else {
                Log.d(TAG, "sync here");
            }
        } else {
            sync.buildExistingView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_front);
        loadingList(savedInstanceState, 1);
    }

    private void add_menu() {
        /*SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.f_menu);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_front, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startPage(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
