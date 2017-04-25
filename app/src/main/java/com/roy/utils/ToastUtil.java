package com.roy.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Roy on 2017/4/25.
 */
public class ToastUtil {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }
}
