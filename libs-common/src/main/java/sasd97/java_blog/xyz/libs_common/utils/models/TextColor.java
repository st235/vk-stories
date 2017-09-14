package sasd97.java_blog.xyz.libs_common.utils.models;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;

/**
 * Created by alexander on 14/09/2017.
 */

public class TextColor {

    public static TextColor WHITE = new TextColor(Color.WHITE, Color.BLACK);
    public static TextColor BLACK = new TextColor(Color.BLACK, Color.WHITE);

    private int primaryColor;
    private int contrastColor;

    public TextColor(@ColorInt int primaryColor,
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

    @ColorInt public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @ColorInt public int getContrastColor(int color) {
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }
}
