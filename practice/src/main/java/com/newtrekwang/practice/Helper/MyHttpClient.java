package com.newtrekwang.practice.Helper;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WJX .
 * Desc: 单例，http请求过程封装
 * Created on 2017/1/15 15:35.
 * Mail:408030208@qq.com
 */

public class MyHttpClient {
    public static  final String BASE_URL="http://s.budejie.com/";
    private static  final int DEFAULT_TIMEOUT=5;

    private Retrofit retrofit;
    private ApiStores apiStores;
    /**
     * 构造方法私有
     */
    private MyHttpClient(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        builder.addInterceptor(new LogInterceptor());
        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiStores=retrofit.create(ApiStores.class);
    }

    private static class SingletonHolder{
        private static  final  MyHttpClient INSTANCE=new MyHttpClient();
    }

    public static  MyHttpClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public static  ApiStores getApi(){
        return SingletonHolder.INSTANCE.apiStores;
    }


    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Logger.d(request.toString()+request.headers().toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            if (!TextUtils.isEmpty(content)){
            Logger.json(content);
            }
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }


}
