package com.secured.qrpay.http.impl;

import com.secured.qrpay.enums.HttpType;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.http.HttpManager;
import com.secured.qrpay.http.RequestParam;
import com.secured.qrpay.util.BasicUtil;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class HttpManagerImpl implements HttpManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpManagerImpl.class);
    @Override
    public <T> T post(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args) {
        try {
            final HttpPost httpPost = (HttpPost) prepareHttpData(url, requestParams, timeout, HttpType.POST);
            CloseableHttpClient client = HttpClients.createDefault();
            String response = client.execute(httpPost, new BasicHttpClientResponseHandler());
            return BasicUtil.jsonToObjectUsingGson(response, clazz);
        } catch (Exception e) {
            LOGGER.error("Error occurred while post call for: {}, error: {}", url, e.getMessage());
            return null;
        }
    }

    @Override
    public <T> T get(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args) {
        try {
            final HttpGet httpGet = (HttpGet) prepareHttpData(url, requestParams, timeout, HttpType.GET);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            String response = client.execute(httpGet, new BasicHttpClientResponseHandler());
            return BasicUtil.jsonToObjectUsingGson(response, clazz);
        } catch (Exception e) {
            LOGGER.error("Error occurred while get call for: {}, error: {}", url, e.getMessage());
            return null;
        }
    }

    @Override
    public <T> List<T> getList(String url, Map<RequestParam, String> requestParams, Integer timeout, Class<T> clazz, Object... args) {
        try {
            final HttpGet httpGet = (HttpGet) prepareHttpData(url, requestParams, timeout, HttpType.GET);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            String response = client.execute(httpGet, new BasicHttpClientResponseHandler());
            return BasicUtil.jsonToCollectionUsingGson(response, clazz);
        } catch (Exception e) {
            LOGGER.error("Error occurred while get list call for: {}, error: {}", url, e.getMessage());
            return null;
        }
    }

    private void setTimeout(Integer timeout, HttpUriRequestBase httpPost) {
        if (BasicUtil.isNull(timeout)) {
            httpPost.setConfig(RequestConfig.DEFAULT);
        } else {
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.of((long) timeout, TimeUnit.SECONDS))
                    .build());
        }
    }

    private HttpUriRequestBase prepareHttpData(String url, Map<RequestParam, String> requestParams, Integer timeout, HttpType httpType) throws AppException {
        HttpUriRequestBase http;
        if (httpType.equals(HttpType.GET)) {
            http = new HttpGet(url);
        } else if (httpType.equals(HttpType.POST)) {
            http = new HttpPost(url);
        } else {
            throw new AppException("HttpType is not supported " + httpType);
        }
        final List<NameValuePair> params = new ArrayList<>();
        Iterator<Map.Entry<RequestParam, String>> iterator = requestParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RequestParam, String> keyValue = iterator.next();
            if (keyValue.getKey().isHeader()) {
                http.setHeader(keyValue.getKey().getName(), keyValue.getValue());
            }
            if (keyValue.getKey().isRequestBody()) {
                params.add(new BasicNameValuePair(keyValue.getKey().getName(), keyValue.getValue()));
            }
        }
        http.setEntity(new UrlEncodedFormEntity(params));
        setTimeout(timeout, http);
        return http;
    }
}
