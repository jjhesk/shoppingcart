package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.tasks.ListQueryManager;
import com.hb.hkm.hypebeaststore.tasks.asyclient;
import com.hb.hkm.hypebeaststore.widgets.dialogcom.RunLDialogs;

public class StoreFrontOld extends ActionBarActivity implements asyclient.callback {
    private ListQueryManager sync;
    public static String TAG = "store front here";
    final GridDisplay mdisplay = new GridDisplay();
    private Bundle msavedInstanceState;

    //overridePendingTransition(0, 0);
    @Override
    public void onSuccess(String data) {
        //RunLDialogs.strDemo2(StoreFront.this, data);
        StoreFrontOld.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mdisplay.notifyList();
            }
        });
        Tool.trace(getApplicationContext(), "new items");
    }

    @Override
    public void onFailure(String message, int code) {
        RunLDialogs.strDemo2(StoreFrontOld.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }


    public void loadingList(final int page_at) {
        // final Bundle instance = savedInstanceState;
        try {
            sync = new ListQueryManager(this);
            sync.setPageView(page_at)
                    .setURL(Config.hometech)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_store_front);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mdisplay)
                    .commit();
            //  if (DataBank.current_product_list.size() == 0) {
            if (retent.current_product_list2.size() == 0) {
                loadingList(1);
            }
        } else {
            msavedInstanceState = savedInstanceState;
        }


        mdisplay.setGridEvents(new GrideDisplayEvent() {
            @Override
            public void requestmoreitems(int page) {
                if (sync != null) {
                    if (sync.getStatus() == AsyncTask.Status.FINISHED) {
                        loadingList(page);
                    }
                }
            }
        });
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
