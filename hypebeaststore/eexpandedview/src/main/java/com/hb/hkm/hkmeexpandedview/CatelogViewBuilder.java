package com.hb.hkm.hkmeexpandedview;

import android.app.Fragment;
import android.content.Context;

import com.hb.hkm.hkmeexpandedview.databindingmodel.BasicDataBind;
import com.hb.hkm.hkmeexpandedview.databindingmodel.SlickBind;

import java.util.ArrayList;

/**
 * Created by hesk on 2/24/15.
 */
public class CatelogViewBuilder {
    public enum CHILD_LAYOUT_TYPE {
        DEFAULT, ICON_TEXT, CUSTOM
    }

    private int resLayoutChildItem = 0, red = 0, green = 0, blue = 0, viewHeightHalf = 0, viewWidthHalf = 0, header_image_drawable_resId = 0;
    private float collapsed_height;
    private ArrayList<BasicDataBind> list_source;
    private boolean spring_enable = false;
    private toggleWatcher watcher;
    private String imgurl = "";
    protected Context mctx;

    public CatelogViewBuilder(Context c) {
        setLayoutChildType(CHILD_LAYOUT_TYPE.DEFAULT);
        enableFBSpring(true);
        mctx = c;
    }

    public CatelogView create() {
        return new CatelogView(mctx, this);
    }

    public CatelogViewBuilder enableFBSpring(boolean b) {
        spring_enable = b;
        return this;
    }

    public CatelogViewBuilder preset_src(String image_uri, float height) {
        imgurl = image_uri;
        header_image_drawable_resId = 0;
        collapsed_height = height;
        return this;
    }

    public CatelogViewBuilder preset_src(int src, float height) {
        header_image_drawable_resId = src;
        imgurl = "";
        collapsed_height = height;
        return this;
    }

    public CatelogViewBuilder rndColor() {
        red = (int) (Math.random() * 128 + 127);
        green = (int) (Math.random() * 128 + 127);
        blue = (int) (Math.random() * 128 + 127);
        return this;
    }

    public CatelogViewBuilder setItemChildLayoutId(int resLayoutId) {
        resLayoutChildItem = resLayoutId;
        setLayoutChildType(CHILD_LAYOUT_TYPE.CUSTOM);
        return this;
    }

    public CatelogViewBuilder setLayoutChildType(final CHILD_LAYOUT_TYPE res) {
        switch (res) {
            case DEFAULT:
                resLayoutChildItem = R.layout.l_default;
                break;
            case ICON_TEXT:
                resLayoutChildItem = R.layout.l_icon_text_c;
                break;
            case CUSTOM:
                break;
            default:
                resLayoutChildItem = R.layout.l_default;
                break;
        }
        return this;
    }

    private int resLayoutSecondLayer = 0;
    private int resStyleId = 0;
    private String titleSecondLayer = "";

    public CatelogViewBuilder setSecondLayerOnBanner(int resId) {
        resLayoutSecondLayer = resId;
        return this;

    }

    public int getResLayoutSecondLayer() {
        return resLayoutSecondLayer;
    }

    public CatelogViewBuilder setImageTitle(String content) {
        titleSecondLayer = content;
        return this;
    }

    /**
     * fragment control display
     */
    private Fragment customFragment;
    private boolean useFragmentInstead = false;

    public CatelogViewBuilder setHeaderFragment(Fragment fragment) {
        useFragmentInstead = true;
        titleSecondLayer = "";
        customFragment = fragment;
        return this;
    }

    public boolean useFragment() {
        return useFragmentInstead;
    }

    public Fragment getCustomFragment() {
        return customFragment;
    }

    /**
     * use custom style to override the default style
     * @param styleId
     * @return
     */
    public CatelogViewBuilder withCustomStyle(int styleId) {
        resStyleId = styleId;
        return this;
    }

    /**
     * import data listing
     * @param str
     * @return
     */
    public CatelogViewBuilder setDataList(ArrayList<BasicDataBind> str) {
        list_source = str;
        return this;
    }

    public ArrayList<BasicDataBind> getPrimaryList() {
        return list_source;
    }

    /**
     * set up the display title on the header section
     * @return
     */
    public String getTitleOnSecondLayer() {
        return titleSecondLayer;
    }

    public int getChildItemLayoutResId() {
        return resLayoutChildItem;
    }

    /**
     * using spring interactions
     * @return
     */
    public boolean hasSpring() {
        return spring_enable;
    }

    public int getResId() {
        return header_image_drawable_resId;
    }

    public String getBannerImageUrl() {
        return imgurl;
    }

    public float getHeight() {
        return collapsed_height;
    }

    public int getHeightWhole() {
        return (int) collapsed_height;
    }

    public int getColor() {
        return 0xff << 24 | (red << 16) | (green << 8) | blue;
    }

    public CatelogViewBuilder setWatcher(toggleWatcher t) {
        watcher = t;
        return this;
    }

    public boolean hasWatcher() {
        return watcher != null;
    }

    public void notifyWatcher(CatelogView view) {
        watcher.onSelect(view);
    }
}
