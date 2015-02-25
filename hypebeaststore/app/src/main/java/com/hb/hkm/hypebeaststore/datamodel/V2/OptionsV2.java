package com.hb.hkm.hypebeaststore.datamodel.V2;

import java.util.HashMap;

/**
 * Created by hesk on 2/23/15.
 */
public class OptionsV2 {
    private OptionSingleV2 option;
    private String value;

    public OptionsV2() {
    }

    public HashMap<String, String> getpair() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put(option.getcodename(), value);
        return map;
    }
}
