package com.application.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-10-16.
 */
public class CartData {

    private ArrayList<DrinkData> arrCart;
    private int totalPrice;
    private int totalSavemoney;

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalSavemoney() {
        return totalSavemoney;
    }

    public CartData() {
        totalPrice = 0;
        totalSavemoney = 0;
    }

    public void addCart(DrinkData orderkData) {
        if(arrCart == null) {
            arrCart = new ArrayList<>();

        }

        DrinkData temp = new DrinkData();
        temp.setShot(orderkData.isShot());
        temp.setOrderPrice(orderkData.getOrderPrice());
        temp.setEa(orderkData.getEa());
        temp.setDrinkFlag(orderkData.getDrinkFlag());
        temp.setDrinkIdx(orderkData.getDrinkIdx());
        temp.setDrinkName(orderkData.getDrinkName());
        temp.setDrinkOption(orderkData.getDrinkOption());
        temp.setDrinkPrice(orderkData.getDrinkPrice());
        temp.setIsCheck(orderkData.isCheck());

        arrCart.add(temp);
        totalPrice += temp.getOrderPrice();
        totalSavemoney += temp.getOrderPrice() / 10;

    }

    public DrinkData getCart(int index) {
        if(arrCart == null) {
            arrCart = new ArrayList<>();
        }
        return arrCart.get(index);
    }

    public ArrayList<DrinkData> getAllCart() {
        if(arrCart == null) {
            arrCart = new ArrayList<>();
        }
        return arrCart;
    }

    public void removeCart( DrinkData orderkData) {
        if(arrCart != null) {
            int index = arrCart.indexOf(orderkData);
            if(index >= 0) {
                arrCart.remove(index);
                totalPrice -= orderkData.getOrderPrice();
                totalSavemoney -= orderkData.getOrderPrice() / 10;
            }
        }
    }

    public void removeAllCart() {

        totalPrice = 0;
        totalSavemoney = 0;

        if(arrCart != null) {
            arrCart.clear();
            arrCart = null;
        }
    }

    public int countCart() {

        if(arrCart == null)
            return 0;

        int count = arrCart.size();

        return count;
    }

}
