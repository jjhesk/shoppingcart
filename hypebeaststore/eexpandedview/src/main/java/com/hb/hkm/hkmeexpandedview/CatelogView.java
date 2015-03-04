package com.hb.hkm.hkmeexpandedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.hb.hkm.hkmeexpandedview.bindingholder.SlickHolder;
import com.hb.hkm.hkmeexpandedview.databindingmodel.BasicDataBind;
import com.hb.hkm.hkmeexpandedview.databindingmodel.SlickBind;
import com.hb.hkm.hkmeexpandedview.list.BasicListingAdapter;
import com.squareup.picasso.Picasso;

import static com.hb.hkm.hkmeexpandedview.R.styleable;

/**
 * Created by hesk on 2/24/15.
 */
public class CatelogView extends LinearLayout implements View.OnClickListener, SpringListener {
    private static String TAG = ".CatelogView";
    // Create a system to run the physics loop for a set of springs.
    private static SpringSystem springSystem = SpringSystem.create();
    private LinearLayout.LayoutParams mCompressedParams;
    private LinearLayout.LayoutParams mExpandedParams;

    public CatelogView(Context context) {
        this(context, null, 0);
    }

    public CatelogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CatelogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CatelogView(Context context, CatelogViewBuilder cb) {
        super(context);
        cateb = cb;
        init(null);
    }

