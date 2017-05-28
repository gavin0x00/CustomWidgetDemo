package com.newtrekwang.customwidgetdemo.httphelper;

import java.io.IOException;

import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody_1 extends okhttp3.ResponseBody{
    private okhttp3.ResponseBody responseBody;
    private  ProgressListener progressListener;
    private BufferedSource bufferedSource;

public ProgressResponseBody_1(okhttp3.ResponseBody responseBody,ProgressListener  progressListener){
    this.responseBody = responseBody;
    this.progressListener=progressListener;
}

public void setProgressListener(ProgressListener progressListener){
    this.progressListener=progressListener;
}

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink,byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;   //不断统计当前下载好的数据
                //接口回调
                if (progressListener!=null){
                progressListener.update(totalBytesRead,responseBody.contentLength(),bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }

    //回调接口
  public   interface ProgressListener{
        /**
         * @param bytesRead 已经读取的字节数
         * @param contentLength 响应总长度
         * @param done 是否读取完毕
         */
        void update(long bytesRead,long contentLength,boolean done);
    }

}
