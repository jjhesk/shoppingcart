package com.hb.hkm.hypebeaststore.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.fragments.GridComponents.FooterComponent;
import com.hb.hkm.hypebeaststore.fragments.GridComponents.ListAdapter;
import com.hkm.ui.BounceScoller.BounceScroller;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;


/**
 * Created by hesk on 2/3/15.
 */
public class GridDisplay extends ListFragment {


    public GridDisplay() {
    }

    private JazzyGridView mGrid;
    private View groupview_footer;
    private BounceScroller scroller;
    private FooterComponent ftv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_store_item_collection, container, false);
        // LayoutInflater lf = getLayoutInflater(savedInstanceState);
        groupview_footer = inflater.inflate(R.layout.l_loading_footer, null);
        return rv;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        mGrid = (JazzyGridView) v.findViewById(android.R.id.list);
        mGrid.setAdapter(new ListAdapter(getActivity(), R.layout.l_griditem));
        mGrid.setTransitionEffect(JazzyHelper.FADE);
        mGrid.setShouldOnlyAnimateNewItems(false);

        scroller = (BounceScroller) v.findViewById(R.id.pc_root);
        ftv = new FooterComponent(scroller, groupview_footer, getActivity());

    }


}
