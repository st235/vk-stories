package sasd97.java_blog.xyz.libs_common.utils.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexander on 14/09/2017.
 */

public class IntentResolver {

    public static final int GALLERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 11;

    public static Intent createGalleryIntent() {
        return new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public static Uri getGalleryUri(@NonNull Intent data) {
        Uri temp = data.getData();
        if (temp == null) throw new IllegalStateException("Data is null");
        return temp;
    }

    public static Intent createCameraIntent(@NonNull File file) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return cameraIntent;
    }

    public static File createCameraFile() {
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
