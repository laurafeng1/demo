package com.example.demo.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil<T> {
    static Logger log = LoggerFactory.getLogger(JsonUtil.class);
    public static <T> T readFromJson(String json, Class<T> c) {
        Gson gson = new Gson();
        T object = null;
        try{
            object = gson.fromJson(json, c);
        } catch(Exception e) {
            log.error("read json {} failed", json, e);
        }
        return object;
    }

    public static <T> String writeWithJsonFormat(T c) {
        Gson gson = new Gson();
        String json = null;
        try{
            json = gson.toJson(c);
        } catch(Exception e){
            log.error("write user {} to json failed", c.toString());
        }
        return json;
    }
}
