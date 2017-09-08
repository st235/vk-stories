package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.IntDef;

/**
 * Created by alexander on 08/09/2017.
 */

public interface BackgroundItem {

    int GRADIENT = 0;
    int IMAGE = 1;
    int PLUS = 2;

    @IntDef({ GRADIENT, IMAGE, PLUS })
    @interface Types {
    }

    @Types int getType();
}
