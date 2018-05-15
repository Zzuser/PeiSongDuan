package com.cuc.cuc.deliver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cuc.cuc.deliver.HttpUtil.HttpUtil;
import com.cuc.cuc.deliver.HttpUtil.ServiceIP;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cuc.cuc.deliver.LoginActivity.data;

public class RegActivity extends Activity implements View.OnClickListener {


    private EditText alEtUsername;
    private EditText alEtUserpsw;
    private EditText alEtUserpsw2;
    private EditText alEtPhone;
    private Button regBtn;
    private ProgressDialog pd;

    private void assignViews() {
        alEtUsername = (EditText) findViewById(R.id.al_et_username);
        alEtUserpsw = (EditText) findViewById(R.id.al_et_userpsw);
        alEtUserpsw2 = (EditText) findViewById(R.id.al_et_userpsw2);
        alEtPhone = (EditText) findViewById(R.id.al_et_phone);
        regBtn = (Button) findViewById(R.id.regBtn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_activity);
        assignViews();
        regBtn.setOnClickListener(this);
    }

    public static void backgroundThreadShortToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        String name = alEtUsername.getText().toString();
        String psw = alEtUserpsw.getText().toString();
        String phone = alEtPhone.getText().toString();



        Map requsetBody = new HttpUtil.MyMap()
                .add("horsemanName", name)
                .add("horsemanPsw", psw)
                .add("horsemanAdd", "中国")
                .add("horsemanTel", phone)
                .build();

        HttpUtil.sendOkHttpRequest(ServiceIP.SERVICEIP+"horsemanReg.do",
                HttpUtil.myForm(requsetBody), new okhttp3.Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Toast.makeText(RegActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        Log.d("注册", "onResponse: " + responseData);
                        if (responseData.equals("1")) {
                            backgroundThreadShortToast(RegActivity.this, "注册成功");
                            startActivity(new Intent(RegActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            backgroundThreadShortToast(RegActivity.this, "注册失败");
                        }
                    }
                });

    }
}