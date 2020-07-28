package com.example.debasish.homeservice;

import java.io.Serializable;

public class HiringServicemanInfo implements Serializable {
    private String  user_id,user_name,user_home_town,user_phone_number,working_date,working_address,working_descrip;
    private String hiring_serviceman_id;

    public HiringServicemanInfo(String hiring_serviceman_id,String user_id,String user_name, String user_phone_number,String user_home_town, String working_date, String working_address, String working_descrip) {
        this.hiring_serviceman_id=hiring_serviceman_id;
        this.user_id=user_id;
        this.user_name = user_name;
        this.user_phone_number=user_phone_number;
        this.user_home_town = user_home_town;
        this.working_date = working_date;
        this.working_address = working_address;
        this.working_descrip = working_descrip;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_home_town() {
        return user_home_town;
    }

    public void setUser_home_town(String user_home_town) {
        this.user_home_town = user_home_town;
    }

    public String getWorking_date() {
        return working_date;
    }

    public void setWorking_date(String working_date) {
        this.working_date = working_date;
    }

    public String getWorking_address() {
        return working_address;
    }

    public void setWorking_address(String working_address) {
        this.working_address = working_address;
    }

    public String getWorking_descrip() {
        return working_descrip;
    }

    public void setWorking_descrip(String working_descrip) {
        this.working_descrip = working_descrip;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getHiring_serviceman_id() {
        return hiring_serviceman_id;
    }

    public void setHiring_serviceman_id(String hiring_serviceman_id) {
        this.hiring_serviceman_id = hiring_serviceman_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
