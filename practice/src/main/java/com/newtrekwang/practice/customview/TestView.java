package com.newtrekwang.practice.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.newtrekwang.practice.R;


public class TestView extends View implements Test {
    private int mWidth;
    private int mHeight;
    private Context context;
    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initPaint();
    }

    private Paint paint = new Paint();

    private void initPaint() {
        paint.setColor(Color.BLACK);//set color
        paint.setStyle(Paint.Style.FILL);//set mode
        paint.setStrokeWidth(10f);// set strok 10px
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
     drawText1(canvas);
    }

    @Override
    public void drawLine(Canvas canvas) {
        canvas.drawLine(300, 300, 500, 500, paint);
        canvas.drawLines(new float[]{
                100, 200, 200, 200,
                100, 300, 200, 300
        }, paint);
    }

    @Override
    public void drawRec1(Canvas canvas) {
        canvas.drawRect(100, 100, 800, 400, paint);
    }

    @Override
    public void drawRec2(Canvas canvas) {
        Rect rect=new Rect(100,100,800,400);
        canvas.drawRect(rect,paint);
    }

    @Override
    public void drawRec3(Canvas canvas) {
        RectF rectF=new RectF(100,100,800,400);
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void drawRoundRec1(Canvas canvas) {
        RectF rectF=new RectF(100,100,800,400);
        canvas.drawRoundRect(rectF,30,30,paint);
    }

    @Override
    public void drawRoundRec2(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100,100,800,400,350,150,paint);
        }
    }

    @Override
    public void drawCircle(Canvas canvas) {
        canvas.drawCircle(500,500,400,paint);//坐标（500，500）半径400
    }

    @Override
    public void drawArc(Canvas canvas) {
        RectF rectF=new RectF(100,100,800,400);
        //绘制背景矩形
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF,paint);
        //绘制圆弧
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,paint);

//绘制背景矩形
        RectF rectF1=new RectF(100,600,800,900);
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF1,paint);
//        绘制圆弧
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF1,0,90,true,paint);

    }

    @Override
    public void drawArc1(Canvas canvas) {
        RectF rectF=new RectF(100,100,600,600);
        //绘制背景矩形
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF,paint);
        //绘制圆弧
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF,0,90,false,paint);

//绘制背景矩形
        RectF rectF1=new RectF(100,600,600,1100);
        paint.setColor(Color.GRAY);
        canvas.drawRect(rectF1,paint);
//        绘制圆弧
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF1,0,90,true,paint);
    }

    @Override
    public void testPaintStyle(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40);//为了试验，设为40px
        //描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,200,100,paint);

        //填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200,500,100,paint);

        //描边加填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200,800,100,paint);


    }
 //画布平移
    @Override
    public void transLate(Canvas canvas) {
        paint.setColor(Color.BLUE);
        canvas.translate(200,200);//原点移到200，200
        canvas.drawCircle(0,0,100,paint);

        canvas.translate(200,200);
        paint.setColor(Color.RED);
        canvas.drawCircle(0,0,100,paint);
    }

    @Override
    public void scale(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);//移至中心
        RectF rectF=new RectF(0,-400,400,0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.GREEN);
        canvas.scale(0.5f,0.5f);//画布缩放
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void scale1(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);//移至中心
        RectF rectF=new RectF(0,-400,400,0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.GREEN);
        canvas.scale(0.5f,0.5f,200,0);//画布缩放,缩放中心向右偏移200
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void scale2(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);//移至中心
        RectF rectF=new RectF(0,-400,400,0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.GREEN);
        canvas.scale(-0.5f,-0.5f);//画布缩放，而且进行了翻转
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void scale3(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);//移至中心
        RectF rectF=new RectF(0,-400,400,0);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,paint);
        paint.setColor(Color.GREEN);
        canvas.scale(-0.5f,-0.5f,200,0);//画布缩放，而且进行了翻转，在缩放中心平移
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void scale4(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);
        RectF rectF=new RectF(-400,-400,400,400);
        for (int i=0;i<=20;i++){
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,paint);
        }

    }

    @Override
    public void rotate1(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);

        RectF rectF=new RectF(0,-400,400,0);
        canvas.drawRect(rectF,paint);

        paint.setColor(Color.BLUE);
        canvas.rotate(180);//顺时针旋转190度
        canvas.drawRect(rectF,paint);

    }

    @Override
    public void rotate2(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);

        RectF rectF=new RectF(0,-400,400,0);
        canvas.drawRect(rectF,paint);

        paint.setColor(Color.BLUE);
        canvas.rotate(180,200,0);//顺时针旋转190度,并且设置了旋转中心
        canvas.drawRect(rectF,paint);
    }

    @Override
    public void rotate3(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);

        canvas.drawCircle(0,0,400,paint);
        canvas.drawCircle(0,0,300,paint);

        for (int i=0;i<360;i++){
            canvas.drawLine(0,300,400,0,paint);
            canvas.rotate(10);
        }
    }

    @Override
    public void rotate4(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);

        canvas.drawCircle(0,0,400,paint);
        canvas.drawCircle(0,0,300,paint);

        for (int i=0;i<360;i++){
            canvas.drawLine(300,0,400,0,paint);
            canvas.rotate(10);
        }
    }

    /**
     * 错切 public void skew (float sx, float sy)
     * float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
     * float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
     * @param canvas
     */
    @Override
    public void skew1(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);

        RectF rectF=new RectF(0,0,200,200);
        canvas.drawRect(rectF,paint);

        canvas.skew(1,0);//水平错切
        paint.setColor(Color.BLUE);
        canvas.drawRect(rectF,paint);

    }

