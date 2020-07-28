package com.example.debasish.homeservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterInVistProfile extends BaseAdapter {
    Context context;
    List<UserInfo> userInfoList;
    public ArrayAdapterInVistProfile(Context context,List<UserInfo>userInfoList)
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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        row= layoutInflater.inflate(R.layout.row_of_visit_rofile_list,viewGroup,false);
        UserInfoHolder userInfoHolder=new UserInfoHolder();
        userInfoHolder.userInfo=userInfoList.get(i);
        userInfoHolder.textView1=row.findViewById(R.id.nameInVisitProfileListRow);
        userInfoHolder.textView2=(TextView)row.findViewById(R.id.emailInVisitProfileListRow);
        userInfoHolder.textView3=(TextView)row.findViewById(R.id.homeInVisitProfileListRow);
        userInfoHolder.textView4=(TextView)row.findViewById(R.id.phoneInVisitProfileListRow);
        row.setTag(userInfoHolder);
        userInfoHolder.textView1.setText(userInfoHolder.userInfo.getName());
        userInfoHolder.textView2.setText(userInfoHolder.userInfo.getEmail());
        userInfoHolder.textView3.setText(userInfoHolder.userInfo.getPassword());
        userInfoHolder.textView4.setText(userInfoHolder.userInfo.getPhoneNumber());
        return row;
    }
    public class UserInfoHolder  {
        UserInfo userInfo;
        TextView textView1,textView2,textView3,textView4;

    }
}
