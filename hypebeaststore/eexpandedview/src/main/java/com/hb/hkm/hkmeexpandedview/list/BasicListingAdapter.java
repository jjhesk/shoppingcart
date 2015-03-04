package com.hb.hkm.hkmeexpandedview.list;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hb.hkm.hkmeexpandedview.bindingholder.basicViewHolder;
import com.hb.hkm.hkmeexpandedview.databindingmodel.BasicDataBind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 2/25/15.
 */
public class BasicListingAdapter extends ArrayAdapter<BasicDataBind> {

    protected final LayoutInflater inflater;
    protected final Resources res;
    protected final int itemLayout;
    // private final Picasso pica = Picasso.with(getContext());
    public static final String TAG = "product list adapter";

    public BasicListingAdapter(Context context, int itemLayouti, List<BasicDataBind> listsrc) {
        super(context, itemLayouti, itemLayouti, listsrc);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        itemLayout = itemLayouti;
    }

    protected void feedData(final basicViewHolder vh, final int position) {
        final BasicDataBind p = getItem(position);
        vh.text.setText(p.label());
        vh.backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  final Intent i = new Intent(getContext(), ProductSingle.class);
                i.putExtra(Config.single_id, position);
                getContext().startActivity(i);*/
                trigger_press(p);
            }
        });

    }

    protected void trigger_press(BasicDataBind basicb) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        basicViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(itemLayout, null);
            holder = new basicViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (basicViewHolder) convertView.getTag();
        }
        feedData(holder, position);
        return convertView;
    }


}