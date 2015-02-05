package com.hb.hkm.hypebeaststore.fragments.GridComponents;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by hesk on 2/3/15.
 */


public class ListAdapter extends ArrayAdapter<Product> {

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayout;
    private final Picasso pica = Picasso.with(getContext());
    public static final String TAG = "product list adapter";

    public ListAdapter(Context context, int itemLayout) {
        super(context, itemLayout, R.id.description, DataBank.product_master_list.getProducts());
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayout = itemLayout;

    }

    private void feedData(final ViewHolder vh, final int position) {
        final Product p = DataBank.product_master_list.getProduct(position);
        final String test = p.get_cover_image();

        vh.text.setText(p.getTitle());
        vh.price_tag.setText(p.getPrice() + "USD");

        //   int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
        //   holder.text.setBackgroundColor(res.getColor(colorResId));
        try {
            Log.d(TAG, test);
            pica
                    .load(p.get_cover_image())
                    //.load("http://store.hypebeast.com/bundles/hypebeasteditorial/images/hypebeast-logo.png")
                    .fit()
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.bike)
                    .into(vh.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(itemLayout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        feedData(holder, position);
        return convertView;
    }

    static class ViewHolder {
        final TextView text;
        final TextView price_tag;
        final ImageView iv;

        ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.description);
            iv = (ImageView) view.findViewById(R.id.imagevi);
            price_tag = (TextView) view.findViewById(R.id.price_tag);
        }
    }
}