package sasd97.java_blog.xyz.gallery_picker.models;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by alexander on 09/09/2017.
 */

public class Tile {

    private Uri uri;

    public Tile(@NonNull Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }
}
