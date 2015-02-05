package com.hb.hkm.hypebeaststore.fragments.singleComponents;

import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.Image;
import com.hb.hkm.hypebeaststore.datamodel.Product;
import com.hb.hkm.hypebeaststore.fragments.dialogComponents.RunLDialogs;
import com.hb.hkm.hypebeaststore.tasks.ListResultBuilder;
import com.hb.hkm.hypebeaststore.tasks.SingleBuilder;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

import java.util.HashMap;

/**
 * Created by hesk on 2/5/15.
 */
public class ViewHolder
        implements
        BaseSliderView.OnSliderClickListener,
        asyclient.callback {
    private ProductSingle context;
    private SliderLayout sl;
    private PagerIndicator pi;
    private TextView tv_title, tv_sub_title, tv_desc, price;
    private Button add_bag, add_wish;
    private Product mproduct;
    private SingleBuilder lb;

    public ViewHolder(final ProductSingle act, final int product_array_id) {
        context = act;
        sl = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_title = (TextView) act.findViewById(R.id.title);
        tv_sub_title = (TextView) act.findViewById(R.id.subtitle);
        price = (TextView) act.findViewById(R.id.price_tag);
        tv_desc = (TextView) act.findViewById(R.id.desc);
        add_bag = (Button) act.findViewById(R.id.add_to_bag);
        add_wish = (Button) act.findViewById(R.id.add_to_wish);
        after(product_array_id);
    }

    private void after(final int pos) {
        mproduct = DataBank.product_master_list.getProduct(pos);
        final String singleurl = mproduct.getSingleEndPoint();

        tv_title.setText(mproduct.getTitle());
        add_bag.setText("Add to Bag");
        add_wish.setText("Add to Wishlist");
        price.setText(mproduct.getPrice());

        //loading point
        lb = new SingleBuilder(context, this);
        if (!singleurl.isEmpty())
            lb.setURL(singleurl).execute();

    }

    protected void setup_slider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();

        for (final Image image : mproduct.get_product_images()) {
            url_maps.put("", image.getL_url());
        }

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra", name);

            sl.addSlider(textSliderView);
        }
        sl.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sl.setCustomAnimation(new DescriptionAnimation());
        sl.setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }

    @Override
    public void onSuccess(String data) {
        RunLDialogs.strDemo2(context, data);
        setup_slider();
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(context, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }
}
