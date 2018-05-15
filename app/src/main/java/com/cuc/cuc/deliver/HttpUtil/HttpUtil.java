package com.cuc.cuc.deliver.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static final MediaType MYUTF8
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static void sendOkHttpRequest(final String address, final RequestBody requestBody, final okhttp3.Callback callback) {
        OkHttpInitUtil okHttpInitUtil = new OkHttpInitUtil();
        OkHttpClient client = okHttpInitUtil.httpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static RequestBody myForm(Map<String, String> formParams) {

        StringBuffer datas = new StringBuffer();
        //设置表单参数
        for (String key : formParams.keySet()) {
            datas.append(key + "=" + formParams.get(key) + "&");
        }

        RequestBody body = RequestBody.create(MYUTF8, datas.toString());
        return body;
    }

    public static final class MyMap {
        private final Map<String, String> requestMap = new HashMap<>();

        public MyMap add(String name, String value) {
            requestMap.put(name,value);
            return this;
        }
        public Map<String,String> build(){
            return requestMap;
        }
    }
}