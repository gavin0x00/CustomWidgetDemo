package com.newtrekwang.customwidgetdemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.newtrekwang.customwidgetdemo.R;
import com.newtrekwang.customwidgetdemo.toast.ToastBuilder;

public class CustomContentViewDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View contentView= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_customcontent,null);
        final EditText et_userName= (EditText) contentView.findViewById(R.id.username);
        final EditText et_passWord= (EditText) contentView.findViewById(R.id.password);
            builder.setView(contentView)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ToastBuilder().setContentString(""+et_passWord.getText().toString()+et_userName.getText().toString())
                                    .createDefaultTextToast(getActivity())
                                    .show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }
}
