package com.example.alif.movie.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.example.alif.movie.R;

public class AlertDialogManager {
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(status != null)
            alertDialog.setIcon((status) ? R.mipmap.ic_icon_bell : R.drawable.ic_action_bell);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
