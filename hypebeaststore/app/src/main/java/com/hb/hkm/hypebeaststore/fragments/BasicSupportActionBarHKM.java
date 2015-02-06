package com.hb.hkm.hypebeaststore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by hesk on 2/6/15.
 */
public class BasicSupportActionBarHKM extends ActionBarActivity {
    protected ActionBar action;
    private final static String TAG = "BASIC_SUPPORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        action = getSupportActionBar();
      /*  action.setHomeButtonEnabled(true);
        action.setDisplayHomeAsUpEnabled(true);*/
        //  action.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        // action.setDisplayShowCustomEnabled(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, action.toString());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //
                // NavUtils.navigateUpFromSameTask(this);
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
