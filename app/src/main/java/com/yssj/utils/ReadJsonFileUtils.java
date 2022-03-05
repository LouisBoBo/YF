package com.yssj.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ReadJsonFileUtils {
    /**
     */
    public static JSONObject ReadHJsonFile(String url) {
        LogYiFu.e("JsonFile",url);
        JSONObject jsonObject = null;
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
            HttpResponse response = httpClient.execute(httpGet);
            StringBuilder builder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent(), "utf-8"));
            for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
                    .readLine()) {
                builder.append(s);
            }
            LogYiFu.e("JsonFile",builder.toString());
            jsonObject = new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject = null;
        }
        return jsonObject;
    }


}
