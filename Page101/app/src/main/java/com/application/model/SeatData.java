package com.application.model;

/**
 * Created by Hong on 2015-10-30.
 */
public class SeatData {

    private int seat_idx;
    private String seat_occupy_check;

    public int getSeat_idx() {
        return seat_idx;
    }

    public void setSeat_idx(int seat_idx) {
        this.seat_idx = seat_idx;
    }

    public String getSeat_occupy_check() {
        return seat_occupy_check;
    }

    public void setSeat_occupy_check(String seat_occupy_check) {
        this.seat_occupy_check = seat_occupy_check;
    }
}
