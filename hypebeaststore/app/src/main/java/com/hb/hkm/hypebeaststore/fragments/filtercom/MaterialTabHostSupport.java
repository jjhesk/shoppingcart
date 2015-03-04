package com.hb.hkm.hypebeaststore.fragments.filtercom;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.fragments.FilterFragment;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.ArrayList;

import github.chenupt.dragtoplayout.DragTopLayout;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by hesk on 2/26/15.
 */
public class MaterialTabHostSupport implements onApplyFilterTrigger, MaterialTabListener, DragTopLayout.PanelListener {
    public final static String
            brand_key = "Brands",
            cate_key = "Category",
            size_key = "Size",
            price_key = "Price",
            TAG = "MaterialTabHostSupport";

    private MaterialTabHost tab;
    final ArrayList<String> items_list = new ArrayList<String>();
    private boolean notify = false;
    private FilterFragment filterfragment;
    private DragTopLayout dtoplayout;

    @Override
    public void apply_action() {
        dtoplayout.closeTopView(true);
    }


    @SuppressLint("ResourceAsColor")
    public MaterialTabHostSupport(MaterialTabHost mtab, FilterFragment df, DragTopLayout dl) {
        mtab.setAccentColor(R.color.primary_pref_v2);
        //  mtab.setIconColor(resolved(R.color.primary_pref_v2));
        //  mtab.setPrimaryColor(resolved(R.color.primary_dark_material_dark));
        dl.setTouchMode(false);
        dl.listener(this);
        tab = mtab;
        filterfragment = df;
        dtoplayout = dl;
    }


    private ArrayList<TermWrap> brand = new ArrayList<TermWrap>(), cat = new ArrayList<TermWrap>(), size = new ArrayList<TermWrap>(), price = new ArrayList<TermWrap>();

    public void init() {

        if (tab.getChildCount() == 0) {
            if (retent.filter_list_brand.size() > 0) {
                items_list.add(brand_key);
                brand.addAll(retent.filter_list_brand);
            }
            if (retent.filter_list_cat.size() > 0) {
                items_list.add(cate_key);
                cat.addAll(retent.filter_list_cat);
            }
            if (retent.filter_list_size.size() > 0) {
                items_list.add(size_key);
                size.addAll(retent.filter_list_size);
            }
            if (retent.filter_list_price.size() > 0) {
                items_list.add(price_key);
                price.addAll(retent.filter_list_price);
            }

            for (int i = 0; i < items_list.size(); i++) {
                String txt = items_list.get(i);
                tab.addTab(tab.newTab().setTabListener(this).setText(txt));
                notify = true;
            }
            // Log.d(TAG, items_list.size() + " completed request for top menu");
            // Tool.trace(this, TAG + "completed request for the top menu. there are : " + items_list.size() + " items");
            if (notify) {
                tab.notifyDataSetChanged();
                notify = false;
            }
        } else {
            // mTab.removeAllViews();
            // mTab.notifyDataSetChanged();
        }

        filterfragment.setTriggerApplier(this);
    }

    public TermWrap getPriceList(int choose) {
        return price.get(choose);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //pager.setCurrentItem(tab.getPosition());
        tab_selected_final(materialTab);
        if (dtoplayout.getState() == DragTopLayout.PanelState.COLLAPSED) {
            dtoplayout.openTopView(true);
        }

    }


    @Override
    public void onTabReselected(MaterialTab materialTab) {
        //tab_selected_final(materialTab);
        if (dtoplayout.getState() == DragTopLayout.PanelState.EXPANDED) {
            dtoplayout.closeTopView(true);
        } else {
            dtoplayout.openTopView(true);
        }
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
        //  tab_selected_final(materialTab);

    }

    private void tab_selected_final(MaterialTab materialTab) {
        // Tool.trace(this, materialTab.getView().getId());
        int tabPos = materialTab.getPosition();
        tab.setSelectedNavigationItem(tabPos);
        final String t = items_list.get(tabPos);

        if (t.equalsIgnoreCase(brand_key)) {
            filterfragment.updateNewList(brand_key, brand);
        } else if (t.equalsIgnoreCase(cate_key)) {
            filterfragment.updateNewList(cate_key, cat);
        } else if (t.equalsIgnoreCase(size_key)) {
            filterfragment.updateNewList(size_key, size);
        } else if (t.equalsIgnoreCase(price_key)) {
            filterfragment.updateNewList(price_key, price);
        }
        retent.msubmissionfilter.setOperation(t);
        //Tool.trace(getApplicationContext(), "pos=" + tabPos + ". pressed title = " + title);
    }

    @Override
    public void onPanelStateChanged(DragTopLayout.PanelState panelState) {
        if (panelState == DragTopLayout.PanelState.COLLAPSED) {
            //to notify the submission filter is ready to rock;
            // clear list
            Log.d(TAG, "state changed");
        }
    }

    @Override
    public void onSliding(float ratio) {

    }

    @Override
    public void onRefresh() {

    }
}
