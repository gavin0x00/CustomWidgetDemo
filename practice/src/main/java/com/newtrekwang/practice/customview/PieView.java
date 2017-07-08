package com.newtrekwang.practice.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.newtrekwang.practice.bean.PieData;

import java.util.List;


public class PieView extends View {
    public PieView(Context context) {
        this(context,null);
    }
    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private List<PieData> mData;
    //宽高
    private int mWidth,mHeight;
    //画笔
    private Paint mPaint=new Paint();

    private void initPaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null==mData){
            return;
        }
        float currentStartAngle=mStartAngle;// 当前起始角度
        canvas.translate(mWidth/2,mHeight/2); // 将画布坐标原点移动到中心位置
        float r=Math.min(mWidth,mHeight)/2;//确定半径
        RectF rectF=new RectF(-r,-r,r,r);//饼状图绘制区域
mPaint.setTextSize(30);

        for (int i=0;i<mData.size();i++){
            PieData pieData=mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF,currentStartAngle,pieData.getAngle(),true,mPaint);
            currentStartAngle+=pieData.getAngle();
        }
    }
    public void setStartAngle(int mStartAngle){
        this.mStartAngle=mStartAngle;
        invalidate();//update
    }
    public void initDate(List<PieData> data){
        if (null==data || data.size()==0){
            return;
        }
        float sumValue=0;
        for (int i=0;i<data.size();i++){
            PieData pie=data.get(i);
            sumValue+=pie.getValue();
            int j=i%mColors.length;//设置颜色
            pie.setColor(mColors[j]);
        }
        float sumAngle=0;
        for (int i=0;i<data.size();i++){
            PieData pie=data.get(i);
            float percentage=pie.getValue()/sumValue;//百分比
            float angle=percentage * 360;

            pie.setPercentage(percentage);
            pie.setAngle(angle);
            sumAngle+=angle;
            Log.i("angle", "initDate: >>>"+pie.getAngle());
        }
    }
    public void setData(List<PieData> mData) {
        this.mData =  mData;
        initDate(this.mData);
        invalidate();//update
    }
}
