package com.newtrekwang.customwidgetdemo.utils;

import android.text.TextUtils;

public class StringUtils {
    public static String getUrlFileName(String fileUrl){
        if (TextUtils.isEmpty(fileUrl)){
            throw new NullPointerException("fileUrl为空！");
        }
        String fileName= fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        if (!TextUtils.isEmpty(fileName)){
            return fileName;
        }
        return "";
    }
}
