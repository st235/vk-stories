package sasd97.java_blog.xyz.libs_common.utils.models;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by alexander on 14/09/2017.
 */

public class ComplementaryColor {

    public static ComplementaryColor WHITE = new ComplementaryColor(Color.WHITE, Color.BLACK);
    public static ComplementaryColor BLACK = new ComplementaryColor(Color.BLACK, Color.WHITE);

    private int primaryColor;
    private int contrastColor;

    public ComplementaryColor(@ColorInt int primaryColor,
                              @ColorInt int contrastColor) {
        this.primaryColor = primaryColor;
        this.contrastColor = contrastColor;
    }

    @ColorInt public int getPrimary() {
        return primaryColor;
    }

    @ColorInt public int getPrimaryWithScaledAlpha(float factor) {
        return adjustAlpha(primaryColor, factor);
    }

    @ColorInt public int getContrast() {
        return contrastColor;
    }

    @ColorInt public int getContrastAuto() {
        return getContrastColor(primaryColor);
    }

    @ColorInt public int getContrastWithScaledAlpha(float factor) {
        return adjustAlpha(contrastColor, factor);
    }

    @ColorInt public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @ColorInt public static int getContrastColor(int color) {
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }
}
