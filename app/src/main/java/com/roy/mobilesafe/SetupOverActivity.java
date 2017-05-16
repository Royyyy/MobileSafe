package com.roy.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;

public class SetupOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER,false);
        if (setup_over) {
            setContentView(R.layout.activity_setup_over);
        }else{
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        }
        }
}
