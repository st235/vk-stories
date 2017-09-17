package sasd97.java_blog.xyz.gallery_picker.providers;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.annotation.NonNull;

import sasd97.java_blog.xyz.libs_common.utils.providers.AsyncProvider;

/**
 * Created by alexander on 17/09/2017.
 */

public class CameraImageProvider implements AsyncProvider<Uri> {

    private static final String MEDIA_TYPE = "image/jpeg";

    private Context context;
    private AsyncProvider.ProviderListener<Uri> listener;

    public CameraImageProvider(@NonNull Context context,
                               @NonNull AsyncProvider.ProviderListener<Uri> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void provide(@NonNull Uri uri) {
        MediaScannerConnection.scanFile(context,
                new String[]{ uri.getPath() }, new String[]{ MEDIA_TYPE },
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {
                    }

                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        listener.onProvide(uri);
                    }
                });
    }
}