package com.hb.hkm.hkmeexpandedview.list;

import android.content.Context;

import com.hb.hkm.hkmeexpandedview.R;
import com.hb.hkm.hkmeexpandedview.bindingholder.SlickHolder;
import com.hb.hkm.hkmeexpandedview.bindingholder.basicViewHolder;
import com.hb.hkm.hkmeexpandedview.databindingmodel.BasicDataBind;
import com.hb.hkm.hkmeexpandedview.databindingmodel.SlickBind;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by hesk on 2/27/2015.
 */
public class SlickListAdapter extends BasicListingAdapter {
    final Picasso theloadingimagepicasso = Picasso.with(getContext());

    public SlickListAdapter(Context context, int itemLayouti, ArrayList<BasicDataBind> listsrc) {
        super(context, R.layout.l_icon_text_c, listsrc);
    }

    @Override
    protected void trigger_press(BasicDataBind bb) {
        final SlickBind sb = (SlickBind) bb;

    }

    @Override
    protected void feedData(final basicViewHolder vh, final int position) {
        super.feedData(vh, position);
        try {
            final SlickBind p = (SlickBind) getItem(position);
            final SlickHolder sh = (SlickHolder) vh;
            theloadingimagepicasso
                    .load(p.get_url_img())
                    .fit().centerCrop()
                    .placeholder(R.drawable.load)
                    .error(R.drawable.load)
                    .into(sh.imageview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
