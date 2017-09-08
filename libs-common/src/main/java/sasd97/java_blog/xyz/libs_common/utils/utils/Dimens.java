package sasd97.java_blog.xyz.libs_common.utils.utils;

import android.content.res.Resources;

/**
 * Created by Alex on 31.01.2016.
 */

public final class Dimens {

    private Dimens() {
    }

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static float pxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }
}
