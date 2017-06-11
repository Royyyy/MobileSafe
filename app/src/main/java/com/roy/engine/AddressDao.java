package com.roy.engine;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Roy on 2017/6/11.
 */

public class AddressDao {
    private static final String tag = "AddressDao";
    private static String mAddress = "未知号码";

    // 指定访问数据库的路径
    public static String path = "data/data/com.roy.mobilesafe/files/address.db";

    /**
     * 查询电话号码，传递一个电话号码，开启数据库进行访问，返回一个归属地
     * @param phone
     * @return
     */
    public final static String getAddress(String phone) {
        mAddress = "未知号码";
        String regularExpression = "^1[3-8]\\d{9}";
        //2.开启数据库连线（只读形式）
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        if (phone.matches(regularExpression)) {
            phone = phone.substring(0, 7);

            //3.库查询
            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id = ?", new String[]{phone}, null, null, null);
            if (cursor.moveToNext()) {
                String outkey = cursor.getString(0);
                Cursor indexCursor = db.query("data2", new String[]{"location"}, "id = ?", new String[]{outkey}, null, null, null);
                if (indexCursor.moveToNext()) {
                    mAddress = indexCursor.getString(0);
                }
            }else{
                mAddress = "未知号码";
            }
        } else {
            int length = phone.length();
            switch (length){
                case 3:
                    mAddress = " 报警电话";
                    break;
                case 4:
                    mAddress = " 模拟器电话";
                    break;
                case 5:
                    mAddress = " 服务电话";
                    break;
                case 7:
                    mAddress = " 固话";
                    break;
                case 8:
                    mAddress = " 固话";
                    break;
                case 11:
                    //(3+8)
                    String area1 = phone.substring(1,3);
                    Cursor cursor1 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area1}, null, null, null);
                    if (cursor1.moveToNext()){
                        mAddress = cursor1.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;
                case 12:
                    //(4+8)
                    String area2 = phone.substring(1,4);
                    Cursor cursor2 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area2}, null, null, null);
                    if (cursor2.moveToNext()){
                        mAddress = cursor2.getString(0);
                    }else{
                        mAddress = "未知号码";
                    }
                    break;
            }
        }
        return mAddress;
    }
}
