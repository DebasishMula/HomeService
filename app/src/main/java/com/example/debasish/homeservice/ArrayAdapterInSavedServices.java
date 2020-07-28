package com.example.debasish.homeservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterInSavedServices extends BaseAdapter{
    Context context;
    List<HiringServicemanInfo> HiringServicemanInfoList;
    public ArrayAdapterInSavedServices(Context context,List<HiringServicemanInfo> HiringServicemanInfoList)
    {
        this.context=context;
        this.HiringServicemanInfoList=HiringServicemanInfoList;
    }
    @Override
    public int getCount() {
        return HiringServicemanInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return HiringServicemanInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        row= layoutInflater.inflate(R.layout.row_of_saved_services,viewGroup,false);
        HiringServicemanInfoHolder hiringServicemanInfoHolder=new HiringServicemanInfoHolder();
        hiringServicemanInfoHolder.hiringServicemanInfo=HiringServicemanInfoList.get(i);
        hiringServicemanInfoHolder.textViewLocation=row.findViewById(R.id.SavedServiceLocationInSavedServiceLIst);
        hiringServicemanInfoHolder.textViewDate=row.findViewById(R.id.SavedServiceDateInSavedServiceLIst);
        row.setTag(hiringServicemanInfoHolder);
        hiringServicemanInfoHolder.textViewLocation.setText(hiringServicemanInfoHolder.hiringServicemanInfo.getWorking_address());
        hiringServicemanInfoHolder.textViewDate.setText(hiringServicemanInfoHolder.hiringServicemanInfo.getWorking_date());
        return row;
    }
    public class HiringServicemanInfoHolder
    {
        HiringServicemanInfo hiringServicemanInfo;
        TextView textViewDate,textViewLocation;

    }
}
