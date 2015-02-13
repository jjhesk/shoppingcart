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
    private TextView tv_brand_title, tv_product_title, price, sales;
    private Button add_bag, add_wish, sp_color, sp_size, sp_cat, sp_qty;
    private Product mproduct_from_list, product_single;
    private SingleBuilder lb;
    private AddCartManager acm;
    private cardbox card_description, card_size, card_hard;
    private SelectionSpinner SPinner;

    public SelectionSpinner getOptionUIs() {
        return SPinner;
    }

    public ViewHolder(final ProductSingle act, final int product_array_id) {
        context = act;
        sl = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_brand_title = (TextView) act.findViewById(R.id.brand_title);
        tv_product_title = (TextView) act.findViewById(R.id.product_title);
        price = (TextView) act.findViewById(R.id.price_tag);
        sales = (TextView) act.findViewById(R.id.sale_price_tag);

        SPinner = new SelectionSpinner(act);


        card_description = new cardbox(act.findViewById(R.id.description), "DESCRIPTION");
        card_size = new cardbox(act.findViewById(R.id.size), "SIZE");
        card_hard = new cardbox(act.findViewById(R.id.hardcode), "TERMS");


        if (!SPinner.foundSize()) card_size.setgone();
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

    protected void setup_selection_UI() {

    }

    protected void setup_textviews() {
        String simple = !product_single.hasVariants() ? product_single.getMasterVariant().stock_logic() : "";

        card_description.setDesc(product_single.get_desc());
        card_size.setDesc("iaonvoinaoivneionv ev ");
        card_hard.setDesc("fiaojfdsoi jfiosdj ");




        if (!product_single.getSalesPrice().equalsIgnoreCase("")) {
            price.setText(product_single.getPrice() + simple);
            sales.setText(product_single.getSalesPrice());
        } else {

            price.setText(product_single.getPrice() + simple);
            sales.setText("");
        }


    }

    protected void setup_slider() {
        try {
            final Map<String, String> url_maps = new HashMap<String, String>();

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
                //RunLDialogs.strDemo2(context, data);
                product_single = DataBank.product_single;
                setup_slider();
                setup_selection_UI();
                setup_textviews();
                break;
            case ADDCART:
                //RunLDialogs.strDemo2(context, data);

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
