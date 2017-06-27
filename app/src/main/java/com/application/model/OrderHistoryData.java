package com.application.model;

/**
 * Created by Hong on 2015-10-30.
 */
public class OrderHistoryData {

    private String first_menu;
    private String amount;
    private String order_total_price;
    private String order_date;

    public String getFirst_menu() {
        return first_menu;
    }

    public void setFirst_menu(String first_menu) {
        this.first_menu = first_menu;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(String order_total_price) {
        this.order_total_price = order_total_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
