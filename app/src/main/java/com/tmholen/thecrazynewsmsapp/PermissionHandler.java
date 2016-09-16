package com.tmholen.thecrazynewsmsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dogsh on 16-Sep-16.
 */

public class PermissionHandler {

    AppCompatActivity appCompatActivity;
    Context context;

    public void requestAccessToContacts(int permissionRequestCode) {
        requestPermission(Manifest.permission.WRITE_CONTACTS,
                permissionRequestCode);
    }

    public void requestAccessToSms(int permissionRequestCode) {
        requestPermission(Manifest.permission.SEND_SMS,
                permissionRequestCode);
    }

    public PermissionHandler(AppCompatActivity appCompatActivity, Context context) {
        this.appCompatActivity = appCompatActivity;
        this.context = context;
    }

    public void requestPermission(String manifestPermissionValue, int permissionRequestInt) {
        if (ContextCompat.checkSelfPermission(context, manifestPermissionValue) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity, manifestPermissionValue)) {

            } else {
                ActivityCompat.requestPermissions(appCompatActivity, new String[]{manifestPermissionValue}, permissionRequestInt);
            }
        }
    }

    public boolean permissionGranted(String manifestPermissionValues) {
        boolean result = true;
        result = ContextCompat.checkSelfPermission(context, manifestPermissionValues) == PackageManager.PERMISSION_GRANTED;
        return result;
    }
}
