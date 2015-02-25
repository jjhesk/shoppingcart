package com.hb.hkm.hkmeexpandedview.list;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by hesk on 2/25/15.
 */

/**
 * Created by hesk on 2/3/15.
 */


public class KRAdapter extends ArrayAdapter<DataBind> {

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayout;
    // private final Picasso pica = Picasso.with(getContext());
    public static final String TAG = "product list adapter";

    public KRAdapter(Context context, int itemLayouti, ArrayList<DataBind> listsrc) {
        super(context, itemLayouti, itemLayouti, listsrc);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        itemLayout = itemLayouti;
    }

    private void feedData(final ViewHolder vh, final int position) {
        // final Product p = DataBank.current_product_list.get(position);
        final DataBind p = (DataBind) getItem(position);
        // vh.description.title.setText(p.getTitle());
        vh.text.setText(p.label());
        //vh.price_tag.setText(p.getPrice());
        /*try {

            pica
                    .load(p.get_cover_image())
                    .fit()
                    .placeholder(R.drawable.hbstoreicon)
                    .error(R.drawable.bike)
                    .into(vh.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        vh.backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  final Intent i = new Intent(getContext(), ProductSingle.class);
                i.putExtra(Config.single_id, position);
                getContext().startActivity(i);*/
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