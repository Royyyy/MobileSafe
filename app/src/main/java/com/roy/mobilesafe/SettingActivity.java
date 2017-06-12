package com.roy.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;

import com.roy.service.AddressService;
import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;
import com.roy.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {
    private SettingItemView siv_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
        iniAddress();
    }

    /**
     * 是否显示电话归属地
     */
    private void iniAddress() {
        siv_address = (SettingItemView) findViewById(R.id.siv_address);
        boolean address_service = SpUtil.getBoolean(this,ConstantValue.ADDRESS_SERVICE,false);
        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回点击前的选中状态
                boolean isCheck = siv_address.isCheck();
                siv_address.setCheck(!isCheck);
                if(!isCheck){
                    //开启服务,管理吐司
                    startService(new Intent(getApplicationContext(),AddressService.class));
                }else{
                    //关闭服务,不需要显示吐司
                    stopService(new Intent(getApplicationContext(),AddressService.class));
                }
            }
        });
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
