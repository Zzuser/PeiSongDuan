package com.cuc.cuc.deliver.Jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cuc.cuc.deliver.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * Created by Jack on 16/10/31.
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "收到了JPush的通知");
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "接收到推送下来的通知");
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.i(TAG, "title==" + title);
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.i(TAG, "message==" + message);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG, "extra==" + extra);

                Intent intent1 = new Intent(context, MainActivity.class);
            intent1.putExtra("MESSAGE", message);
            context.startActivity(intent1);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "用户打开了通知");
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Map<String, Object> map = getMapFromExtra(extra);
            String url = map.get("url").toString();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }

    /**
     * 推送过来的url信息(json数据)转为Map集合
     *
     * @param extra
     * @return
     */
    private Map<String, Object> getMapFromExtra(String extra) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(extra);
            Iterator<String> keys = jsonObject.keys();
            String key;
            Object value;
            while (keys.hasNext()) {
                key = keys.next();
                value = jsonObject.get(key);
                map.put(key, value);
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
