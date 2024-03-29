package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.NonNull;

import com.github.sasd97.lib_gradientview.models.Gradient;

import sasd97.java_blog.xyz.libs_common.utils.models.ComplementaryColor;

/**
 * Created by alexander on 08/09/2017.
 */

public class GradientItem implements BackgroundItem {

    private Gradient gradient;
    private ComplementaryColor complementaryColor = ComplementaryColor.WHITE;

    public GradientItem(@NonNull Gradient gradient) {
        this.gradient = gradient;
    }

    public Gradient getGradient() {
        return gradient;
    }

    public void setComplementaryColor(@NonNull ComplementaryColor complementaryColor) {
        this.complementaryColor = complementaryColor;
    }

    public ComplementaryColor getComplementaryColor() {
        return complementaryColor;
    }

    @Override
    public int getType() {
        return GRADIENT;
    }
}
