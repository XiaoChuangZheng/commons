package com.yishan.biz.pack.httpclient;

import com.yishan.biz.pack.httpclient.domain.HttpResult;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constants.MethodEnum;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 * <p>
 * 创建日期 2018年04月23日}
 *
 * @author zhengxiaochuang(zhengxiaochuang @ eefung.com)
 */
public class HttpUtil {
    public static HttpResult execute(String url, Map<String, String> headers, Map<String, String> params, MethodEnum methodType) throws IOException {
        if (methodType == null) {
            throw new IllegalArgumentException("http请求参数类型错误");
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (params == null) {
            params = new HashMap<>();
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResult result = null;
        switch (methodType) {
            case GET:
                if (params.size() > 0) {
                    url = formatGetMethodUrl(url, params);
                }
                HttpGet httpGet = new HttpGet(url);
                result = getHttpResult(httpGet, headers, new HashMap<>(), httpClient);
                break;
            case POST:
                HttpPost httpPost = new HttpPost(url);
                result = getHttpResult(httpPost, headers, params, httpClient);
                break;
            case PUT:
                HttpPut httpPut = new HttpPut(url);
                result = getHttpResult(httpPut, headers, params, httpClient);
                break;
            case DELETE:
                if (params.size() > 0) {
                    url = formatGetMethodUrl(url, params);
                }
                HttpDelete httpDelete = new HttpDelete(url);
                result = getHttpResult(httpDelete, headers, new HashMap<>(), httpClient);
                break;
        }

        return result;
    }

    private static void setRequestConfig(HttpRequestBase method) {
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setSocketTimeout(3000)
                                                   .setConnectTimeout(3000)
                                                   .build();
        method.setConfig(requestConfig);
    }

    private static HttpResult getHttpResult(HttpRequestBase method, Map<String, String> headers, Map<String, String> params, CloseableHttpClient httpClient) throws IOException {
        setRequestConfig(method);
        List<NameValuePair> nvps = new ArrayList<>();
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                method.addHeader(entry.getKey(), entry.getValue());
            }
        }
        params.forEach((key, value) -> nvps.add(new BasicNameValuePair(key, value)));
        if (method instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase m = (HttpEntityEnclosingRequestBase) method;
            m.setEntity(new UrlEncodedFormEntity(nvps));
            method = m;
        }
        CloseableHttpResponse response = httpClient.execute(method);
        return getContentByEntity(response);
    }

    private static String formatGetMethodUrl(String url, Map<String, String> params) {
        Set<String> sets = new HashSet<>();
        params.forEach((key, value) -> {
            sets.add(key + "=" + value);
        });
        String s = String.join("&", sets);
        return url + "?" + s;
    }

    private static HttpResult getContentByEntity(CloseableHttpResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpResult result = new HttpResult();
        byte[] bytes = new byte[1024];
        int statusCode = response.getStatusLine().getStatusCode();
        String reasonPhrase = response.getStatusLine().getReasonPhrase();
        try {
            InputStream contentStream = response.getEntity().getContent();
            int read;
            while ((read = contentStream.read(bytes)) > 0) {
                sb.append(new String(bytes, 0, read));
            }
            EntityUtils.consume(response.getEntity());
        } finally {
            response.close();

        }
        result.setMessage(reasonPhrase);
        result.setResult(sb.toString());
        result.setStatusCode(statusCode);
        return result;
    }

    public static void main(String[] args) throws IOException {
        HttpResult result = HttpUtil.execute("https://biz.antfact.com/tags/industry/all", new HashMap<>(), new HashMap<>(), MethodEnum.GET);
        System.out.println(result.getResult());

    }

}
