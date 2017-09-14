package sasd97.java_blog.xyz.gallery_picker.models;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import sasd97.java_blog.xyz.libs_common.utils.models.Colorable;
import sasd97.java_blog.xyz.libs_common.utils.models.TextColor;

/**
 * Created by alexander on 09/09/2017.
 */

public class Tile implements Colorable {

    public static final int IMAGE = 0;
    public static final int CAMERA = 1;
    public static final int GALLERY = 2;

    @IntDef({IMAGE, CAMERA, GALLERY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private Uri uri;
    private int type;

    public Tile(@Type int type) {
        this.type = type;
    }

    public Tile(@NonNull Uri uri) {
        this.uri = uri;
        type = IMAGE;
    }

    public Uri getUri() {
        return uri;
    }

    @Type
    public int getType() {
        return type;
    }

    @Override
    public TextColor getColor() {
        return TextColor.WHITE;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tile{");
        sb.append("uri=").append(uri);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
