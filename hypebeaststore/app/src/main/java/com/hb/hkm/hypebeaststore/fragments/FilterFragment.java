package com.hb.hkm.hypebeaststore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.fragments.filtercom.MaterialTabHostSupport;
import com.hb.hkm.hypebeaststore.fragments.filtercom.filterAdapter;
import com.hb.hkm.hypebeaststore.fragments.filtercom.onApplyFilterTrigger;
import com.hb.hkm.hypebeaststore.life.retent;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;

import java.util.ArrayList;

/**
 * Created by hesk on 2/24/15.
 */
public class FilterFragment extends Fragment {
    private View groupview_footer;
    private JazzyGridView search_list;
    private filterAdapter madapter;
    private ArrayList<String> selections;
    public static final String
            ARRAYLIST = "ARRAY_VAL",
            CHOICE = "CHOICE_VAL";

/*

singleton

    public static FilterFragment getInstance(String[] val, int which) {
        FilterFragment instance = new FilterFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARRAYLIST, val);
        args.putInt(CHOICE, which);
        instance.setArguments(args);
        return instance;
    }

*/


    public FilterFragment() {

    }

    private String[] listval;
    private int m;

    public void setContent(String[] val, int which) {
        listval = val;
        m = which;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    public void updateNewList(String tag, ArrayList<TermWrap> l) {
        wordList.clear();
        wordList.addAll(l);
        madapter.changeList(tag);
    }

    public void setHeight(int resId) {
        final LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(-1, resId);
        // search_list.setLayoutParams(l);
        // search_list.requestLayout();
    }


    private final ArrayList<TermWrap> wordList = new ArrayList<>();
    private onApplyFilterTrigger trigger;

    public void setTriggerApplier(onApplyFilterTrigger e) {
        trigger = e;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        search_list = (JazzyGridView) v.findViewById(android.R.id.list);
        madapter = new filterAdapter(getActivity(), R.layout.l_item_selection, wordList);
        search_list.setAdapter(madapter);
        search_list.setTransitionEffect(JazzyHelper.SLIDE_IN);
        search_list.setShouldOnlyAnimateNewItems(false);
        // search_list.setOnScrollListener(this);
        // need some more resources to find out the problem on this line
        // search_list.setOnScrollListener(this);
        // scroller = (BounceScroller) v.findViewById(R.id.pc_root);
        // ftv = new FooterComponent(scroller, groupview_footer, getActivity());
        Button action_filter_start = (Button) v.findViewById(R.id.f3);
        action_filter_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retent.msubmissionfilter.filterapply();
                if (trigger != null) trigger.apply_action();
            }
        });

    }



    /*private View layoutbuilder() {
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


        return LL;
    }*/
}
