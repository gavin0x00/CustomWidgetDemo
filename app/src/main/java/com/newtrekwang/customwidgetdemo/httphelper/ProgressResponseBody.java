package com.newtrekwang.customwidgetdemo.httphelper;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    private static final String TAG = "ProgressResponseBody>>>>>>>>>>>>";
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private FileProgresListenner fileProgresListenner;

    public ProgressResponseBody(ResponseBody responseBody){
    this.responseBody = responseBody;
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
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                Log.e(TAG, "read: >>>>>>>>>>>"+bytesRead/1024+"kb");
//                if (fileProgresListenner!=null){
//                    fileProgresListenner.onRead(contentLength(),bytesReaded);
//                }
                return bytesRead;
            }
        };
    }

    public void setFileProgresListenner(FileProgresListenner fileProgresListenner) {
        this.fileProgresListenner = fileProgresListenner;
    }

    public interface  FileProgresListenner{
        void onRead(long allSize,long readedSize);
    }
}
