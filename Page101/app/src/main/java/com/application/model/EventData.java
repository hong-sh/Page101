package com.application.model;

/**
 * Created by Hong on 2015-10-30.
 */
public class EventData {

    private int event_idx;
    private String event_title;
    private String event_start_date;
    private String event_end_date;
    private String event_image;

    public int getEvent_idx() {
        return event_idx;
    }

    public void setEvent_idx(int event_idx) {
        this.event_idx = event_idx;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_start_date() {
        return event_start_date;
    }

    public void setEvent_start_date(String event_start_date) {
        this.event_start_date = event_start_date;
    }

    public String getEvent_end_date() {
        return event_end_date;
    }

    public void setEvent_end_date(String event_finish_date) {
        this.event_end_date = event_finish_date;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }
}
