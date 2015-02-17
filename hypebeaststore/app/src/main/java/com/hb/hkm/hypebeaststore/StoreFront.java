package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.controller.Config;
import com.hb.hkm.hypebeaststore.controller.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.Termm;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.fragments.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.tasks.Filtering;
import com.hb.hkm.hypebeaststore.tasks.ListQueryManager;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class StoreFront extends ActionBarActivity implements
        MaterialTabListener,
        asyclient.callback {
    private ListQueryManager sync;
    public static String TAG = "store front here";
    final GridDisplay mdisplay = new GridDisplay();
    private Bundle msavedInstanceState;
    private MaterialTabHost mTab;
    protected ProgressBar mProgressBar;

    protected void setLoadingAnimation() {
      /*  mProgressBar.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                .sweepSpeed(1f)
                .strokeWidth(3f)
                .style(CircularProgressDrawable.Style.ROUNDED)

                .build();*/

    }

    @Override
    public void onSuccess(String data) {
        //RunLDialogs.strDemo2(StoreFront.this, data);
        StoreFront.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mdisplay.notifyList();
                if (DataBank.result_current_page == 1) {
                    initTabs();
                    Log.d(TAG, "request init tab");
                }
            }
        });
        Tool.trace(getApplicationContext(), "added items");
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(StoreFront.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }

    private String query_url;

    public void setQuery(final String url) {
        query_url = url;
    }

    private String getURLQ() {
        if (query_url == null) query_url = Config.newarrivals;
        return query_url;
    }

    public void loadingList(final int page_at) {
        // final Bundle instance = savedInstanceState;
        sync = new ListQueryManager(this);
        sync.setPageView(page_at)
                .setURL(getURLQ())
                .execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // initTabs();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_store_front);
        } else {
            msavedInstanceState = savedInstanceState;
        }


        // progress2.
        // final CircleProgressBar progress2 = (CircleProgressBar) findViewById(R.id.progressBar);
        mdisplay.setGridEvents(new GrideDisplayEvent() {
            @Override
            public void requestmoreitems(int page) {
                int p = page;
                if (sync != null) {
                    if (sync.getStatus() == AsyncTask.Status.FINISHED) {
                        loadingList(page);
                    }
                } else {
                    sync = new ListQueryManager(StoreFront.this);
                    loadingList(page);
                }
            }
        });

        mTab = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mdisplay)
                .commit();


        if (DataBank.current_product_list.size() == 0) {
            loadingList(1);
        } else {
            initTabs();
        }

    }

    final ArrayList<String> items_list = new ArrayList<String>();

    private void initTabs() {
        //  items_list.clear();
        if (mTab.getChildCount() == 0) {
            if (DataBank.filter_list_brand.size() > 0) items_list.add("Brand");
            if (DataBank.filter_list_cat.size() > 0) items_list.add("Category");
            if (DataBank.filter_list_size.size() > 0) items_list.add("Size");
            if (DataBank.filter_list_price.size() > 0) items_list.add("Price");
            for (int i = 0; i < items_list.size(); i++) {
                String txt = items_list.get(i);
                mTab.addTab(mTab.newTab().setTabListener(this).setText(txt));
            }
            // Log.d(TAG, items_list.size() + " completed request for top menu");
            // Tool.trace(this, TAG + "completed request for the top menu. there are : " + items_list.size() + " items");
            mTab.notifyDataSetChanged();
        } else {
            // mTab.removeAllViews();
            // mTab.notifyDataSetChanged();
        }


    }


    private void listdialog(final String title, final String[] list) {
        int list_index_selection = -1;
        //Tool.trace(getApplicationContext(), tit + " : " + list.toString());
        if (title.equalsIgnoreCase("price")) {
            list_index_selection = DataBank.msubmissionfilter.getIndexPrice();
            // list_index_selection = DataBank.msubmissionfilter.getIndex(list, DataBank.filter_price.getRangeAt(which));
        } else if (title.equalsIgnoreCase("category")) {
            list_index_selection = DataBank.msubmissionfilter.getIndexCat(list);
        } else if (title.equalsIgnoreCase("brand")) {
            list_index_selection = DataBank.msubmissionfilter.getIndexBrand(list);
        } else if (title.equalsIgnoreCase("size")) {
            list_index_selection = DataBank.msubmissionfilter.getIndexSize(list);
        } else {

        }

        final MaterialDialog md = new MaterialDialog.Builder(this)
                .title(title)
                .items(list)

                .itemsCallbackSingleChoice(list_index_selection,
                        new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Tool.trace(getApplicationContext(), text + "");
                            }
                        })

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        DataBank.msubmissionfilter.dialogSelection(dialog.getSelectedIndex(), title, list);
                        mdisplay.notifyList();
                        loadingList(1);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        DataBank.msubmissionfilter.reset(title);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {

                    }
                })
                .positiveText(R.string.action_okay_filter)
                .negativeText(R.string.action_clear_filter)
                .show();

    }

    private void beforedialog(String title, final ArrayList<Termm> terms) {
        if (title.equalsIgnoreCase("Brand")) {
            listdialog(title, Filtering.TermsAsListAlphabetical(terms));
        } else
            listdialog(title, Filtering.TermsAsList(terms));
    }

    private void tab_selected_final(MaterialTab materialTab) {
        // Tool.trace(this, materialTab.getView().getId());
        int tabPos = materialTab.getPosition();
        final String title = items_list.get(tabPos);
        if (title.equalsIgnoreCase("Brand")) {
            beforedialog(title, DataBank.filter_list_brand);
        } else if (title.equalsIgnoreCase("Category")) {
            beforedialog(title, DataBank.filter_list_cat);
        } else if (title.equalsIgnoreCase("Size")) {
            beforedialog(title, DataBank.filter_list_size);
        } else if (title.equalsIgnoreCase("Price")) {
            beforedialog(title, DataBank.filter_list_price);
        }
        mTab.setSelectedNavigationItem(tabPos);
        Tool.trace(getApplicationContext(), "pos=" + tabPos + ". pressed title = " + title);

    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //pager.setCurrentItem(tab.getPosition());
        tab_selected_final(materialTab);
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
        tab_selected_final(materialTab);
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
        //  tab_selected_final(materialTab);
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
        } else if (id == R.id.action_clear_all) {
            if (DataBank.msubmissionfilter.reset()) {
                loadingList(1);
            }
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
