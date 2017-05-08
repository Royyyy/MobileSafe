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
    public static final String NAMESPACE = "http://schemas.android.com/apk/res/com.roy.mobilesafe";
    private  CheckBox cb_box;
    private  TextView tv_des;
    private  String mDestitle;
    private  String mDesoff;
    private  String mDeson;
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
        tv_des = (TextView)findViewById(R.id.tv_des);
        cb_box = (CheckBox)findViewById(R.id.cb_box);
        initAttrs(attrs);
        
//        setCheck(true);
        tv_title.setText(mDestitle);

    }

    private void initAttrs(AttributeSet attrs) {

        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");

        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");

        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
    }

    /**
     * 判断是否开启
     * @return
     */
    public boolean isCheck() {
        return cb_box.isChecked();
    }

    /**
     * 切换开启变量，由点击过程中去做传递
     * @param isCheck
     */
    public void setCheck(boolean isCheck) {
        cb_box.setChecked(isCheck);
        if (isCheck){
            tv_des.setText(mDeson);
        }else{
            tv_des.setText(mDesoff);
        }
    }
}
