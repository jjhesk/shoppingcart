package com.hb.hkm.hypebeaststore.fragments.filtercom;

import android.view.View;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/11/15.
 */
public class ViewHolder {

    public final TextView text;
    // public final ImageView iv;
    public final View backdrop;


    public ViewHolder(View view) {
        // description = new cardbox((View) view.findViewById(R.id.description), "DESCRIPTION");
        //   iv = (ImageView) view.findViewById(R.id.imagevi);
        //  price_tag = (TextView) view.findViewById(R.id.price_tag);
        backdrop = view;
        text = (TextView) view.findViewById(R.id.label_field);

    }


}
