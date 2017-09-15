package com.github.sasd97.vk_stories.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alexander on 14/09/2017.
 */

@Singleton
public class IntentResolver {

    private static final String PACKAGE_PROVIDER = ".provider";

    public static final int GALLERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 11;

    private Context context;

    @Inject
    public IntentResolver(@NonNull Context context) {
        this.context = context;
    }

    public Intent createGalleryIntent() {
        return new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public Uri getGalleryUri(@NonNull Intent data) {
        Uri temp = data.getData();
        if (temp == null) throw new IllegalStateException("Data is null");
        return temp;
    }

    public Intent createCameraIntent(@NonNull File file) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            fileUri = Uri.fromFile(file);
        } else {
            fileUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + PACKAGE_PROVIDER, file);
        }

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return cameraIntent;
    }

    public File createCameraFile() {
        File imageFile = null;

        try {
            String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
            String imageFileName = String.format(Locale.US, "JPEG_%1$s_", timeStamp);

            File storageDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            if (!storageDir.exists()) storageDir.mkdirs();
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }
}
