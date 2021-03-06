package com.hb.hkm.hypebeaststore.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.fragments.gridcom.FooterComponent;
import com.hb.hkm.hypebeaststore.fragments.gridcom.GrideDisplayEvent;
import com.hb.hkm.hypebeaststore.fragments.gridcom.HBResultAdapter;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hkm.ui.BounceScoller.BounceScroller;
import com.melnykov.fab.FloatingActionButton;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;


/**
 * Created by hesk on 2/3/15.
 */
public class GridDisplay extends ListFragment implements AbsListView.OnScrollListener {
    private static String TAG = "GridDisplay";

    public GridDisplay() {
    }

    private GrideDisplayEvent event;
    private JazzyGridView mGrid;
    private View groupview_footer;
    private BounceScroller scroller;
    private FooterComponent ftv;
    private HBResultAdapter madapter;
    private FloatingActionButton mfab;
    private boolean floating_bag = true;

    public void setGridEvents(GrideDisplayEvent m_event) {
        event = m_event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_store_item_collection, container, false);
        // LayoutInflater lf = getLayoutInflater(savedInstanceState);
        groupview_footer = inflater.inflate(R.layout.l_loading_footer, null);
        return rv;
    }

    public void notifyList() {
        if (madapter != null)
            madapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        mfab = (FloatingActionButton) v.findViewById(R.id.fab);
        mGrid = (JazzyGridView) v.findViewById(android.R.id.list);
        madapter = new HBResultAdapter(getActivity(), R.layout.l_griditem);
        mGrid.setAdapter(madapter);
        mGrid.setTransitionEffect(JazzyHelper.FADE);
        mGrid.setShouldOnlyAnimateNewItems(false);
        mGrid.setOnScrollListener(this);

        if (floating_bag)
            mfab.attachToListView(mGrid, null, this);
        //need some more resources to find out the problem on this line

        // mGrid.setOnScrollListener(this);
        //     scroller = (BounceScroller) v.findViewById(R.id.pc_root);
        //     ftv = new FooterComponent(scroller, groupview_footer, getActivity());
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {

        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

        }
    }

    private static int target_total = 1000;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        double b = (double) firstVisibleItem / (double) totalItemCount;
        //  Log.d(TAG, firstVisibleItem + "/" + totalItemCount + " : " + b + "");
        double k = Config.setting.list_expand_factor + (double) totalItemCount / (double) target_total * (100 - Config.setting.list_expand_factor) / 100d;
        // Log.d(TAG, k + " : " + b + "");
        if (b > k) {
            // Log.d(TAG, "trigger run request. ");
            if (retent.result_total_pages > retent.result_current_page) {
                if (event != null) event.requestmoreitems(retent.result_current_page + 1);
            }
        }
        if (swipeContainer != null) {
            swipeContainer.setEnabled(firstVisibleItem == 0 );
        }
    }

    private SwipeRefreshLayout swipeContainer;
    public void setSwipeRefresherHandle(SwipeRefreshLayout swipe) {
        swipeContainer = swipe;
    }
}
