package com.hb.hkm.hypebeaststore.fragments.gridcom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.datamodel.V2.Product;
import com.squareup.picasso.Picasso;

/**
 * Created by hesk on 2/3/15.
 */


public class HBResultAdapter extends ArrayAdapter<Product> {

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayout;
    private final Picasso pica = Picasso.with(getContext());
    public static final String TAG = "product list adapter";

    public HBResultAdapter(Context context, int itemLayout) {
        super(context, itemLayout, R.id.description, retent.current_product_list2);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        this.itemLayout = itemLayout;
    }

    private void feedData(final ViewHolder vh, final int position) {
        // final Product p = DataBank.current_product_list.get(position);
        final Product p = getItem(position);
        //final String test = p.get_cover_image();

        // vh.description.title.setText(p.getTitle());
        vh.text.setText(p.get_brand_name());
        vh.price_tag.setText(p.getPrice());

        //   int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
        //   holder.text.setBackgroundColor(res.getColor(colorResId));
        try {
            // Log.d(TAG, test);
            pica
                    .load(p.get_cover_image())
                            //.load("http://store.hypebeast.com/bundles/hypebeasteditorial/images/hypebeast-logo.png")
                            //.resizeDimen(R.dimen.dialog_title, R.dimen.dialog_title)
                    .fit().centerInside()
                    .placeholder(R.drawable.hbstoreicon)
                    .error(R.drawable.hbstoreicon)
                    .into(vh.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vh.backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getContext(), ProductSingle.class);
                i.putExtra(Config.single_id, position);
                getContext().startActivity(i);
            }
        });


    }

  /*  @Override
    public int getCount() {
        return DataBank.current_product_list.size();
    }*/

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


}