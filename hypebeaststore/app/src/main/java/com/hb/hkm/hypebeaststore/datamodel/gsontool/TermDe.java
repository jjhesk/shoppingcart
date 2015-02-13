package com.hb.hkm.hypebeaststore.datamodel.gsontool;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hb.hkm.hypebeaststore.datamodel.Term;

import java.lang.reflect.Type;

/**
 * Created by hesk on 2/13/15.
 */
public class TermDe implements JsonDeserializer<Term>,
        InstanceCreator<Term> {

    @Override
    public Term deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jO = (JsonObject) json;
        return new Term(jO.get("term").getAsString(), jO.get("count").getAsInt());

    }

    @Override
    public Term createInstance(Type type) {
        return new Term("", 0);
    }
}
