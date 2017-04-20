package com.newtrekwang.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Sub1Activity extends AppCompatActivity {
    private static final String TAG = "Sub1Activity>>>>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
        Log.e(TAG, "onCreate: !!!!!!!!!!!!!!!!" +getTaskId());

        findViewById(R.id.btn_gosub2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sub1Activity.this,Sub2Activity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, "onNewIntent: >>>>>>>>>"+getTaskId() );
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: !!!!!!!!!!!!!!!!" +getTaskId());
    }
}
