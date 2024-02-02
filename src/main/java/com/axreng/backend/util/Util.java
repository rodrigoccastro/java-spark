package com.axreng.backend.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;

public class Util {

    // validate base_url and response especific error
    public static String getValidateBaseUrl(String baseUrl) throws Exception {
        if (baseUrl==null || baseUrl.isEmpty()) {
            throw new Exception("Not found BASE_URL!");
        }
        return baseUrl;
    }

    // validate param from body and response especific error
    public static String getKeywordFromBody(Request req, String key) throws Exception {
        String requestBody = req.body();
        if (requestBody==null || requestBody.isEmpty()) {
            throw new Exception("The body cannot be null!");
        }

        JsonObject json;
        try {
            json = new Gson().fromJson(requestBody, JsonObject.class);
        } catch (Exception ex) {
            throw new Exception("The body cannot be converted to Json!");
        }

        var keyword = json.get(key);
        if (keyword==null) {
            throw new Exception("The search term cannot be null!");
        }

        return keyword.getAsString();
    }

    // validate param from param and response especific error
    public static String getKeywordFromParams(Request req, String key) throws Exception {
        String value = req.params(key);
        if (value==null || value.isEmpty()) {
            throw new Exception("The param "+key+" cannot be null!");
        }
        return value;
    }

}
