package com.roy.mobilesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Roy on 2017/5/16.
 */

public abstract class BaseSetupActivity extends Activity {
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2,创建手势管理的对象,用作管理在onTouchEvent(event)传递过来的手势动作
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //监听手势的移动
                //调用子类的下一页方法,抽象方法
                if (e1.getX()-e2.getX()>0){
                    showNextPage();
                }else if (e1.getX()-e2.getX()<0) {
                    //调用子类的上一页方法
                    showPrePage();
                }
                return super.onFling(e1,e2,velocityX,velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //下一页的抽象方法,由子类决定具体跳转到那个界面
    protected abstract void showNextPage();
    //上一页的抽象方法,由子类决定具体跳转到那个界面
    protected abstract void showPrePage();

    //点击下一页按钮的时候,根据子类的showNextPage方法做相应跳转
    public void nextPage(View view){
        showNextPage();
    }
    //点击上一页按钮的时候,根据子类的showPrePage方法做相应跳转
    public void prePage(View view){
        showPrePage();
    }
}
