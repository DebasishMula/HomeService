package com.example.debasish.homeservice;

import android.widget.Adapter;
import java.io.Serializable;
@SuppressWarnings("serial")
public class UserInfo implements Serializable {
    private int id;
   private String name,email,phoneNumber,homeTown,password,skill1,skill2,skill3,skill4,skill5,skill6,skill7,search_skill;

   public UserInfo()
   {

   }

    public UserInfo(String name, String email, String phoneNumber, String homeTown, String password, String skill1, String skill2, String skill3, String skill4, String skill5, String skill6, String skill7) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homeTown = homeTown;
        this.password = password;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.skill5 = skill5;
        this.skill6 = skill6;
        this.skill7 = skill7;
    }

    public UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public UserInfo(String name,String email,String phoneNumber,String password)
    {
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.password=password;
    }


    public UserInfo( int id,String name,String email,String phoneNumber,String homeTown,String skill1,String skill2,String skill3,String skill4,String skill5,String skill6,String skill7,String search_skill)
    {
        this.id=id;
        this.name=name;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.homeTown=homeTown;
        this.skill1=skill1;
        this.skill2=skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.skill5 = skill5;
        this.skill6 = skill6;
        this.skill7 = skill7;
        this.search_skill=search_skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public String getSkill4() {
        return skill4;
    }

    public void setSkill4(String skill4) {
        this.skill4 = skill4;
    }

    public String getSkill5() {
        return skill5;
    }

    public void setSkill5(String skill5) {
        this.skill5 = skill5;
    }

    public String getSkill6() {
        return skill6;
    }

    public void setSkill6(String skill6) {
        this.skill6 = skill6;
    }

    public String getSkill7() {
        return skill7;
    }

    public void setSkill7(String skill7) {
        this.skill7 = skill7;
    }

    public String getId() {
        return  String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearch_skill() {
        return search_skill;
    }

    public void setSearch_skill(String search_skill) {
        this.search_skill = search_skill;
    }
}
