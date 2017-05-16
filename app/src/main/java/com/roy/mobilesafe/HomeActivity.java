package com.roy.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.roy.utils.ConstantValue;
import com.roy.utils.Md5Util;
import com.roy.utils.SpUtil;
import com.roy.utils.ToastUtil;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //初始化UI
        initUI();
        //初始化数据
        initData();
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showDialog();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;


                }
            }
        });
    }

    private void showDialog() {
        //先判断本地是否有储存密码sp
        String psd = SpUtil.getString(this,ConstantValue.MOBILT_SAFE_PSD,"");
        if (TextUtils.isEmpty(psd)){
            showSetPsdDialog();
        }else{
            showConfirmPsdDialog();
        }
    }

    private void showConfirmPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(this,R.layout.dialog_confirm_psd,null);
        //这里为了低版本的兼容，所以用了5个参数的setView方法，分别设置view的内边距
        dialog.setView(view,0,0,0,0);
        dialog.show();
        Button bt_submit = (Button)view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button)view.findViewById(R.id.bt_cancel);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确认按钮
                EditText firstpsd = (EditText) view.findViewById(R.id.firstpsd);
                String firstPsd = firstpsd.getText().toString();
                if (!TextUtils.isEmpty(firstPsd)){
                    //进行将存储在sp中32位的密码获取出来，然后再与输入密码做对比
                   String twicePsd = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILT_SAFE_PSD,"");
                    if (twicePsd.equals(Md5Util.encoder(firstPsd))){
                        Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
                        startActivity(intent);
                        //防止跳转成功以后点击返回，会继续跳到输入框，所以这里要用到dismiss
                        dialog.dismiss();
                    }else{
                        ToastUtil.show(getApplicationContext(),"密码错误");
                    }
                }else{
                    ToastUtil.show(getApplicationContext(),"密码输入不能为空");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮
                dialog.dismiss();
            }
        });
    }


    private void showSetPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(this,R.layout.dialog_set_psd,null);
        dialog.setView(view);
        dialog.show();
        Button bt_submit = (Button)view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button)view.findViewById(R.id.bt_cancel);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确认按钮
                EditText firstpsd = (EditText) view.findViewById(R.id.firstpsd);
                EditText twicepsd = (EditText) view.findViewById(R.id.twicepsd);
                String firstPsd = firstpsd.getText().toString();
                String twicePsd = twicepsd.getText().toString();
                if (!TextUtils.isEmpty(firstPsd) && !TextUtils.isEmpty(twicePsd)){
                    //进行密码判断
                    if (firstPsd.equals(twicePsd)){
                        Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
                        startActivity(intent);
                        //防止跳转成功以后点击返回，会继续跳到输入框，所以这里要用到dismiss
                        dialog.dismiss();
                        SpUtil.putString(getApplicationContext(),ConstantValue.MOBILT_SAFE_PSD, Md5Util.encoder(firstPsd));
                    }else{
                        ToastUtil.show(getApplicationContext(),"两次密码不一致");
                    }
                }else{
                    ToastUtil.show(getApplicationContext(),"密码输入不能为空");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮
                dialog.dismiss();
            }
        });
    }


    private void initData() {
        //准备数据 文字9组，图片9张
        mTitleStr = new String[]{
                "手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
        };
        mImageId = new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,
                R.drawable.home_apps,R.drawable.home_taskmanager,
                R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,
                R.drawable.home_settings
        };

        //九宫格控件设置数据适配器(等同listView数据适配器)
        gv_home.setAdapter(new MyAdapter());
    }

    private void initUI() {
        gv_home = (GridView)findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //条目的总数，用文字或者图片id
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            TextView tv_title = (TextView)view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

            tv_title.setText(mTitleStr[position]);
            iv_icon.setBackgroundResource(mImageId[position]);
            return view;
        }
    }
}
