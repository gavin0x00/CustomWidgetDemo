package com.newtrekwang.customwidgetdemo.httphelper;

import okhttp3.MediaType;
import okio.BufferedSource;

public abstract class ResponseBody implements Cloneable {
    public abstract MediaType contentType();
    public abstract long contentLength();
    public abstract BufferedSource source();

}
