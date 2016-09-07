package com.feicui.contacts.android8_30.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicui.contacts.android8_30.R;
import com.feicui.contacts.android8_30.entity.PhoneNumberEnerty;

import java.util.ArrayList;

public class PhoneNumberAdapter extends BaseAdapter {
    //待装载数据，为电话号码列表适配器
    ArrayList<PhoneNumberEnerty> mData;
    //上下文
    Context mcontext;

    /**
     * @description 本适配器构造器，传入电话号码列表的实体类以装载数据
     * @param context  载入的数据源
     * @param data  上下文
     */
    public PhoneNumberAdapter(Context context, ArrayList<PhoneNumberEnerty> data){
        mData = data;
        mcontext = context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //声明ViewHolder
        PhoneNumberAdapter.ViewHolder holder = null;
        //若为第一次显示当前item
        if (convertView == null){
            //获取布局加载器
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            //使用布局加载器加载item布局
            convertView = inflater.inflate(R.layout.item_phone_number_list,null);
            //保存ViewHolder的状态，存在当前convertView中
            holder = new PhoneNumberAdapter.ViewHolder();
            holder.tv_phoneName = (TextView) convertView.findViewById(R.id.tv_phoneName);
            holder.tv_phoneNumber = (TextView) convertView.findViewById(R.id.tv_phoneNumber);
            convertView.setTag(holder);
        }
        else{
            holder = (PhoneNumberAdapter.ViewHolder)convertView.getTag();
        }
        //从数据源中装载数据
        holder.tv_phoneName.setText(mData.get(position).getPhoneName());
        holder.tv_phoneNumber.setText(mData.get(position).getPhoneNumber());
        return convertView;
    }
    /**
     * 用于优化性能的ViewHolder，保存Item布局中的控件
     */
    public class ViewHolder{
        public TextView tv_phoneName;//电话名称
        public TextView tv_phoneNumber;//电话号码
    }
}
