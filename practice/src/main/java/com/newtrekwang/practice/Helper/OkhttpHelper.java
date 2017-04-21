package com.newtrekwang.practice.Helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkhttpHelper {
    public static final int DEFAULTITMEOUT=5;
    private OkHttpClient okHttpClient;

    private OkhttpHelper(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .connectTimeout(DEFAULTITMEOUT, TimeUnit.SECONDS);
        okHttpClient=builder.build();
    }

    private static class OkhttpHelperHolder{
        public static OkhttpHelper INSTANCE=new OkhttpHelper();
    }

    public static OkHttpClient getOkhttpClient(){
        return OkhttpHelperHolder.INSTANCE.okHttpClient;
    }
}
