package com.roy.mobilesafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;
import com.roy.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView)findViewById(R.id.siv_update);

        //获取已有开关状态
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE,false);
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前checkbox状态
                boolean isCheck = siv_update.isCheck();
                //将当前状态取反然后设置
                siv_update.setCheck(!isCheck);

                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!isCheck);
            }
        });
    }
}
