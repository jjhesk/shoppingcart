package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.dd.processbutton.iml.ActionProcessButton;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V2.Image;
import com.hb.hkm.hypebeaststore.datamodel.V2.Product;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.tasks.AddCartManager;
import com.hb.hkm.hypebeaststore.tasks.SingleBuilder;
import com.hb.hkm.hypebeaststore.tasks.asyclient;
import com.hb.hkm.hypebeaststore.widgets.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.zoomimage;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;

import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.ADDCART;
import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.ADDTOWISH;
import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.READSINGLE;

/**
 * Single View Holder to Display A SINGLE PRODUCT INFOMATION
 * Created by hesk on 2/5/15.
 */
public class SingleProductApplication
        implements
        BaseSliderView.OnSliderClickListener,
        asyclient.callback,
        View.OnClickListener {
    private static String TAG = "viewholder vh";
    private RequestTag request_tag;
    private int current_variant_selected = 0;

    enum RequestTag {
        READSINGLE, ADDCART, ADDTOWISH
    }

    private ProgressDialog pd;
    private ProductSingle context;
    private SliderLayout sl;
    private PagerIndicator pi;
    private TextView tv_brand_title, tv_product_title, price, sales;
    private ActionProcessButton add_bag, add_wish, sp_color, sp_size, sp_cat, sp_qty;
    private Product product;
    private SingleBuilder lb;
    private AddCartManager acm;
    private cardbox card_description, card_size, card_hard;
    private SelectionSpinner SPinner;
    private SwipeRefreshLayout swipcontainer;

    public SelectionSpinner getOptionUIs() {
        return SPinner;
    }

    public SingleProductApplication(final ProductSingle act, final int product_array_id) {
        context = act;
        sl = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_brand_title = (TextView) act.findViewById(R.id.brand_title);
        tv_product_title = (TextView) act.findViewById(R.id.product_title);
        price = (TextView) act.findViewById(R.id.price_tag);
        sales = (TextView) act.findViewById(R.id.sale_price_tag);

        SPinner = new SelectionSpinner(act);

        card_description = new cardbox(act.findViewById(R.id.description), act.getString(R.string.label_field_1));
        card_size = new cardbox(act.findViewById(R.id.size), act.getString(R.string.label_field_2));
        card_hard = new cardbox(act.findViewById(R.id.hardcode), act.getString(R.string.label_field_3));


        add_bag = (ActionProcessButton) act.findViewById(R.id.add_to_bag);
        add_wish = (ActionProcessButton) act.findViewById(R.id.add_to_wish);

        /*swipcontainer = (SwipeRefreshLayout) act.findViewById(R.id.swiphandlesingle);
        swipcontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        swipcontainer.setEnabled(false);*/


        if (product_array_id == -1) {
            RunLDialogs.strDemo2(context, "position id not found");
        } else
            after(product_array_id);
    }

    /**
     * start making the url request
     *
     * @param pos
     */
    private void after(final int pos) {
        try {
            final Product product_from_list = retent.current_product_list2.get(pos);
            final String singleurl = product_from_list.getSingleEndPoint();
            tv_brand_title.setText(product_from_list.get_brand_name());
            tv_product_title.setText(product_from_list.getTitle());
            add_bag.setText(context.getString(R.string.button_1));
            add_wish.setText(context.getString(R.string.button_2));
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

    /**
     * when the UI data is retrieved for list of selections like size, cate, and numbers
     */
    protected void setup_selection_UI() {
        if (!SPinner.foundSize()) card_size.setgone();
        if (product.hasVariance())
            try {
                SPinner.addVariance(product.getMappedVariants()).init();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    protected void setup_textviews() {
        //String simple = !act_product.hasVariants() ? act_product.getMasterVariant().stock_logic() : "";
        card_description.setDesc(product.get_desc());
        card_size.setDesc("product size and all other items goes here.");
        card_hard.setDesc("terms and conditions goes here");
        if (!product.getSalesPrice().equalsIgnoreCase("")) {
            price.setText(product.getPrice());
            sales.setText(product.getSalesPrice());
        } else {
            price.setText(product.getPrice());
            sales.setText("");
        }
    }

    protected void setup_slider() {
        try {
            final Map<String, String> url_maps = new HashMap<String, String>();
            if (product.get_product_images() == null) throw new Exception("no images found");
            for (Image image : product.get_product_images()) {
                url_maps.put(image.originalImage(), image.largeImage());
            }

            for (Map.Entry<String, String> entry : url_maps.entrySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        // .description(entry.getKey())
                        .image(entry.getValue())
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.getBundle()
                        .putString("full", entry.getKey());

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

    /**
     * start activity to zoom image view
     *
     * @param baseSliderView
     */
    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        // baseSliderView.getView().getNextFocusDownId()
        Log.d(TAG, baseSliderView.toString());
        final Intent i = new Intent(context, zoomimage.class);
        i.putExtra(zoomimage.URLKEY, baseSliderView.getBundle().getString("full"));
        context.startActivity(i);
    }

    @Override
    public void onSuccess(String data) {
        switch (request_tag) {
            case READSINGLE:
                /**
                 * after loaded the json data from request url and now render the ui based on the retrieved data.
                 */
                product = retent.product_single2;
                setup_slider();
                setup_selection_UI();
                setup_textviews();
                pd.dismiss();
                add_bag.setEnabled(true);
                SPinner.setEnabled(true);
                break;
            case ADDCART:
                //RunLDialogs.strDemo2(context, data);
                add_bag.setProgress(1);
                add_bag.setEnabled(true);
                SPinner.setEnabled(true);
                break;
        }
    }

    @Override
    public void onFailure(String message, int code) {
        final MaterialDialog materialdialognow = new MaterialDialog(context);
        materialdialognow.setTitle("Error Found");
        materialdialognow.setMessage(message);
        materialdialognow.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();
                context.actionAsUp();
            }
        });
        materialdialognow.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();

            }
        });
        materialdialognow.show();

    }

    @Override
    public void beforeStart(asyclient task) {
        switch (request_tag) {
            case ADDCART:
                //RunLDialogs.strDemo2(context, data);
                add_bag.setMode(ActionProcessButton.Mode.ENDLESS);
                add_bag.setEnabled(false);
                break;
            case READSINGLE:
                pd = new ProgressDialog(context);
                //    pd.setIndeterminateDrawable();
                pd = ProgressDialog.show(context, null, "One moment", true, false, null);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * adding this product to the bag
             */
            case R.id.add_to_bag:
                try {
                    request_tag = ADDCART;
                    acm = new AddCartManager(context, this);
                    acm.setItem(product.getVariantID(current_variant_selected), SPinner.getCurrentQuantity());
                    acm.execute();
                    SPinner.setEnabled(false);
                } catch (Exception e) {
                }

                break;

            /**
             * adding this product into the wish list
             */
            case R.id.add_to_wish:
                request_tag = ADDTOWISH;
                break;
        }
    }
}
