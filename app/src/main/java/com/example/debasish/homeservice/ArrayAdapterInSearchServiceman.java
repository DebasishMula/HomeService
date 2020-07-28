package com.example.debasish.homeservice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterInSearchServiceman extends BaseAdapter {
    Context context;
    List<UserInfo> userInfoList;


    public ArrayAdapterInSearchServiceman(Context context,List<UserInfo>userInfoList)
    {
        this.context=context;
        this.userInfoList=userInfoList;
    }


    @Override
    public int getCount() {
       return userInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return userInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View row=view;
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        row= layoutInflater.inflate(R.layout.row_of_result_list_after_seraching_serviceman,viewGroup,false);
        UserInfoHolder userInfoHolder=new UserInfoHolder();
        userInfoHolder.userInfo=userInfoList.get(i);
        userInfoHolder.textView1=row.findViewById(R.id.tvUserName1);
        userInfoHolder.textView2=(TextView)row.findViewById(R.id.tvUserSkill1);
        userInfoHolder.textView3=(TextView)row.findViewById(R.id.tvUserPlace1);
        row.setTag(userInfoHolder);

        userInfoHolder.textView1.setText(userInfoHolder.userInfo.getName());
        userInfoHolder.textView2.setText(userInfoHolder.userInfo.getSearch_skill());
        userInfoHolder.textView3.setText(userInfoHolder.userInfo.getHomeTown());
        if (i % 2 == 0) {
            row.setBackgroundColor(Color.rgb(213, 229, 241));
        } else {
            row.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        return row;
    }


    public class UserInfoHolder  {
         UserInfo userInfo;
         TextView textView1,textView2,textView3;

    }
}
