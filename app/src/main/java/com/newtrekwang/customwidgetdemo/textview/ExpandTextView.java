package com.newtrekwang.customwidgetdemo.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.utils.CustomMovementMethod;

/**
 * 字数多了可折叠的TextView
 */
public class ExpandTextView extends LinearLayout {
//    默认的显示行数
    public static final int DEFAULT_MAX_LINES = 3;
//    显示正文的textView
    private TextView contentText;
//    显示展开或折叠
    private TextView textPlus;
//    展开或折叠的监听器
    private OnExpandTextViewClicklistener onExpandTextViewClicklistener;
//    显示行数
    private int showLines;


    public ExpandTextView(Context context) {
       this(context,null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }




/**
 * 初始化
 */
    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentText=new TextView(getContext());
        addView(contentText,params);
//        设置显示行数
        if(showLines > 0){
            contentText.setMaxLines(showLines);
        }
        contentText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    onExpandTextViewClicklistener.onExpandTextViewClick();
            }
        });

        textPlus=new TextView(getContext());
        textPlus.setPadding(30,0,0,0);
        addView(textPlus,params);
        textPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String textStr = textPlus.getText().toString().trim();
                if("全文".equals(textStr)){
                    contentText.setMaxLines(Integer.MAX_VALUE);
                    textPlus.setText("收起");
                }else{
                    contentText.setMaxLines(showLines);
                    textPlus.setText("全文");
                }
            }
        });
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandTextView, 0, 0);//属性
        try {
            showLines = typedArray.getInt(R.styleable.ExpandTextView_showLines, DEFAULT_MAX_LINES);//显示属性行数
        }finally {
            typedArray.recycle();
        }
    }

    public void setText(final CharSequence content){
        contentText.post(new Runnable() {
            @Override
            public void run() {
                contentText.setText(content);
                int linCount = contentText.getLineCount();

                if(linCount > showLines){
                    contentText.setMaxLines(showLines);
                    textPlus.setVisibility(View.VISIBLE);
                    textPlus.setText("全文");
                }else{
                    textPlus.setVisibility(View.GONE);
                }
            }
        });

        contentText.setMovementMethod(new CustomMovementMethod());
    }

    public OnExpandTextViewClicklistener getOnExpandTextViewClicklistener() {
        return onExpandTextViewClicklistener;
    }

    public void setOnExpandTextViewClicklistener(OnExpandTextViewClicklistener onExpandTextViewClicklistener) {
        this.onExpandTextViewClicklistener = onExpandTextViewClicklistener;
    }

    public interface  OnExpandTextViewClicklistener{
        public void onExpandTextViewClick();
    }

}
