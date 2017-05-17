package com.roy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;

/**
 * Created by Roy on 2017/5/17.
 * SIM 卡变更发送短信
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String spSimNumber = SpUtil.getString(context, ConstantValue.SIM_NUMBER,"");
        //获得TelephonyManager的服务对象
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String simNumber = telephonyManager.getSimSerialNumber();

        if (simNumber.equals(spSimNumber)) {
            SmsManager smsManager = SmsManager.getDefault(); //获得默认的消息管理器

            //因为要发送信息去安全号码你的sim卡更改了
            String phone = SpUtil.getString(context,ConstantValue.CONTACT_NUMBER,"");

            smsManager.sendTextMessage(phone,null,"SIM CHANGE",null,null);
            Log.i("TAG","!!!!SIM CHANGE");

        }


    }
}
