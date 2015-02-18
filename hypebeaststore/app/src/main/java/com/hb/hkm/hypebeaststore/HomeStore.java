package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;

/**
 * Created by hesk on 2/11/15.
 */
public class HomeStore extends ActionBarActivity {

    private String[] theItemList = new String[]{
            "pop",
            "handle",
            "spring",
            "summer"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.ex_item, R.id.header_text, theItemList);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.expandablelistview);
        expandableLayoutListView.setHeaderDividersEnabled(true);
        expandableLayoutListView.setAdapter(arrayAdapter);



        final ExpandableLayout single_exp = (ExpandableLayout) findViewById(R.id.single_expanableview);
        //single_exp.setVerticalScrollbarPosition();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
