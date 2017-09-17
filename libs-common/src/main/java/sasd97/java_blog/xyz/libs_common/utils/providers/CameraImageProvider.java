package sasd97.java_blog.xyz.libs_common.utils.providers;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by alexander on 17/09/2017.
 */

public class CameraImageProvider implements AsyncProvider<Uri, Single<Uri>> {

    private static final String MEDIA_TYPE = "image/jpeg";

    private Context context;

    public CameraImageProvider(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public Single<Uri> provide(@NonNull final Uri uri) {
        return Single.create(new SingleOnSubscribe<Uri>() {
            @Override
            public void subscribe(final SingleEmitter<Uri> e) throws Exception {
                MediaScannerConnection.scanFile(context,
                        new String[]{ uri.getPath() }, new String[]{ MEDIA_TYPE },
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {
                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                                e.onSuccess(uri);
                            }
                        });
            }
        });
    }
}