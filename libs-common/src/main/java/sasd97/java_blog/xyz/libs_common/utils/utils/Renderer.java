package sasd97.java_blog.xyz.libs_common.utils.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by alexander on 13/09/2017.
 */

public final class Renderer {

    private Renderer() {
    }

    public static Bitmap renderView(@NonNull View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }
}
