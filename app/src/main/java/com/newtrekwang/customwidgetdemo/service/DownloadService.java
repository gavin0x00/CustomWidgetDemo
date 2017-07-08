package com.newtrekwang.customwidgetdemo.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.httphelper.ProgressResponseBody_1;
import com.newtrekwang.customwidgetdemo.utils.StringUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService>>>>>>>>>>>";
    private int id=0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Disposable disapo;

    private Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    String pro= (String) msg.getData().get("progress");
                    updateNoti(pro);
                    break;
                case 101:
                    Toast.makeText(DownloadService.this, "下载完成！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
    };


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }
    public DownloadService(){
        this("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("下载文件")
                .setTicker(null)
                .setSmallIcon(R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url =intent.getStringExtra("url");
        if (TextUtils.isEmpty(url))
            return;
        updateNoti("开始下载！");
        doGet_1(url);
    }

    private void doGet_1(final String url){
        Request request=new Request.Builder()
                .url(url)
                .method("GET",null)
                .build();
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        //这里将ResponseBody包装成我们的ProgressResponseBody
                        return response.newBuilder()
                                .body(new ProgressResponseBody_1(response.body(), new ProgressResponseBody_1.ProgressListener() {
                                    @Override
                                    public void update(long bytesRead, long contentLength, boolean done) {
                                        Log.e(TAG, "update: "+bytesRead/1024+" kb" );
                                        double progress=((bytesRead/1024)/((contentLength/1024)*1.0))*100;
                                        DecimalFormat df   = new DecimalFormat("######0.0");
                                        String proStr=df.format(progress)+"%";
                                        updateNoti(proStr);
                                        if (done){
                                            updateNoti("已完成下载！");
                                        }
                                    }
                                }))
                                .build();
                    }
                }).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                writeResponseBodyToDisk(response.body(),StringUtils.getUrlFileName(url));
            }
        });
    }

    private void disapose(){
        if (disapo!=null&&(!disapo.isDisposed()))
        {
            disapo.dispose();
            disapo=null;
        }
    }

    public boolean writeResponseBodyToDisk(ResponseBody responseBody, String saveName) {
        File file = new File( getExternalFilesDir(null) + File.separator + saveName);
        Logger.i("saveName>>>" + file.getAbsolutePath().toString());

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] bytes = new byte[4048];
            long fileSize = responseBody.contentLength()/1024;
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

//               Message message=new Message();
//                message.what=100;
//                Bundle bundle=new Bundle();
//                bundle.putString("progress",proStr);
//                message.setData(bundle);
//                handle.sendMessageDelayed(message,500);
//                Log.e(TAG, "writeResponseBodyToDisk: "+proStr);
            }
            outputStream.flush();
            disapose();
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

    private void updateNoti(String pro){
        builder.setContentText(pro);
        notificationManager.notify(id,builder.build());
    }

}
