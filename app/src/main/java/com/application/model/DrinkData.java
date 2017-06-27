package com.application.model;

/**
 * Created by Administrator on 2015-10-12.
 */
public class DrinkData {


    private int drinkIdx;
    private int drinkFlag;
    private String drinkName;
    private int drinkPrice;
    private int orderPrice;
    private boolean shot;
    private int ea;
    private String drinkOption;
    private boolean isCheck;

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public int getEa() {
        return ea;
    }

    public void setEa(int ea) {
        this.ea = ea;
    }

    public String getDrinkOption() {
        return drinkOption;
    }

    public void setDrinkOption(String drinkOption) {
        this.drinkOption = drinkOption;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public int getDrinkIdx() {
        return drinkIdx;
    }

    public void setDrinkIdx(int drinkIdx) {
        this.drinkIdx = drinkIdx;
    }

    public int getDrinkFlag() {
        return drinkFlag;
    }

    public void setDrinkFlag(int drinkFlag) {
        this.drinkFlag = drinkFlag;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public int getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(int drinkPrice) {
        this.drinkPrice = drinkPrice;
    }


}
