package com.raju.emogi.data.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raju Yadav
 */

public class DataParser implements JsonDeserializer<EmogiContent> {
    @Override
    public EmogiContent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        EmogiContent result = new EmogiContent();
        try {
            final HashMap<String, EmogiContentValue> map = readServiceUrlMap(json.getAsJsonObject());
            if (map != null) {
                result.dataValues = map;
            }
        } catch (JsonSyntaxException ex) {
            return null;
        }
        return result;
    }

    private HashMap<String, EmogiContentValue> readServiceUrlMap(final JsonObject jsonObject) throws JsonSyntaxException {
        if (jsonObject == null) {
            return null;
        }

        Gson gson = new Gson();
        HashMap<String, EmogiContentValue> emContent = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            EmogiContentValue value = gson.fromJson(entry.getValue(), EmogiContentValue.class);
            emContent.put(key, value);
        }
        return emContent;
    }

}
