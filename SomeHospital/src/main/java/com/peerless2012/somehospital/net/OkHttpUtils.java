package com.peerless2012.somehospital.net;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/3 22:56
 * @Version V1.0
 * @Description
 */
public class OkHttpUtils {

    private OkHttpClient mOkHttpClient;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    private static volatile OkHttpUtils sInst = null;  // <<< 这里添加了 volatile  

    public static OkHttpUtils getInstance() {
        OkHttpUtils inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (OkHttpUtils.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new OkHttpUtils();
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }
}


