package com.hb.hkm.hkmeexpandedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hb.hkm.hkmeexpandedview.list.DataBind;
import com.hb.hkm.hkmeexpandedview.list.KRAdapter;

import static com.hb.hkm.hkmeexpandedview.R.styleable;

/**
 * Created by hesk on 2/24/15.
 */
public class CatelogView extends LinearLayout implements View.OnClickListener {
    private boolean mExpanded = false;
    private ImageView image_location;
    private LinearLayout.LayoutParams mCompressedParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 50);

    private LinearLayout.LayoutParams mExpandedParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 200);

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

        int color = 0;
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
        } else {
            if (cateb != null) {
                color = cateb.getColor();
                resLayout = cateb.getLayoutResId();
            }

        }

        init(color);
    }

    private int
            resLayout,
            red = 0, green = 0, blue = 0, viewHeightHalf = 0, viewWidthHalf = 0;
    private String src_url = "";
    private LinearLayout layoutnow;
    private ListView childLayout;
    private CatelogViewBuilder cateb;
    private ArrayAdapter<DataBind> listAdapter;

    private RelativeLayout.LayoutParams getParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    private void init(int color) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.TOP);
        setOnClickListener(this);
        setBackgroundColor(color);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutnow = (LinearLayout) inflater.inflate(R.layout.base_layout, this, true);
        image_location = (ImageView) findViewById(R.id.base_image_frame);
        childLayout = (ListView) findViewById(R.id.list);
        if (cateb != null) {
            if (cateb.getResId() > 0 && cateb.getHeight() > 0f) {
                mCompressedParams = new LinearLayout.LayoutParams(-1, (int) cateb.getHeight());
                image_location.setImageDrawable(getResources().getDrawable(cateb.getResId()));
                image_location.setVisibility(View.VISIBLE);
                image_location.setLayoutParams(mCompressedParams);
                setLayoutParams(mCompressedParams);
            } else {
                // throw new IllegalArgumentException("Bad image source id or bad header height!");
            }

            if (cateb.getPrimaryList().size() > 0) {
                LinearLayout child = (LinearLayout) inflater.inflate(resLayout, null, false);
                //  TextView tvc = (TextView) child.findViewById(R.id.label_field);
                int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                child.measure(widthMeasureSpec, heightMeasureSpec);

                listAdapter = new KRAdapter(getContext(), resLayout, cateb.getPrimaryList());
                childLayout.setAdapter(listAdapter);
                final int height = child.getMeasuredHeight();
                float tp = height + cateb.getHeight();
                mExpandedParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, (int) tp);
                //
                // childLayout.setLayoutParams();
            }

        } else {
            setLayoutParams(mCompressedParams);
        }
     /*   if (getMeasuredHeight() == 0)
            mExpandedParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 400);
        else mExpandedParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, getMeasuredHeight());*/


    }


    public void setImageVisible(boolean visible) {
        image_location.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        setLayoutParams(mExpanded ? mCompressedParams : mExpandedParams);
        mExpanded = !mExpanded;
        //invalidate();
        requestLayout();
    }

    public boolean isOpenned() {
        return mExpanded;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw the View
    }

}
