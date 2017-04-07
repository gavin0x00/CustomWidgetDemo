package com.newtrekwang.customwidgetdemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.SimpleAdapter;

import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;
import com.newtrekwang.customwidgetdemo.utils.DataUtil;

public class ListDialogFragment4 extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(), DataUtil.getSimpleData(),android.R.layout.simple_list_item_2,new String[]{"key1","key2"},new int[]{android.R.id.text1,android.R.id.text2});

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("这是个列表（自定义adapter实现）")
                .setAdapter(simpleAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ToastBuilder()
                                .setContentString(""+which)
                                .createDefaultTextToast(getActivity())
                                .show();
                    }
                });
        return builder.create();
    }
}
