package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.util.Log;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hesk on 30/3/15.
 */
public class SortSimpleAdapterData<T extends itemDisplay> {

    public final static String pattern = "^([^A-Za-z]).+";
    public Pattern compiledMatcher;

    public SortSimpleAdapterData() {
        compiledMatcher = Pattern.compile(pattern);
    }

    public boolean isSharpGroup(char h) {
        Matcher m = compiledMatcher.matcher(h + "");
        while (m.find()) {
            return true;
        }

        return false;
    }

    public void sortA2Za2z(ArrayList<T> listingnow) {
        Collections.sort(listingnow, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                final String h1 = lhs.toString();
                final String h2 = rhs.toString();
                Matcher m = compiledMatcher.matcher(h1);
                while (m.find()) {
                    return -1;
                }
                return h1.compareToIgnoreCase(h2);
            }
        });
    }


}
