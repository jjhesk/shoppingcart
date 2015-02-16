package com.hb.hkm.hypebeaststore;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by hesk on 2/9/15.
 */
public class SelectView extends ListActivity implements AdapterView.OnItemClickListener {
    public final static int SIZE = 0;
    public final static int COLOR = 1;
    public final static int QTY = 2;
    public final static String selection_view = "choice";
    public final static String TAG = "selection_view_as_tag";
    public final static int ACTION_ON_SELECT = 9921;
    public final static String pos = "POSITION";
    public final static String kind = "KIND";
    public final static String arraystring = "ARRAY";
    public final static String selection_val = "VAL";
    private String[] loading_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.act_selection_list);
        loading_list = getIntent().getExtras().getStringArray(SelectView.arraystring);
        final TextView tv = (TextView) findViewById(R.id.title_top);

        String tag_txt = "";
        switch (getIntent().getExtras().getInt(SelectView.selection_view, 0)) {
            case SIZE:
                tag_txt = "Size";
                break;
            case COLOR:
                tag_txt = "Product Variants";
                break;
            case QTY:
                tag_txt = "Quantity";
                break;
        }

        tv.setText(tag_txt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, loading_list);

        final ListView t = (ListView) findViewById(android.R.id.list);
        t.setBackgroundColor(getResources().getColor(R.color.common_background));

        // Bind to our new adapter.
        setListAdapter(adapter);
        t.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, position + "");

        filter_configuration_change(position);
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putInt(pos, position);
        b.putInt(kind, getIntent().getExtras().getInt(SelectView.selection_view, 0));
        b.putString(selection_val, loading_list[position]);
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
        finish();
    }

    protected void filter_configuration_change(int position) {
        final String val = loading_list[position];
        switch (getIntent().getExtras().getInt(SelectView.selection_view, 0)) {
            case SIZE:
                // DataBank.msubmissionfilter.setSize(val);
                break;
            case COLOR:
                // DataBank.msubmissionfilter.setCate(val);
                break;
            case QTY:
                //tag_txt = "Quantity";
                break;
        }
    }
}
