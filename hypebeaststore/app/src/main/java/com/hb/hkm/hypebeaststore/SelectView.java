package com.hb.hkm.hypebeaststore;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.Term;

import java.util.ArrayList;

/**
 * Created by hesk on 2/9/15.
 */
public class SelectView extends ListActivity implements AdapterView.OnItemClickListener {
    public final static int SIZE = 0;
    public final static int CAT = 1;
    public final static int BRAND = 2;
    public final static String selection_view = "choice";
    public final static String TAG = "this tag";
    public final static int ACTION_ON_SELECT = 9929121;
    public final static String pos = "POSITION";
    public final static String kind = "KIND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.act_selection_list);
        ArrayList<Term> load = new ArrayList<Term>();
        switch (getIntent().getExtras().getInt(SelectView.selection_view, 0)) {
            case SIZE:
                load = DataBank.filter_list_size;
                break;
            case CAT:
                load = DataBank.filter_list_cat;
                break;
            case BRAND:
                load = DataBank.filter_list_size;
                break;
        }
        ArrayAdapter<Term> adapter = new ArrayAdapter<Term>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, load);
        final ListView t = (ListView) findViewById(android.R.id.list);
        // Bind to our new adapter.
        setListAdapter(adapter);
        t.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, position + "");

        Intent intent = new Intent();
        intent.putExtra(pos, position);
        intent.putExtra(kind, getIntent().getExtras().getInt(SelectView.selection_view, 0));
        setResult(ACTION_ON_SELECT, intent);
    }
}
