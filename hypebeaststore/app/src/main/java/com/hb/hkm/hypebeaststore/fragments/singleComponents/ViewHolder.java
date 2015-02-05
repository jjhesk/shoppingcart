package com.hb.hkm.hypebeaststore.fragments.singleComponents;

import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.Product;

/**
 * Created by hesk on 2/5/15.
 */
public class ViewHolder {
    private ProductSingle context;
    private SliderLayout sl;
    private PagerIndicator pi;
    private TextView tv_title, tv_sub_title, tv_desc;
    private Button add_bag, add_wish;
    private Product mproduct;

    public ViewHolder(final ProductSingle act, final int product_array_id) {
        context = act;
        sl = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_title = (TextView) act.findViewById(R.id.title);
        tv_sub_title = (TextView) act.findViewById(R.id.subtitle);
        tv_desc = (TextView) act.findViewById(R.id.desc);
        add_bag = (Button) act.findViewById(R.id.add_to_bag);
        add_wish = (Button) act.findViewById(R.id.add_to_wish);
        after(product_array_id);
    }

    private void after(final int pos) {
        mproduct = DataBank.product_master_list.getProduct(pos);
        tv_title.setText(mproduct.getTitle());
        //tv_sub_title.setText(mproduct.get());
    }
}
