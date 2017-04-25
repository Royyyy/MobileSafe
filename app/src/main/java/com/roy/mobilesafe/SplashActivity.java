package com.roy.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.roy.utils.StreamUtil;
import com.roy.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    protected static final String tag = "SplashActivity";

    /**
     * 更新新版本的状态码
     */
    protected static final int UPDATE_VERSION = 100;
    /**
     * 进入应用主界面的状态码
     */
    protected static final int ENTER_HOME = 101;
    /**
     *一下是异常的状态码
     */
    protected static final int ERROR_URL = 102;
    protected static final int ERROR_IO = 103;
    protected static final int ERROR_JSON = 104;
    private String mVersionDes;
    private TextView textView;
    private int mLocalVersionCode;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VERSION:
                    //弹出对话框
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case ERROR_URL:
                    ToastUtil.show(getApplicationContext(),"URL异常");
                    enterHome();
                    break;
                case ERROR_IO:
                    ToastUtil.show(getApplicationContext(),"URL异常");
                    enterHome();
                    break;
                case ERROR_JSON:
                    ToastUtil.show(getApplicationContext(),"URL异常");
                    enterHome();
                    break;
            }
        }
    };

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置弹出框的logo，标题，内容，还有两个按键
        builder.setIcon(R.drawable.home_apps);
        builder.setTitle("更新内容");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //立即更新
            }
        });

        builder.setNegativeButton("稍后再说",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消对话框
                enterHome();
            }
        });
        builder.show();

    }

    private void enterHome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

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
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    //1.封装url地址
                    URL url = new URL("http://10.0.2.2:8080/update.json");
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
                        //7.解析Json
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        String downloadUrl = jsonObject.getString("downloadUrl");

                        Log.i(tag,versionName);
                        Log.i(tag,mVersionDes);
                        Log.i(tag,versionCode);
                        Log.i(tag,downloadUrl);

                        //8,对比版本号
                        if (mLocalVersionCode < Integer.parseInt(versionCode)){
                            //提示用户更新
                            message.what = UPDATE_VERSION;
                        }else{
                            //进入主程序
                            message.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what = ERROR_URL;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = ERROR_IO;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = ERROR_JSON;
                }finally {
                    //指定睡眠时间，请求网络的时长超过4秒则不做处理
                    //请求网络的时长少于4秒，让其睡眠满4秒钟
                    long endTime = System.currentTimeMillis();
                    if (endTime-startTime<4000){
                        try {
                            Thread.sleep(4000-(endTime-startTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(message);
                }
            }
        }).start();
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
