package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.github.sasd97.lib_gradientview.models.Gradient;

/**
 * Created by alexander on 08/09/2017.
 */

public class GradientItem implements BackgroundItem {

    private Gradient gradient;

    public GradientItem(@NonNull Gradient gradient) {
        this.gradient = gradient;
    }

    public Gradient getGradient() {
        return gradient;
    }

    @Override
    public int getType() {
        return GRADIENT;
    }
}
