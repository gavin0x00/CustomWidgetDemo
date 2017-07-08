package com.newtrekwang.customwidgetdemo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class TestView extends View {
    public TestView(Context context) {
       this(context,null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
       this(context,attrs,0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private Paint paint=new Paint();

    private void initPaint(){
        paint.setColor(Color.BLACK);//set color
        paint.setStyle(Paint.Style.FILL);//set mode
        paint.setStrokeWidth(10f);// set strok 10px
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPoint(200,200,paint);
        canvas.drawPoints(new float[]{
                500,500,
                500,600,
                500,700
        },paint);
    }
}
