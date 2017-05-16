package com.roy.mobilesafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;
import com.roy.utils.ToastUtil;
import com.roy.view.SettingItemView;

public class Setup2Activity extends AppCompatActivity {
    private SettingItemView siv_bound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        initUi();
    }

    private void initUi() {

        siv_bound = (SettingItemView) findViewById(R.id.siv_bound);
        //回显(读取已有的绑定状态,用作显示,sp中是否存储了sim卡的序列号)
        String sim_number = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER,null);
        //2,判断是否序列卡号为""
        if (TextUtils.isEmpty(sim_number)){
            siv_bound.setCheck(false);
        }else{
            siv_bound.setCheck(true);
        }

        siv_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3,获取原有的状态
                boolean isCheck = siv_bound.isCheck();
                //4,将原有状态取反
                //5,状态设置给当前条目
                siv_bound.setCheck(!isCheck);
                if (!isCheck) {
                    //6,存储(序列卡号)
                    //6.1获取sim卡序列号TelephoneManager
                    TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    //6.2获取sim卡的序列卡号
                    String simSerialNumber = manager.getSimSerialNumber();
                    //6.3存储
                    SpUtil.putString(getApplicationContext(),ConstantValue.SIM_NUMBER,simSerialNumber);
                } else {
                    //7,将存储序列卡号的节点,从sp中删除掉
                    SpUtil.remove(getApplicationContext(),ConstantValue.SIM_NUMBER);

                }
            }
        });


    }
    public void nextPage(View view){
        String simSerialNumber = SpUtil.getString(this,ConstantValue.SIM_NUMBER,"");
        if (!TextUtils.isEmpty(simSerialNumber)) {
            Intent intent = new Intent(this, Setup3Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
        }else{
            ToastUtil.show(this,"请绑定sim卡");
        }
    }
    public void previousPage(View view){
        Intent intent = new Intent(this,Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}
