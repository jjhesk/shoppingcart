package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.view.View;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/11/15.
 */
public class cardbox {

    public final TextView _title;
    public final TextView content;
    public final String tag;
    public final View holder;

    public cardbox(View v, String tag) {
        _title = (TextView) v.findViewById(R.id.title);
        content = (TextView) v.findViewById(R.id.context_conten);
        _title.setText(tag);
        holder = v;
        this.tag = tag;
    }

    public void setTitle(final String title) {
        _title.setText(title);
    }

    public void setDesc(final String desc) {
        content.setText(desc);
    }

    public void setgone() {
        holder.setVisibility(View.GONE);
    }
}
