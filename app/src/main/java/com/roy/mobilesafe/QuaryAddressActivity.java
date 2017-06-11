package com.roy.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
