package com.roy.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;

public class SetupOverActivity extends AppCompatActivity {
    private TextView tv_safe_number;
    private TextView tv_reset_setup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER,false);
        if (setup_over) {
            setContentView(R.layout.activity_setup_over);
            initUI();
        }else{
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        }
        }

    private void initUI() {
        tv_safe_number = (TextView) findViewById(R.id.tv_safe_number);
        tv_safe_number.setText(SpUtil.getString(this,ConstantValue.CONTACT_NUMBER,""));

        tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupOverActivity.this,Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
