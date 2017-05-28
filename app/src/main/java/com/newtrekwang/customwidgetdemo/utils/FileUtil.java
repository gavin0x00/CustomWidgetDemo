package com.newtrekwang.customwidgetdemo.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

public class FileUtil {
    public boolean writeResponseBodyToDisk(Context  context, ResponseBody responseBody, String saveName) {
        File file = new File( context.getExternalFilesDir(null) + File.separator + saveName);
        Logger.i("saveName>>>" + file.getAbsolutePath().toString());

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] bytes = new byte[2048];
            long fileSize = responseBody.contentLength();

            long fileDownLoadSize = 0;

            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(file);

            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }
                outputStream.write(bytes, 0, read);
                fileDownLoadSize += read;

            }
            outputStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
