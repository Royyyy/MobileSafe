package com.roy.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roy.engine.AddressDao;

import org.w3c.dom.Text;

public class QuaryAddressActivity extends Activity {
    private static TextView tv_quert_result;
    private static EditText et_phone;
    private static Button bt_query;
    private static String mAddress;
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_quert_result.setText(mAddress);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quary_address);

        initUI();

    }

    private void initUI() {
        et_phone = (EditText)findViewById(R.id.et_phone);
        bt_query = (Button)findViewById(R.id.bt_query);
        tv_quert_result = (TextView)findViewById(R.id.tv_query_result);

        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = et_phone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    query(phone);
                }else{
                    //抖动
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    et_phone.startAnimation(shake);

                    //手机震动效果
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    //震动毫秒
                    vibrator.vibrate(2000);

                    //规律震动  震动规则((不震动时间，震动时间)，重复次数)
                    vibrator.vibrate(new long[]{2000,5000,2000,5000},-1);
                }
            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = et_phone.getText().toString();
                query(phone);
            }
        });
    }
    protected void query(final String phone) {
        new Thread(){
            @Override
            public void run() {
                mAddress = AddressDao.getAddress(phone);
                //消息机制
                mHandle.sendEmptyMessage(0);
            }
        }.start();
    }
}
