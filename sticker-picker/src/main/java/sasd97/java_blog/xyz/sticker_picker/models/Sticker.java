package sasd97.java_blog.xyz.sticker_picker.models;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by alexander on 09/09/2017.
 */

public class Sticker {

    private Uri uri;

    public Sticker(@NonNull Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sticker{");
        sb.append("uri=").append(uri);
        sb.append('}');
        return sb.toString();
    }
}
