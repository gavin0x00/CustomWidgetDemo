package com.newtrekwang.practice;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity>>>>";
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_stop)
    Button btnStop;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mediaPlayer=MediaPlayer.create(this,R.raw.kr36);
        btnPause.setEnabled(false);
        btnPlay.setEnabled(true);
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                holder=surfaceView.getHolder();
                holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                mediaPlayer.setDisplay(holder);
                mediaPlayer.start();
                btnPause.setEnabled(true);
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPause.setText("继续");
                }else {
                    mediaPlayer.start();
                    btnPause.setText("暂停");
                }
                break;
            case R.id.btn_stop:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    btnPause.setEnabled(false);
                    btnPlay.setEnabled(true);
                }
                break;
        }
    }
}
