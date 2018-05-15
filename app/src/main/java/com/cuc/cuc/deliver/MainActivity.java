package com.cuc.cuc.deliver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.cuc.cuc.deliver.HttpUtil.HttpUtil;
import com.cuc.cuc.deliver.HttpUtil.ServiceIP;
import com.cuc.cuc.deliver.pojo.Food_list;
import com.cuc.cuc.deliver.pojo.JsonRootBean;
import com.google.gson.Gson;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

public class MainActivity extends Activity {



    private TextView msg;
    private TextView order;
    private TextView orderst;
    private Button orderCFM;
    private Button orderFNS;
    private String orders;
    private TextView food;
    private String foods;







    private static final int UI1 = 1;
    private static final int UI2 = 2;
    private static final int UI3 = 3;
    private  String responseData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        Log.d(TAG, "onCreate: ==+++++" + intent.getStringExtra("MESSAGE"));
        msg = (TextView) findViewById(R.id.msgTxt);
        order = (TextView) findViewById(R.id.orderTxt);
        orderst = (TextView) findViewById(R.id.my_order);
        orderCFM = (Button) findViewById(R.id.order_cfm);
        orderFNS = (Button) findViewById(R.id.finish_btn);
        food=(TextView) findViewById(R.id.foodTxt);

        msg.setText(intent.getStringExtra("MESSAGE"));
        RequestBody requestBody = new FormBody.Builder()
                .add("orderId", intent.getStringExtra("MESSAGE"))
                .build();
        HttpUtil.sendOkHttpRequest(ServiceIP.SERVICEIP+"selectOrderByOrderId.do",
                requestBody, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        responseData = response.body().string();
                        Log.d("onResponse", "onResponse: " + responseData);
                        Gson gson=new Gson();
                        JsonRootBean jsonRootBean=gson.fromJson(responseData,JsonRootBean.class);

                        //时间戳转换时间
                        String res;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        long lt = new Long(jsonRootBean.getOrder_time());
                        Date date = new Date(lt);
                        res = simpleDateFormat.format(date);
                        //
                        orders="客户地址："+jsonRootBean.getUser_add()+"\n"+
                                "客户名："+jsonRootBean.getUser_name()+"\n"+
                                "客户电话："+jsonRootBean.getUser_tel()+"\n"+
                                "商户id："+jsonRootBean.getShop_id()+"\n"+
                                "商户地址："+jsonRootBean.getShop_add()+"\n"+
                                "商户电话："+jsonRootBean.getShop_tel()+"\n"+
                                "订单号："+jsonRootBean.getOrder_id()+"\n"+
                                "订单总额："+jsonRootBean.getOrder_money()+"元"+"\n"+
                                "订单状态："+jsonRootBean.getOrder_status()+"\n"+
                                "订餐时间："+res;
                        foods="";
                        Log.d("菜单长度", "onResponse: " + jsonRootBean.getFood_list().size());
                        for (int z=0;z<jsonRootBean.getFood_list().size();z++) {
                            int x=z+1;
                            try {
                                foods = foods + "菜品"+x+"：" + jsonRootBean.getFood_list().get(z).getFood_name() + "x"+jsonRootBean.getFood_list().get(z).getFood_count()+"\n";

                            }
                           finally {

                            }
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = UI1;
                                handler.sendMessage(message);
                            }
                        }).start();
                    }
                });
        orderCFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                String loginData = pref.getString("loginData", "");
                final Gson gson = new Gson();
                LoginData data = gson.fromJson(loginData, LoginData.class);

                Log.d("requestBody", "horsemanId: " + String.valueOf(data.getId()));
                Log.d("requestBody", "orderId: " + getIntent().getStringExtra("MESSAGE"));
                RequestBody requestBody = new FormBody.Builder()
                        .add("horsemanId", String.valueOf(data.getId()))
                        .add("orderId", getIntent().getStringExtra("MESSAGE"))
                        .build();
                HttpUtil.sendOkHttpRequest(ServiceIP.SERVICEIP+"orderCFM.do",
                        requestBody, new okhttp3.Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseData = response.body().string();
                                Gson gson=new Gson();
                                Log.d("dsfgnoiondfvd", "onResponse: " + gson.toJson(responseData));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        orders=responseData;
                                        Message message = new Message();
                                        message.what = UI2;
                                        handler.sendMessage(message);
                                    }
                                }).start();
//                                Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
//                                intent1.putExtra("orders", orders);
//                                MainActivity.this.startActivity(intent1);

                            }
                        });
            }
        });
        orderFNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                String loginData = pref.getString("loginData", "");
                Gson gson = new Gson();
                LoginData data = gson.fromJson(loginData, LoginData.class);
                Log.d("requestBody", "horsemanId: " + String.valueOf(data.getId()));
                Log.d("requestBody", "orderId: " + getIntent().getStringExtra("MESSAGE"));
                RequestBody requestBody = new FormBody.Builder()
                        .add("horsemanId", String.valueOf(data.getId()))
                        .add("orderId", getIntent().getStringExtra("MESSAGE"))
                        .build();
                HttpUtil.sendOkHttpRequest(ServiceIP.SERVICEIP+"orderFNS.do",
                        requestBody, new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                responseData = response.body().string();
//                                parseJSONWithGSON(responseData);
                                Log.d("onResponse", "onResponse: " + responseData);
                                Intent intent1 = new Intent(MainActivity.this, Main2Activity.class);
//                                intent1.putExtra("orders", orders);
//                                Toast.makeText(MainActivity.this, "订单完成", Toast.LENGTH_SHORT).show();
                                MainActivity.this.startActivity(intent1);
                                finish();
                            }
                        });
            }
        });
    }

//    private void parseJSONWithGSON(String jsonData){
//        Gson gson=new Gson();
//        JsonRootBean jsonRootBean=gson.fromJson(jsonData,JsonRootBean.class);
//        Log.d("dads","sadasd"+jsonRootBean.getOrder_status());
//    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI1:
                    order.setText(orders);
                    food.setText(foods);
                    break;
                case UI2:
                    if (orders.equals("200"))
                    { orderst.setText("接单成功");}
                    else {orderst.setText("接单失败");}
                    break;
                case UI3:
                    order.setText(orders);
                    break;
                default:
                    break;

            }
        }
    };
}
