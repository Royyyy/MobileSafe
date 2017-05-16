package com.roy.mobilesafe;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.roy.utils.ConstantValue;
import com.roy.utils.SpUtil;
import com.roy.utils.ToastUtil;

public class Setup3Activity extends AppCompatActivity {
    private EditText et_phone_number;
    private Button select_phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    private void initUI() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        String phone = SpUtil.getString(this,ConstantValue.CONTACT_NUMBER,"");
        et_phone_number.setText(phone);
        select_phone_number = (Button) findViewById(R.id.select_phone_number);

        select_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setup3Activity.this,ContactListActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            String phone = data.getStringExtra("phone");
            //将特殊字符过滤掉
            phone = phone.replace("-", "").replace(" ", "").trim();
            et_phone_number.setText(phone);
            //将选择联系人保存到sp中
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_NUMBER,phone);
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void nextPage(View view){
        String phone = et_phone_number.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(this, Setup4Activity.class);
            startActivity(intent);
            finish();
            //如果是输入电话，就储存
            SpUtil.putString(getApplicationContext(),ConstantValue.CONTACT_NUMBER,phone);
            overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
        }else{
            ToastUtil.show(this," 请输入联系人电话");
        }
    }
    public void previousPage(View view){
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}
