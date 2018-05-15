/**
  * Copyright 2018 bejson.com 
  */
package com.cuc.cuc.deliver.pojo;

/**
 * Auto-generated: 2018-05-11 17:39:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Food_list {

    private int food_id;
    private String food_name;
    private int food_count;
    private String category;
    private FoodShop foodShop;
    public void setFood_id(int food_id) {
         this.food_id = food_id;
     }
     public int getFood_id() {
         return food_id;
     }

    public void setFood_name(String food_name) {
         this.food_name = food_name;
     }
     public String getFood_name() {
         return food_name;
     }

    public void setFood_count(int food_count) {
         this.food_count = food_count;
     }
     public int getFood_count() {
         return food_count;
     }

    public void setCategory(String category) {
         this.category = category;
     }
     public String getCategory() {
         return category;
     }

    public void setFoodShop(FoodShop foodShop) {
         this.foodShop = foodShop;
     }
     public FoodShop getFoodShop() {
         return foodShop;
     }

}