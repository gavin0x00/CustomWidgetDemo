package com.newtrekwang.customwidgetdemo.toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class ToastBuilder {
    private int show_length;
    private String contentString;
    private int contentViewResId;

    public ToastBuilder setShow_length(int show_length) {
        this.show_length = show_length;
        return this;
    }

    public ToastBuilder setContentString(String contentString) {
        this.contentString = contentString;
        return this;
    }

    public ToastBuilder setContentViewResId(int contentViewResId) {
        this.contentViewResId = contentViewResId;
        return this;
    }

    public ToastBuilder() {
        this.show_length = Toast.LENGTH_SHORT;
        this.contentString = "";
        this.contentViewResId =0;
    }

    public Toast createDefaultTextToast(Context context){
        return Toast.makeText(context, this.contentString, this.show_length);
    }
    public Toast createCustomViewToast(Context context){
        Toast toast=new Toast(context);
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(this.contentViewResId, null);
        if (view==null){
            return null;
        }
        toast.setView(view);
        toast.setDuration(show_length);
        return toast;
    }

}
