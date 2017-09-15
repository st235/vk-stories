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

    private Context context;

    @Inject
    public PermissionResolver(@NonNull Context context) {
        this.context = context;
    }

    public boolean isStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission(@NonNull Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, WRITE_EXTERNAL_STORAGE_PERMISSION_NUMBER);
    }

    public boolean checkStoragePermission(int requestCode, int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_STORAGE_PERMISSION_NUMBER) return false;

        boolean result = true;
        for (int grantResult: grantResults) result &= grantResult == PackageManager.PERMISSION_GRANTED;
        return result;
    }
}
