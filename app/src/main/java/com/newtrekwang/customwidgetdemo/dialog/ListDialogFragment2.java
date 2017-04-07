package com.newtrekwang.customwidgetdemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

import java.util.ArrayList;

public class ListDialogFragment2 extends DialogFragment {

    private ArrayList mSelectedItems;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems=new ArrayList();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("这是个多选框")
                .setMultiChoiceItems(R.array.listmenuitems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            mSelectedItems.add(which);
                        }else if (mSelectedItems.contains(which)){
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ToastBuilder().setContentString(mSelectedItems.toString()).createDefaultTextToast(getActivity()).show();
                    }
                })
                .setNegativeButton(R.string.cancel,null);
        return builder.create();
    }
}
