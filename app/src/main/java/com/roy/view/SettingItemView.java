package com.roy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roy.mobilesafe.R;

/**
 * Created by Roy on 2017/5/1.
 */
public class SettingItemView extends RelativeLayout {
    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml-->view  将设置界面的一个条目转换成view对象，直接添加到了当前SettingItemView对应的view中
        View.inflate(context, R.layout.setting_item_view,this);

        //自定义组合控件中的标题描述
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        TextView tv_des = (TextView)findViewById(R.id.tv_des);
        CheckBox cb_box = (CheckBox)findViewById(R.id.cb_box);
    }
}
