package com.cuc.cuc.deliver;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cuc.cuc.deliver.HttpUtil.HttpUtil;
import com.cuc.cuc.deliver.HttpUtil.OkHttpInitUtil;
import com.cuc.cuc.deliver.HttpUtil.ServiceIP;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameT;
    private EditText userpsw;
    private Button regBtn;
    private Button loginBtn;
    public static LoginData data;
    String username;
    String password;
    private ProgressDialog pd;

    private void assignViews() {
        usernameT = (EditText) findViewById(R.id.username);
        userpsw = (EditText) findViewById(R.id.userpsw);
        regBtn = (Button) findViewById(R.id.reg_Btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        assignViews();

        pd = new ProgressDialog(this);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));


            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameT.getText().toString();
                password = userpsw.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    pd.setMessage("加载中,请稍后...");
                    pd.setCanceledOnTouchOutside(false);//点击返回键消失，点击其他地方不消失。
                    pd.show();
//           RequestBody requestBody = new FormBody.Builder()
//                            .add("horsemanName", username)
//                            .add("horsemanPsw", password)
//                            .build();
                    Map requsetBody=new HashMap();
                    requsetBody.put("horsemanName", username);
                    requsetBody.put("horsemanPsw", password);

                    try {
                        HttpUtil.sendOkHttpRequest(ServiceIP.SERVICEIP+"horsemanLogin.do",
                                HttpUtil.myForm(requsetBody), new okhttp3.Callback() {

                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        if (pd != null && pd.isShowing()) {
                                            pd.dismiss();
                                        }
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String responseData = response.body().string();
                                        Log.d("onResponse", "onResponse: " + responseData);
                                        SharedPreferences.Editor editor = getSharedPreferences("loginData", MODE_PRIVATE).edit();
                                        editor.putString("loginData", responseData);
                                        editor.apply();
                                        Gson gson = new Gson();
                                         data = gson.fromJson(responseData, LoginData.class);

                                        startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                                        finish();
//                                        setJPush(data);
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
