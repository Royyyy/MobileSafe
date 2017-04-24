package com.roy.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Roy on 2017/4/24.
 */
public class StreamUtil {

    /**
     *流转换成字符串
     * @return  流转换成的字符串  返回null代表异常
     * @param is  流对象
     */
    public static String streamToString(InputStream is) throws IOException {
        //1.在读取的过程中，将读取的内容存储值缓存中，然后一次性转换成字符串返回
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //2.读流操作，读到没有为止(循环)
        byte[] buffer = new byte[1024];
        //3.记录读取内容的临时变量
        int temp = -1;
        try {
            while((temp = is.read(buffer))!=-1){
                bos.write(buffer,0,temp);
            }
            //返回读取的数据
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bos.close();
            is.close();
        }
        return null;
    }
}
