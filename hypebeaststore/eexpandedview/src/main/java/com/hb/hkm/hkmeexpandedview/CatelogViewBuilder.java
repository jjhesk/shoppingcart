package com.hb.hkm.hkmeexpandedview;

import com.hb.hkm.hkmeexpandedview.list.DataBind;

import java.util.ArrayList;

/**
 * Created by hesk on 2/24/15.
 */
public class CatelogViewBuilder {
    public enum CHILD_LAYOUT_TYPE {
        DEFAULT, ICON_TEXT
    }

    private int resLayout = 0, red = 0, green = 0, blue = 0, viewHeightHalf = 0, viewWidthHalf = 0, resSrcId = 0;
    private float collapsed_height;
    private ArrayList<DataBind> list_source;

    public CatelogViewBuilder() {

    }

    public CatelogViewBuilder preset_src(int src, float height) {
        resSrcId = src;
        collapsed_height = height;
        return this;
    }

    public CatelogViewBuilder rndColor() {
        red = (int) (Math.random() * 128 + 127);
        green = (int) (Math.random() * 128 + 127);
        blue = (int) (Math.random() * 128 + 127);
        return this;
    }

    public CatelogViewBuilder setLayout(CHILD_LAYOUT_TYPE res) {
        switch (res) {
            case DEFAULT:
                resLayout = R.layout.l_default;
                break;
            case ICON_TEXT:
                resLayout = R.layout.l_icon_text_c;
                break;
            default:
                resLayout = R.layout.l_default;
                break;
        }
        return this;
    }

    public CatelogViewBuilder setCustomLayout(int res) {
        resLayout = res;
        return this;
    }

    public CatelogViewBuilder setDataList(ArrayList<DataBind> str) {
        list_source = str;
        return this;
    }

    public ArrayList<DataBind> getPrimaryList() {
        return list_source;
    }

    public int getLayoutResId() {
        if (resLayout == 0) {
            resLayout = R.layout.l_default;
        }
        return resLayout;
    }

    public int getResId() {
        return resSrcId;
    }

    public float getHeight() {
        return collapsed_height;
    }

    public int getColor() {
        return 0xff << 24 | (red << 16) | (green << 8) | blue;
    }
}
