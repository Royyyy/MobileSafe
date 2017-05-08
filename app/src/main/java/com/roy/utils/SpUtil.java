package com.roy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Roy on 2017/5/2.
 */
public class SpUtil {
    private static SharedPreferences sp;

    //读

    /**
     *
     * @param context   上下文环境
     * @param key       存储节点名称
     * @param value     存储节点的值boolean
     */
    public static void putBoolean(Context context,String key,boolean value){
        //存储节点文件名称，读写方式
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }
    public static void putString(Context context,String key,String value){
        //存储节点文件名称，读写方式
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }
    //写
    /**
     *
     * @param context       上下文环境
     * @param key           存储节点名称
     * @param defValue      没有此节点默认值
     * @return              默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        //存储节点文件名称，读写方式
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }
    public static String getString(Context context, String key, String defValue){
        //存储节点文件名称，读写方式
        if (sp == null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }
}