    private void init(AttributeSet attrs) {
        viewWidthHalf = this.getMeasuredWidth() / 2;
        viewHeightHalf = this.getMeasuredHeight() / 2;
        int radius = 0;
        if (viewWidthHalf > viewHeightHalf)
            radius = viewHeightHalf - 10;
        else
            radius = viewWidthHalf - 10;

        int mred = (int) (Math.random() * 128 + 127);
        int mgreen = (int) (Math.random() * 128 + 127);
        int mblue = (int) (Math.random() * 128 + 127);
        // init from attrs
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, styleable.CatelogView);
            try {
                // setCollapseOffset(a.getDimensionPixelSize(R.styleable.CatelogView_red, collapseOffset));
                red = a.getInteger(styleable.CatelogView_green, mred);
                green = a.getInteger(styleable.CatelogView_blue, mgreen);
                blue = a.getInteger(styleable.CatelogView_red, mblue);
                src_url = a.getString(styleable.CatelogView_src);
                resLayout = a.getResourceId(styleable.CatelogView_childlayout, 0);
                // initOpen(a.getBoolean(R.styleable.CatelogView_dtlOpen, true));
            } finally {
                a.recycle();
                color = 0xff << 24 | (red << 16) | (green << 8) | blue;
            }
        }
        init();
    }

    private boolean mExpanded = false;
    private ImageView image_location;
    private int
            resLayout = 0,
            color = 0,
            red = 0, green = 0, blue = 0, viewHeightHalf = 0, viewWidthHalf = 0;
    private String src_url = "";
    private RelativeLayout frame;
    private LinearLayout layoutnow;
    private ListView childLayout;
    private TextView text_view;
    private CatelogViewBuilder cateb;
    private ArrayAdapter<BasicDataBind> listAdapter;
    private LinearLayout child;
    private Spring spring;
    private SpringSupport springSystemsupport;

    private RelativeLayout.LayoutParams getParamsR(int h) {
        // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, h);
        // params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return new RelativeLayout.LayoutParams(-1, h);
    }

    private LayoutParams getParamsL(int h) {
        return new LinearLayout.LayoutParams(-1, h);
    }

    final Picasso theloadingimagepicasso = Picasso.with(getContext());

    private int getItemLayoutId() {
        return resLayout == 0 ? cateb.getChildItemLayoutResId() : resLayout;
    }

    private void inflate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutnow = (LinearLayout) inflater.inflate(R.layout.base_layout, this, true);
        child = (LinearLayout) inflater.inflate(getItemLayoutId(), null, false);
        frame = (RelativeLayout) findViewById(R.id.base_frame);
        childLayout = (ListView) findViewById(R.id.list);
        image_location = (ImageView) findViewById(R.id.image_src);
        text_view = (TextView) findViewById(R.id.secondlayertext);
    }

    private void init_header() throws Exception {
        if (cateb.getHeight() > 0f) {
            mCompressedParams = getParamsL(cateb.getHeightWhole());
            // frame.setLayoutParams(getParamsR(cateb.getHeightWhole()));
            frame.getLayoutParams().height = cateb.getHeightWhole();
            frame.setBackgroundResource(R.drawable.normal_gradient);
            if (cateb.useFragment()) {

            } else {
                if (cateb.getResId() == 0) {
                    theloadingimagepicasso
                            .load(cateb.getBannerImageUrl())
                            .fit().centerCrop()
                            .placeholder(R.drawable.load)
                            .error(R.drawable.load)
                            .into(image_location);
                } else {
                    image_location.setImageDrawable(getResources().getDrawable(cateb.getResId()));
                }
            }
            if (cateb.getResLayoutSecondLayer() != 0 || !cateb.getTitleOnSecondLayer().equalsIgnoreCase("")) {
                if (cateb.getResLayoutSecondLayer() == 0) {
                    text_view.setText(cateb.getTitleOnSecondLayer());
                } else {
                    text_view.setVisibility(GONE);
                }
            }
            image_location.setVisibility(View.VISIBLE);
            // image_location.setLayoutParams(mCompressedParams);
            image_location.setOnClickListener(this);
            // setLayoutParams(mCompressedParams);
        }
    }

    private void init_spring() throws Exception {
        if (cateb.hasSpring()) {
            // Add a spring to the system.
            spring = springSystem.createSpring();
            // Add a listener to observe the motion of the spring.
            spring.addListener(this);
            // Set the spring in motion; moving from 0 to 1
            spring.setEndValue(0);
            Log.d(TAG, "ID:" + spring.getId());
        }
    }

    private void init_listview() throws Exception {
        if (cateb.getPrimaryList().size() > 0) {
            //  TextView tvc = (TextView) child.findViewById(R.id.label_field);

            listAdapter = new BasicListingAdapter(getContext(), getItemLayoutId(), cateb.getPrimaryList());
            childLayout.setAdapter(listAdapter);
/*  int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,MeasureSpec.EXACTLY);
                    child.measure(widthMeasureSpec, heightMeasureSpec);
                    final int height = child.getMeasuredHeight() == 0 ? 400 : child.getMeasuredHeight();
*/
            final int height = 400;
            Log.d(TAG, "layout height: " + height);
            mExpandedParams = getParamsL((int) (height + cateb.getHeight()));
            springSystemsupport = new SpringSupport(height, (int) cateb.getHeight());
        }
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.TOP);
        setBackgroundColor(color == 0 ? cateb.getColor() == 0 ? 0 : cateb.getColor() : color);
        inflate();
        try {
            init_header();
            init_listview();
            init_spring();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageVisible(boolean visible) {
        image_location.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void changeLayout(LayoutParams l) {
        setLayoutParams(l);
        requestLayout();
    }

    public void triggerClose() {
        if (mExpanded) {
            if (cateb.hasSpring()) {
                // if (spring.isAtRest())
                spring.setEndValue(0);
                Log.d(toggleWatcher.TAG, "Trigger Spring Operation: " + springSystemsupport.getFcompressed() + " at rest?" + spring.isAtRest() + " id:" + spring.getId());
            } else {
                mExpanded = false;
                changeLayout(mCompressedParams);
            }
        }
        //        Log.d(toggleWatcher.TAG, "child item current state: " + mExpanded);
    }

    @Override
    public void onClick(View v) {
        if (cateb.hasSpring()) {
            if (spring.isAtRest()) {
                //  boolean f = !mExpanded;
                //  float t = mExpanded ? 1 : springSystemsupport.getFcompressed();
                spring.setEndValue(!mExpanded ? 1 : 0);
                Log.d(TAG, "onclick val expanded:" + !mExpanded + " id:" + spring.getId());
            }
        } else {
            mExpanded = !mExpanded;
            //invalidate();
            changeLayout(mExpanded ? mCompressedParams : mExpandedParams);
        }
        if (cateb.hasWatcher()) {
            cateb.notifyWatcher(this);
        }
    }

    @Override
    public void onSpringAtRest(Spring spring) {
        mExpanded = !mExpanded;

    }

    @Override
    public void onSpringActivate(Spring spring) {
        Log.d(toggleWatcher.TAG, "onSpringActivate:" + spring.getId());
    }

    @Override
    public void onSpringEndStateChange(Spring spring) {
        Log.d(toggleWatcher.TAG, "onSpringEndStateChange:" + spring.getId());
    }

    @Override
    public void onSpringUpdate(Spring S) {
        if (cateb.hasSpring()) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(S.getCurrentValue(),
                    0, 1, springSystemsupport.getFcompressed(), springSystemsupport.getFHeight()
            );
            changeLayout(getParamsL((int) mappedValue));
        }

        //   myView.setScaleX(scale);
        //    myView.setScaleY(scale);
    }
}
