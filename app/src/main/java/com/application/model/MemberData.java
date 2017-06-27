package com.application.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015-09-23.
 */
public class MemberData {

    private Context mContext;
    private String member_idx;
    private String member_device_id;
    private String member_email;
    private String member_name;
    private String member_phone;
    private String member_pass;
    private String member_point;
    private String member_rank;

    public MemberData(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    // 로그인 데이터 저장
    public void saveLoginData( String ParamID, String ParamPASS){
        SharedPreferences pref = mContext.getSharedPreferences("member_info", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("member_email", ParamID);
        editor.putString("member_pass", ParamPASS);
        editor.commit();
    }

    public String[] loadLoginData(  ){
        String[] temp = null;
        SharedPreferences pref =  mContext.getSharedPreferences("member_info", mContext.MODE_PRIVATE);
        temp = new String[3];
        temp[0] = pref.getString("member_email", "");
        temp[1] = pref.getString("member_pass", "");
        return temp;
    }

    public void removeLoginData() {

        SharedPreferences pref = mContext.getSharedPreferences("member_info", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

    }


    public String getMember_idx() {
        return member_idx;
    }

    public void setMember_idx(String member_idx) {
        this.member_idx = member_idx;
    }

    public String getMember_device_id() {
        return member_device_id;
    }

    public void setMember_device_id(String member_device_id) {
        this.member_device_id = member_device_id;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_pass() {
        return member_pass;
    }

    public void setMember_pass(String member_pass) {
        this.member_pass = member_pass;
    }

    public String getMember_point() {
        return member_point;
    }

    public void setMember_point(String member_point) {
        this.member_point = member_point;
    }

    public String getMember_rank() {
        return member_rank;
    }

    public void setMember_rank(String member_rank) {
        this.member_rank = member_rank;
    }
}
