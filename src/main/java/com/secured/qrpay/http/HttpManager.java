package com.secured.qrpay.http;

import java.util.List;
import java.util.Map;

public interface HttpManager {
    <T>  T post(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args);

    <T> T get(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args);

    <T> List<T> getList(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args);
}
