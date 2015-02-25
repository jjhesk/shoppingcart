package com.hb.hkm.hypebeaststore.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/24/15.
 */
public class FilterFragment extends Fragment {
    private View groupview_footer;

    public void notifyList() {
        // madapter.notifyDataSetChanged();
    }

    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View rv = inflater.inflate(R.layout.fragment_store_item_collection, container, false);
        // LayoutInflater lf = getLayoutInflater(savedInstanceState);
        // groupview_footer = inflater.inflate(R.layout.l_loading_footer, null);
        return layoutbuilder();
    }

    private View layoutbuilder() {
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setBackgroundColor(Color.CYAN);
        LL.setOrientation(LinearLayout.VERTICAL);

        ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LL.setWeightSum(6f);
        LL.setLayoutParams(LLParams);

        ImageView ladder = new ImageView(getActivity());
        ladder.setImageResource(R.drawable.ic_launcher);

        FrameLayout.LayoutParams ladderParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        ladder.setLayoutParams(ladderParams);

        FrameLayout ladderFL = new FrameLayout(getActivity());
        LinearLayout.LayoutParams ladderFLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        ladderFLParams.weight = 5f;
        ladderFL.setLayoutParams(ladderFLParams);
        ladderFL.setBackgroundColor(Color.GREEN);

        View dummyView = new View(getActivity());
        LinearLayout.LayoutParams dummyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        dummyParams.weight = 1f;
        dummyView.setLayoutParams(dummyParams);
        dummyView.setBackgroundColor(Color.RED);


        ladderFL.addView(ladder);
        LL.addView(ladderFL);
        LL.addView(dummyView);


        //RelativeLayout rl = ((RelativeLayout) getActivity().findViewById(R.id.screenRL));
        //rl.addView(LL);


        return LL;
    }
}
