package com.example.alif.movie.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.example.alif.movie.database.DatabaseHandler;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private AlertDialog.Builder alert;
    private DatabaseHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);
        alert = new AlertDialog.Builder(this);
        db = new DatabaseHandler(this);
    }
    //method buat get db
    protected DatabaseHandler getDB() {
        return db;
    }
    protected void showAlertMessage(String title, String message) {
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alert.show();
    }
    protected void showDialog(String msg) {
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null)
            pDialog.cancel();
    }

    protected boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork == null ? false : activeNetwork.isConnectedOrConnecting();
    }

}
