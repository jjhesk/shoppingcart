package com.hb.hkm.hypebeaststore.fragments.singleComponents;

import android.util.Log;
import android.view.View;
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
import com.hb.hkm.hypebeaststore.tasks.AddCartManager;
import com.hb.hkm.hypebeaststore.tasks.SingleBuilder;
import com.hb.hkm.hypebeaststore.tasks.asyclient;

import java.util.HashMap;
import java.util.Map;

import static com.hb.hkm.hypebeaststore.fragments.singleComponents.ViewHolder.RequestTag.ADDCART;
import static com.hb.hkm.hypebeaststore.fragments.singleComponents.ViewHolder.RequestTag.READSINGLE;

/**
 * Created by hesk on 2/5/15.
 */
public class ViewHolder
        implements
        BaseSliderView.OnSliderClickListener,
        asyclient.callback,
        View.OnClickListener {
    private static String TAG = "viewholder vh";
    private RequestTag request_tag;

    enum RequestTag {
        READSINGLE, ADDCART
    }

    private ProductSingle context;
    private SliderLayout sl;
    private PagerIndicator pi;
    private TextView tv_title, tv_sub_title, tv_desc, price;
    private Button add_bag, add_wish;
    private Product mproduct_from_list, product_single;
    private SingleBuilder lb;
    private AddCartManager acm;

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
        if (product_array_id == -1) {
            RunLDialogs.strDemo2(context, "position id not found");
        } else
            after(product_array_id);
    }

    private void after(final int pos) {
        try {
            mproduct_from_list = DataBank.product_master_list.getProduct(pos);
            final String singleurl = mproduct_from_list.getSingleEndPoint();

            tv_title.setText(mproduct_from_list.getTitle());
            add_bag.setText("Add to Bag");
            add_wish.setText("Add to Wishlist");
            price.setText(mproduct_from_list.getPrice());

            //loading point
            request_tag = READSINGLE;
            lb = new SingleBuilder(context, this);
            if (!singleurl.isEmpty())
                lb.setURL(singleurl).execute();
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        }
    }

    protected void setup_slider() {
        try {
            final Map<String, String> url_maps = new HashMap<String, String>();
            product_single = DataBank.product_single;

            tv_desc.setText(product_single.get_desc());


            if (product_single.get_product_images() == null) throw new Exception("no images found");
            for (Image image : product_single.get_product_images()) {
                url_maps.put("sample #" + image.getPosition(), image.getL_url());
            }

            for (Map.Entry<String, String> entry : url_maps.entrySet()) {
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        .description(entry.getKey())
                        .image(entry.getValue())
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.getBundle()
                        .putString("extra", entry.getKey());

                sl.addSlider(textSliderView);
            }
            sl.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
            sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sl.setCustomAnimation(new DescriptionAnimation());
            sl.setDuration(4000);

            add_bag.setOnClickListener(this);
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        }
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        // baseSliderView.getView().getNextFocusDownId()
        Log.d(TAG, baseSliderView.toString());

    }

    @Override
    public void onSuccess(String data) {
        switch (request_tag) {
            case READSINGLE:
                RunLDialogs.strDemo2(context, data);
                setup_slider();

                break;
            case ADDCART:
                RunLDialogs.strDemo2(context, data);

                break;
        }
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(context, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_bag:
                request_tag = ADDCART;
                acm = new AddCartManager(context, this);
                final int h = product_single.hasVariants() ? product_single.getVariantID(0) : product_single.getMasterID();
                //if (!singleurl.isEmpty())
                acm
                        .setURL(AddCartManager.getUrl(h, 1))
                        .execute();

                break;
            case R.id.add_to_wish:

                break;
        }
    }
}
