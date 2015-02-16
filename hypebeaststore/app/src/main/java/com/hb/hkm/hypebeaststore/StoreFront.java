package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.Term;
import com.hb.hkm.hypebeaststore.fragments.GridComponents.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.dialogComponents.RunLDialogs;
import com.hb.hkm.hypebeaststore.tasks.Filtering;
import com.hb.hkm.hypebeaststore.tasks.ListQueryManager;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomListDialog;


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
        if (query_url == null) query_url = Config.hometech;
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
        // mTab.removeAllViews();
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
    }

    private String mfilter_title;

    private void listdialog(String tit, String[] listitems, int preSelect) {
        //Tool.trace(getApplicationContext(), tit + " : " + listitems.toString());
        mfilter_title = tit;
        final CustomListDialog.Builder builder = new CustomListDialog.Builder(this, "nif", listitems);
        // Now we can any of the following methods.
        // builder.content(String content);
        // builder.darkTheme(false);
        // builder.typeface(Typeface.createFromAsset(new AssetManager().));
        builder.titleTextSize(R.dimen.dialog_title);
        builder.titleAlignment(BaseDialog.Alignment.CENTER);// Use either Alignment.LEFT, Alignment.CENTER or Alignment.RIGHT
        builder.titleColor(R.color.main_background); // int res, or int colorRes parameter versions available as well.
        // builder.positiveBackground(Drawable drawable); // int res parameter version also available.
        // builder.rightToLeft(false);
        // Enables right to left positioning for languages that may require so.
        // Now we can build the dialog.
        final CustomListDialog customDialog = builder.build();
        customDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int i, String[] strings, String s) {
                // i is the position clicked.
                // strings is the array of items in the list.
                // s is the item selected.
                if (i > -1) {
                    if (mfilter_title.equalsIgnoreCase("Price")) {
                        //   DataBank.msubmissionfilter.setPrice(DataBank.filter_list_price.);
                    } else {

                    }
                }
                Tool.trace(getApplicationContext(), s);
                customDialog.dismiss();
            }
        });

        // Show the dialog.
        customDialog.show();
    }

    private void beforedialog(String title, final ArrayList<Term> terms) {
        if (title.equalsIgnoreCase("Brand")) {
            listdialog(title, Filtering.TermsAsListAlphabetical(terms), 0);
        } else
            listdialog(title, Filtering.TermsAsList(terms), 0);
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
