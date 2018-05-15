/**
  * Copyright 2018 bejson.com 
  */
package com.cuc.cuc.deliver.pojo;
import java.util.List;

/**
 * Auto-generated: 2018-05-11 17:39:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private int order_id;
    private int order_money;
    private String order_number;
    private String order_status;
    private long order_time;
    private String arrive_time;
    private int horseman_id;
    private int shop_id;
    private String shop_name;
    private String shop_tel;
    private String shop_add;
    private String horseman_tel;
    private String user_name;
    private String user_add;
    private String user_tel;
    private List<Food_list> food_list;
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
    public String getShop_name() {
        return shop_name;
    }

    public void setShop_tel(String shop_tel) {
        this.shop_tel = shop_tel;
    }
    public String getShop_tel() {
        return shop_tel;
    }

    public void setShop_add(String shop_add) {
        this.shop_add = shop_add;
    }
    public String getShop_add() {
        return shop_add;
    }
    public void setOrder_id(int order_id) {
         this.order_id = order_id;
     }
     public int getOrder_id() {
         return order_id;
     }

    public void setOrder_money(int order_money) {
         this.order_money = order_money;
     }
     public int getOrder_money() {
         return order_money;
     }

    public void setOrder_number(String order_number) {
         this.order_number = order_number;
     }
     public String getOrder_number() {
         return order_number;
     }

    public void setOrder_status(String order_status) {
         this.order_status = order_status;
     }
     public String getOrder_status() {
         return order_status;
     }

    public void setOrder_time(long order_time) {
         this.order_time = order_time;
     }
     public long getOrder_time() {
         return order_time;
     }

    public void setArrive_time(String arrive_time) {
         this.arrive_time = arrive_time;
     }
     public String getArrive_time() {
         return arrive_time;
     }

    public void setHorseman_id(int horseman_id) {
         this.horseman_id = horseman_id;
     }
     public int getHorseman_id() {
         return horseman_id;
     }

    public void setShop_id(int shop_id) {
         this.shop_id = shop_id;
     }
     public int getShop_id() {
         return shop_id;
     }

    public void setHorseman_tel(String horseman_tel) {
         this.horseman_tel = horseman_tel;
     }
     public String getHorseman_tel() {
         return horseman_tel;
     }

    public void setUser_name(String user_name) {
         this.user_name = user_name;
     }
     public String getUser_name() {
         return user_name;
     }

    public void setUser_add(String user_add) {
         this.user_add = user_add;
     }
     public String getUser_add() {
         return user_add;
     }

    public void setUser_tel(String user_tel) {
         this.user_tel = user_tel;
     }
     public String getUser_tel() {
         return user_tel;
     }

    public void setFood_list(List<Food_list> food_list) {
         this.food_list = food_list;
     }
     public List<Food_list> getFood_list() {
         return food_list;
     }

}