package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.hb.hkm.hypebeaststore.fragments.FilterFragment;
import com.hb.hkm.hypebeaststore.fragments.GridDisplay;
import com.hb.hkm.hypebeaststore.fragments.filtercom.MaterialTabHostSupport;
import com.hb.hkm.hypebeaststore.fragments.filtercom.SubmissionFilter;
import com.hb.hkm.hypebeaststore.fragments.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.tasks.ListQueryManager;
import com.hb.hkm.hypebeaststore.tasks.asyclient;
import com.hb.hkm.hypebeaststore.widgets.dialogcom.RunLDialogs;

import org.dmfs.android.retentionmagic.RetentionMagic;
import org.dmfs.android.retentionmagic.annotations.Parameter;
import org.dmfs.android.retentionmagic.annotations.Retain;

import github.chenupt.dragtoplayout.DragTopLayout;
import it.neokree.materialtabs.MaterialTabHost;


public class StoreFront extends ActionBarActivity implements asyclient.callback {
    private ListQueryManager sync;
    public static String TAG = "store front here";
    final GridDisplay mdisplay = new GridDisplay();

    private MaterialTabHost mTab;
    private MaterialTabHostSupport supportMtab;
    protected ProgressBar mProgressBar;
    private SwipeRefreshLayout swipcontainer;
    private DragTopLayout slidelayouttop;
    private boolean newlist = false;

    private static final String default_url = Config.newarrivals;
    public static final String EXTRA_VALUE = "com.hb.hkm.hypebeaststore.QUERYURL";

    @Retain
    @Parameter(key = EXTRA_VALUE)
    private String query_url = default_url;
    @Retain
    private Bundle mBundle;

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mdisplay.notifyList();
                if (retent.result_current_page == 1) {
                    if (newlist) {
                        newlist = false;
                        supportMtab.init();
                    }

                    //   Log.d(TAG, "request init tab");
                }
                swipcontainer.setRefreshing(false);
            }
        });
        // Tool.trace(getApplicationContext(), "added items");
    }


    @Override
    public void onFailure(String message, final int code) {
        RunLDialogs.strDemo2(StoreFront.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {
        swipcontainer.setRefreshing(true);
    }


    private void load_fetched_content(final int page_at) {
        // final Bundle instance = savedInstanceState;
        try {
            sync = new ListQueryManager(this);
            sync.setPageView(page_at)
                    .setURL(retent.queryBaseUrl)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        // initTabs();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        RetentionMagic.store(this, outState);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            RetentionMagic.persist(this, mPrefs);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RetentionMagic.persist(this, mPrefs);
        }
    }

    private SharedPreferences mPrefs;

    private void initRetentionMagic(Bundle savedInstanceState) {
        mPrefs = getSharedPreferences(getPackageName() + ".sharedPrefences", 0);
        RetentionMagic.init(this, getIntent().getExtras());
        if (savedInstanceState == null) {
            RetentionMagic.init(this, mPrefs);
        } else {
            RetentionMagic.restore(this, savedInstanceState);
        }
    }

 /*   private void getControlQuery() {
        if (getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("uri", "");
            retent.queryBaseUrl = url.equalsIgnoreCase("") ? default_url : url;
            newlist = true;
            retent.current_product_list2.clear();
            mdisplay.notifyList();
        } else {
            if (retent.queryBaseUrl == null) {
                retent.queryBaseUrl = default_url;
                Log.d(TAG, "default is taken placed.");
            }
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRetentionMagic(savedInstanceState);
        // overridePendingTransition(0, 0);


        if (getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString("uri", "");
            retent.queryBaseUrl = url.equalsIgnoreCase("") ? default_url : url;
            newlist = true;
            retent.current_product_list2.clear();
            mdisplay.notifyList();
        } else {
            if (retent.queryBaseUrl == null) {
                retent.queryBaseUrl = default_url;
                Log.d(TAG, "default is taken placed.");
            }
        }


        retent.msubmissionfilter.reset();
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_store_front);
        }

        retent.msubmissionfilter.setOnFilterApply(new SubmissionFilter.callbackfiltertrigger() {
            @Override
            public void applyfilter() {
                mdisplay.notifyList();
                load_fetched_content(1);
            }
        });
        /*if (newlist && DataBank.current_product_list2.size() > 0) {
            DataBank.current_product_list2.clear();
            mdisplay.notifyList();
        }*/


        // progress2.
        // final CircleProgressBar progress2 = (CircleProgressBar) findViewById(R.id.progressBar);
        mdisplay.setGridEvents(new GrideDisplayEvent() {
            @Override
            public void requestmoreitems(int page) {
                if (sync != null) {
                    if (sync.getStatus() == AsyncTask.Status.FINISHED) {
                        load_fetched_content(page);
                    }
                } else {
                    sync = new ListQueryManager(StoreFront.this);
                    load_fetched_content(page);
                }
            }
        });

        mTab = (MaterialTabHost) this.findViewById(R.id.materialTabHost);
        slidelayouttop = (DragTopLayout) this.findViewById(R.id.draglayout);
        swipcontainer = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        swipcontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retent.current_product_list2.clear();
                mdisplay.notifyList();
                load_fetched_content(1);
            }
        });
        mdisplay.setSwipeRefresherHandle(swipcontainer);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mdisplay)
                .commit();
        //final FilterFragment filter = FilterFragment.getInstance(Filtering.CAT_ORDER_REF, 0);
        final FilterFragment filter = new FilterFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.filter_layouts, filter)
                .commit();


        supportMtab = new MaterialTabHostSupport(mTab, filter, slidelayouttop);

        if (retent.current_product_list2.size() == 0 || newlist) {
            //if (DataBank.current_product_list.size() == 0) {
            load_fetched_content(1);
            swipcontainer.setRefreshing(true);
        } else {
            supportMtab.init();
        }

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
            if (retent.msubmissionfilter.reset()) {
                load_fetched_content(1);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //this.finish();
        // overridePendingTransition(0, 0);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
    }
}
