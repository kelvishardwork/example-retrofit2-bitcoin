package com.brakeel.exampleretrofit2bitcoin.util;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public class TickerDeserialize implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement ticker = json.getAsJsonObject();

        if (json.getAsJsonObject().get("ticker") != null) {
            ticker = json.getAsJsonObject().get("ticker");
        }

        return (new Gson().fromJson(ticker, Ticker.class));
    }
}
