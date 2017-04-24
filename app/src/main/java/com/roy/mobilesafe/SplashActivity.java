package com.roy.mobilesafe;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.roy.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    protected static final String tag = "SplashActivity";
    private TextView textView;
    private  int mLocalVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化UI
        initUi();
        //初始化数据
        initData();
    }

    /**
     * 初始化数据方法
     */
    private void initData() {
        //1.应用版本名称
        textView.setText("版本的名称；"+getVersionName());
        //检测(本地版本号和服务器版本号对比)是否有更新，如果有更新，提示用户下载(member)
        //2.获取本地版本号
        mLocalVersionCode = getVersionCode();
        //3.获取服务器版本号(客户端发请求，服务器给相应(Json,xml))
        //http://www.oxxx.com/update.json?key=value 返回200请求成功，流的方式将数据读取下来
        //Json中内容包括
        /*
        更新版本的版本名称
        新版本的描述信息
        服务器版本号
        新版本apk下载地址
         */
        checkVersion();
    }

    /**
     * 检测版本号
     */
    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送请求获取数据，参数则为请求json的连接地址
                //http://localhost:8080/uiodate.json 测试阶段，不是最优
               //10.0.2.2  仅限与模拟器访问电脑tomcat
                try {
                    //1.封装url地址
                    URL url = new URL("http://10.0.2.2:8080/uiodate.json");
                    //2.开启一个连接
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    //3.设置常见请求参数(请求头)
                    //连接超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);

                    //默认就是get请求方式
                    //connection.setRequestMethod("POST");

                    //4.获取请求成功响应码
                    if (connection.getResponseCode() == 200){
                        //5.以流的形式，将数据获取下来
                        InputStream is = connection.getInputStream();
                        //6.将流转换成字符串(工具封装类)
                        String json = StreamUtil.streamToString(is);
                        Log.i(tag,json);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 初始化UI方法
     */
    private void initUi() {
        textView = (TextView) findViewById(R.id.tv_version_name);

    }



    /**
     * 获取版本名称：清单文件中
     * @return
     */
    private String getVersionName() {
        //1.包管理者对象packageManager
        PackageManager packageManager = getPackageManager();
        //2.从包的管理者对象中,获取指定包名的基本信息(版本名称，版本号)，传0代表获取基本信息
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 返回版本号
     * @return
     * 非0则证明返回成功
     */
    public int getVersionCode() {
        //1.包管理者对象packageManager
        PackageManager packageManager = getPackageManager();
        //2.从包的管理者对象中,获取指定包名的基本信息(版本名称，版本号)，传0代表获取基本信息
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
