package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.SelectView;
import com.hb.hkm.hypebeaststore.datamodel.V2.Variant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hesk on 2/11/15.
 */
public class SelectionSpinner implements View.OnClickListener {
    private ProductSingle mcontext;
    private int chk_color = 0, chk_size = 0, chk_qty = 0;
    private Button add_bag, add_wish, sp_variants, sp_size, sp_qty;
    private String[] color, size, qty;

    enum supporttype {
        VARIANT, SIZE, QTY
    }

    public SelectionSpinner(final ProductSingle act) {
        mcontext = act;
        sp_variants = (Button) act.findViewById(R.id.spinner_color);
        sp_variants.setOnClickListener(this);
        sp_size = (Button) act.findViewById(R.id.spinner_size);
        sp_size.setOnClickListener(this);
        sp_qty = (Button) act.findViewById(R.id.spinner_quantity);
        sp_qty.setOnClickListener(this);

    }

    public SelectionSpinner addVariance(ArrayList<Variant> list) {
        final ArrayList<String> colr = new ArrayList<>();
        final ArrayList<String> sie = new ArrayList<>();
        for (Variant t : list) {
            final String r = t.getMetaValueFromType() + t.stock_logic_configuration();
            if (t.getType().equalsIgnoreCase("color")) {
                colr.add(r);
            } else if (t.getType().equalsIgnoreCase("size")) {
                sie.add(r);
            }
        }
        if (colr.size() > 0)
            color = colr.toArray(new String[colr.size()]);
        if (sie.size() > 0)
            size = sie.toArray(new String[sie.size()]);
        return this;
    }

    public void init() {
        qty = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };
        if (color == null) {
            sp_variants.setVisibility(View.GONE);
            chk_color = -1;
        } else {
            chk_color = 1;
        }
        if (size == null) {
            sp_size.setVisibility(View.GONE);
            chk_size = -1;
        } else {
            chk_size = 1;
        }

        if (color == null && size == null) {
            setLayOutWeight(6.0f, sp_qty);
        }

        if (color != null && size == null) {
            setLayOutWeight(3.0f, sp_size);
            setLayOutWeight(3.0f, sp_qty);
        } else {
            setLayOutWeight(3.4f, sp_variants);
            setLayOutWeight(2.5f, sp_qty);
        }
        setQty("1");
    }

    public boolean foundSize() {
        return chk_size != -1;
    }

    /**
     * to get the current number of item that selected.
     *
     * @return
     */
    public int getCurrentQuantity() {
        return Integer.parseInt(qty[chk_qty]);
    }

    private void setLayOutWeight(final float fl, Button btt) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                fl);
        btt.setLayoutParams(params);
        btt.invalidate();
    }

    private void start_intent(final int action_type, String[] stringlist) {
        final Intent it = new Intent(mcontext, SelectView.class);
        final Bundle f = new Bundle();
        f.putInt(SelectView.selection_view, action_type);
        if (stringlist != null) {
            if (stringlist.length > 0) {
                f.putStringArray(SelectView.arraystring, stringlist);
            }
        }

        it.putExtras(f);
        mcontext.startActivityForResult(it, SelectView.ACTION_ON_SELECT);
    }

    public SelectionSpinner setSize(String t) {
        sp_size.setText("Size: " + t);
        return this;
    }

    public SelectionSpinner setColor(String t) {
        sp_variants.setText("Color: " + t);
        return this;
    }

    public SelectionSpinner setQty(String t) {
        sp_qty.setText("Qty: " + t);
        return this;
    }

    public void setEnabled(boolean b) {
        sp_variants.setEnabled(b);
        sp_qty.setEnabled(b);
        sp_size.setEnabled(b);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinner_color:
                if (color != null)
                    start_intent(SelectView.COLOR, color);
                break;

            case R.id.spinner_size:
                if (size != null)
                    start_intent(SelectView.SIZE, size);
                break;

            case R.id.spinner_quantity:
                if (qty != null)
                    start_intent(SelectView.QTY, qty);
                break;
        }
    }
}