private Picture picture=new Picture();
    @Override
    public void recording1() {
        Canvas canvas1=picture.beginRecording(500,500);//开始录制绘制步骤
        canvas1.translate(250,250);
        canvas1.drawCircle(0,0,100,paint);
        picture.endRecording();//结束录制绘制步骤
    }

    @Override
    public void usePicture(Canvas canvas) {
        picture.draw(canvas);
    }

    @Override
    public void usePicture1(Canvas canvas) {
        //Canvas的drawPicture不会影响Canvas状态。
        canvas.drawPicture(picture);
//        canvas.drawPicture(picture,rect);  还可以加一个rect进行选取缩放
    }

    @Override
    public void usePicture2(Canvas canvas) {
        PictureDrawable drawable=new PictureDrawable(picture);//包装为Drawable
        // 设置绘制区域 -- 注意此处所绘制的实际内容不会缩放
        drawable.setBounds(0,0,250,picture.getHeight());
        drawable.draw(canvas);
    }

    @Override
    public void drawBitmap(Canvas canvas) {
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//        canvas.drawBitmap(bitmap,new Matrix(),new Paint());
//        canvas.drawBitmap(bitmap,200,500,new Paint());//与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的，但是也请注意分别两者的不同。

        canvas.translate(mWidth/2,mHeight/2);
        Rect src=new Rect(0,0,bitmap.getWidth()/2,bitmap.getHeight()/2);// 指定图片绘制区域(左上角的四分之一)
        Rect dst=new Rect(0,0,200,400);// 指定图片在屏幕上显示的区域,会进行缩放
        canvas.drawBitmap(bitmap,src,dst,null);

    }

    @Override
    public void drawText(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50);
        String str="ABcEsdfda";
        canvas.drawText(str,200,500,textPaint);// 参数分别为 (文本 基线x 基线y 画笔)
    }

    @Override
    public void drawText1(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50);
        String str="ABcE";
        canvas.drawPosText(str,new float[]{
                100,100,
                200,200,
                300,300,
                400,400
        },textPaint);
    }

    @Override
    public void drawText2(Canvas canvas) {

    }

}


interface Test {
    void drawLine(Canvas canvas);

    void drawRec1(Canvas canvas);

    void drawRec2(Canvas canvas);
    void drawRec3(Canvas canvas);
    void drawRoundRec1(Canvas canvas);
    void drawRoundRec2(Canvas canvas);
    void drawCircle(Canvas canvas);
    void drawArc(Canvas canvas);
    void drawArc1(Canvas canvas);

    void testPaintStyle(Canvas canvas);

    void transLate(Canvas canvas);

    void scale(Canvas canvas);
    void scale1(Canvas canvas);
    void scale2(Canvas canvas);
    void scale3(Canvas canvas);
    void scale4(Canvas canvas);

    void rotate1(Canvas canvas);
    void rotate2(Canvas canvas);
    void rotate3(Canvas canvas);
    void rotate4(Canvas canvas);



    void skew1(Canvas canvas);

    void recording1();
    void usePicture(Canvas canvas);//不推荐用这种
    void usePicture1(Canvas canvas);
    void usePicture2(Canvas canvas);

    void drawBitmap(Canvas canvas);

    void drawText(Canvas canvas);
    void drawText1(Canvas canvas);
    void drawText2(Canvas canvas);




}

