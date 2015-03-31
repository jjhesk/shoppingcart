package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.content.Context;
import android.util.Log;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.List;

/**
 * Created by hesk on 30/3/15.
 */
public class SorterAdapterNameBrand extends StickyGridHeadersSimpleArrayAdapter {

    private SortSimpleAdapterData k;

    public SorterAdapterNameBrand(Context context, List<? extends itemDisplay> items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        k = new SortSimpleAdapterData();
    }

    //# http://unicode-table.com/en/#0023
    @Override
    protected long processHeaderId(char firstChar, long character_index_order) {
        long f = k.isSharpGroup(firstChar) ? 23L : character_index_order;
        Log.d("checker", f + ":" + firstChar + ":" + (k.isSharpGroup(firstChar) ? "# groupped" : "not #"));
        return f;
    }
}
