package com.github.sasd97.vk_stories.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexander on 15/09/2017.
 */

@Singleton
public class PermissionResolver {

    private final int WRITE_EXTERNAL_STORAGE_PERMISSION_NUMBER = 10;
    private final int READ_EXTERNAL_STORAGE_PERMISSION_NUMBER = 11;

    private Context context;

    @Inject
    public PermissionResolver(@NonNull Context context) {
        this.context = context;
    }

    public boolean isStorageWritePermissionGranted() {
        return isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public boolean isStorageReadPermissionGranted() {
        return isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void requestStorageWritePermission(@NonNull Activity activity) {
        requestPermission(activity, WRITE_EXTERNAL_STORAGE_PERMISSION_NUMBER,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void requestStorageReadPermission(@NonNull Activity activity) {
        requestPermission(activity, READ_EXTERNAL_STORAGE_PERMISSION_NUMBER,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public boolean checkWriteStoragePermission(int requestCode, int[] grantResults) {
        return checkPermission(WRITE_EXTERNAL_STORAGE_PERMISSION_NUMBER, requestCode, grantResults);
    }

    public boolean checkReadStoragePermission(int requestCode, int[] grantResults) {
        return checkPermission(READ_EXTERNAL_STORAGE_PERMISSION_NUMBER, requestCode, grantResults);
    }

    private void requestPermission(@NonNull Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    private boolean isPermissionGranted(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission(int inRequestCode, int outRequestCode, int[] grantResults) {
        if (outRequestCode != inRequestCode) return false;

        boolean result = true;
        for (int grantResult: grantResults) result &= grantResult == PackageManager.PERMISSION_GRANTED;
        return result;
    }
}
