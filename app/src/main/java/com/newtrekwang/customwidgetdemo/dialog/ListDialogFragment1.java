package com.newtrekwang.customwidgetdemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

public class ListDialogFragment1 extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("这是个ListDialog")
                .setItems(R.array.listmenuitems, new DialogInterface.OnClickListener() {
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
