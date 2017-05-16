package com.roy.mobilesafe;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private ListView lv_contact;
    private List<HashMap<String,String>> contactList = new ArrayList<HashMap<String, String>>();
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter = new MyAdapter();
            lv_contact.setAdapter(mAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initUI();
        initData();
    }
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.list_contact_item,null);

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            tv_name.setText(getItem(position).get("name"));
            tv_phone.setText(getItem(position).get("phone"));
            return view;
        }
    }
    private void initData() {
        //因为读取联系人电话是耗时操作，所以要放在子线程里面进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                //开启、游标
                Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),new String[]{"contact_id"},null,null,null);
                //循环游标，直到没有为止
                contactList.clear();
                while (cursor.moveToNext()){
                    String id = cursor.getString(0);
                    Cursor indexCursor = contentResolver.query(Uri.parse("content://com.android.contacts/data"),new String[]{"data1","mimetype"},"raw_contact_id = ?",new String[]{id},null);
                    //循环获取的每一个联系人及姓名，数据类型
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    while (indexCursor.moveToNext()){
                        String data = indexCursor.getString(0);
                        String type = indexCursor.getString(1);
                        if (type.equals("vnd.android.cursor.item/phone_v2")){
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("phone", data);
                            }
                        }else if (type.equals("vnd.android.cursor.item/name")){
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("name", data);
                            }
                        }
                    }
                    indexCursor.close();
                    contactList.add(hashMap);
                }
                cursor.close();
                //消息机制

                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点中条目中的集合的对象
                if (mAdapter != null) {
                    HashMap<String, String> hashMap = mAdapter.getItem(position);
                    //获取当前集合的电话号码
                    String phone = hashMap.get("phone");
                    //把得到的电话号码传回给第三个页面用
                    //在此结束返回到上一个界面的时候，需要把数据传回去
                    Intent intent = new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }
}
