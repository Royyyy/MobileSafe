package com.roy.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

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
