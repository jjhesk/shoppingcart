package com.hb.hkm.hkmeexpandedview;

/**
 * Created by hesk on 2/26/2015.
 */
public class SpringSupport {
    private float full_height = 1, fcompressed;

    public SpringSupport(int JustExpanded, int compressed) {
        float h = ((float) JustExpanded + (float) compressed);
        float t = (float) compressed;
        fcompressed = t;
        full_height = h;
    }

    public int getScaledHeight(float scale) {
        return (int) (full_height * scale);
    }

    public float getFcompressed() {
        return fcompressed;
    }

    public float getFHeight() {
        return full_height;
    }
}
