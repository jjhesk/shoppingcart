package com.hb.hkm.hypebeaststore.fragments.singleComponents;

import android.content.Intent;
import android.os.Bundle;
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
import com.hb.hkm.hypebeaststore.SelectView;
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
    private TextView tv_brand_title, tv_product_title, price;
    private Button add_bag, add_wish, sp_brand, sp_size, sp_cat, sp_qty;
    private Product mproduct_from_list, product_single;
    private SingleBuilder lb;
    private AddCartManager acm;
    private cardbox card_description, card_size;

    public ViewHolder(final ProductSingle act, final int product_array_id) {
        context = act;
        sl = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_brand_title = (TextView) act.findViewById(R.id.brand_title);
        tv_product_title = (TextView) act.findViewById(R.id.product_title);
        price = (TextView) act.findViewById(R.id.price_tag);
        sp_brand = (Button) act.findViewById(R.id.spinner_brand);
        sp_brand.setOnClickListener(this);
        sp_size = (Button) act.findViewById(R.id.spinner_size);
        sp_size.setOnClickListener(this);
        sp_cat = (Button) act.findViewById(R.id.spinner_cat);
        sp_cat.setOnClickListener(this);
        sp_qty = (Button) act.findViewById(R.id.spinner_quantity);
        sp_qty.setOnClickListener(this);
        card_description = new cardbox(act.findViewById(R.id.description), "DESCRIPTION");
        card_description = new cardbox(act.findViewById(R.id.description), "DESCRIPTION");

        add_bag = (Button) act.findViewById(R.id.add_to_bag);
        add_wish = (Button) act.findViewById(R.id.add_to_wish);
        if (product_array_id == -1) {
            RunLDialogs.strDemo2(context, "position id not found");
        } else
            after(product_array_id);
    }

    private void after(final int pos) {
        try {
            mproduct_from_list = DataBank.current_product_list.get(pos);
            final String singleurl = mproduct_from_list.getSingleEndPoint();
            tv_brand_title.setText(mproduct_from_list.get_brand_name());
            tv_product_title.setText(mproduct_from_list.getTitle());
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

            //   tv_desc.setText(product_single.get_desc());
            card_description.setDesc(product_single.get_desc());

            if (product_single.get_product_images() == null) throw new Exception("no images found");
            for (Image image : product_single.get_product_images()) {
                url_maps.put("sample #" + image.getPosition(), image.getL_url());
            }

            for (Map.Entry<String, String> entry : url_maps.entrySet()) {
                TextSliderView textSliderView = new TextSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        // .description(entry.getKey())
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

    static class cardbox {
        TextView _title;
        TextView content;
        String tag;

        public cardbox(View v, String tag) {
            _title = (TextView) v.findViewById(R.id.title);
            content = (TextView) v.findViewById(R.id.context_conten);
            _title.setText(tag);
            this.tag = tag;
        }

        public void setTitle(final String title) {
            _title.setText(title);
        }

        public void setDesc(final String desc) {
            content.setText(desc);
        }
    }

    private void start_intent(final int action_type) {
        Intent it = new Intent(context, SelectView.class);
        Bundle f = new Bundle();
        f.putInt(SelectView.selection_view, action_type);
        it.putExtras(f);
        context.startActivityForResult(it, SelectView.ACTION_ON_SELECT);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinner_size:
                start_intent(0);
                break;
            case R.id.spinner_brand:
                start_intent(0);
                break;
            case R.id.spinner_cat:
                start_intent(0);
                break;
            case R.id.spinner_quantity:
                start_intent(0);
                break;
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
