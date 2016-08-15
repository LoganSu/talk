package com.youlb.utils.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    /**
     * Get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, List<NameValuePair> params) {
        HttpClient httpclient = HttpClients.createDefault();
        String body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            // 发送请求
            HttpResponse httpresponse = httpclient.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * Post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, List<NameValuePair> params) {
        return post(url, null, params, null);
    }

    public static String post(String url, Header[] headers, List<NameValuePair> params, String charset) {
        HttpClient httpclient = HttpClients.createDefault();
        String body = null;
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            if (headers != null) {
                httppost.setHeaders(headers);
            }
            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params, charset));
            // 发送请求
            HttpResponse httpresponse = httpclient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

}

