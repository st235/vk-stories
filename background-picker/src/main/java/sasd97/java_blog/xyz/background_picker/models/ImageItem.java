package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.github.sasd97.lib_gradientview.models.Gradient;

/**
 * Created by alexander on 08/09/2017.
 */

public class ImageItem implements BackgroundItem {

    private int res;

    public ImageItem(@IdRes int res) {
        this.res = res;
    }

    public int getResource() {
        return res;
    }

    @Override
    public int getType() {
        return IMAGE;
    }
}
