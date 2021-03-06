package com.roy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Roy on 2017/6/12.
 */

public class AddressService extends Service {
    public static final String tag = "AddressService";
    private TelephonyManager mTM;
    private MyPhoneListener mPhoneStateListener;
    @Override
    public void onCreate() {
        //第一次开启服务以后,就需要去管理吐司的显示
        //电话状态的监听(服务开启的时候,需要去做监听,关闭的时候电话状态就不需要监听)
        //1,电话管理者对象
         mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //2,监听电话状态
        mPhoneStateListener = new MyPhoneListener();
        mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    //空闲状态,没有任何活动(移除吐司)
                    Log.i(tag, "挂断电话,空闲了.......................");

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //摘机状态，至少有个电话活动。该活动或是拨打（dialing）或是通话
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //响铃(展示吐司)
                    Log.i(tag, "响铃了.......................");
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
    @Override
    public void onDestroy() {
        //取消对电话状体的监听
        if (mTM != null && mPhoneStateListener != null) {
            mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        super.onDestroy();
    }
}
