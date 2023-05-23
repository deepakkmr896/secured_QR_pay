package com.secured.qrpay.util;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicUtil.class);

    private BasicUtil() {

    }

    public static boolean isNotNull(Object obj) {
        return !Objects.isNull(obj);
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public static boolean isNotEmpty(String str) {
        return isNotNull(str) && !str.equals("");
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || str.equals("");
    }
    public static <T> boolean isEmpty(List<T> list) {
        return isNull(list) || list.size() == 0;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return isNotNull(list) && list.size() > 0;
    }

    public static <T> T jsonToObjectUsingJackson(String jsonString, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            LOGGER.error("Error occurred while converting json to object!");
            return null;
        }
    }

    public static <T> T jsonToObjectUsingGson(String jsonString, Class<T> clazz) {
        try {
            return new Gson().fromJson(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.error("Error occurred while converting json to object!");
            return null;
        }
    }

    public static <T> List<T> jsonToCollectionUsingGson(String jsonString, Class<T> clazz) {
        try {
            Type targetType = new TypeToken<ArrayList<T>>() { }.getType();
            return new Gson().fromJson(jsonString, targetType);
        } catch (Exception e) {
            LOGGER.error("Error occurred while converting json to list object!");
            return null;
        }
    }
}
