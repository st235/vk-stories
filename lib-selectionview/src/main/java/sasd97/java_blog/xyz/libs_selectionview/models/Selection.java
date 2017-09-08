package sasd97.java_blog.xyz.libs_selectionview.models;

import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 08/09/2017.
 */

public class Selection {

    private float width = 0;
    private int color = Color.WHITE;
    private RectF viewport = new RectF();

    public Selection(int width, @ColorInt int color) {
        this.width = width;
        this.color = color;
    }

    public void setWidth(float width) {
        this.width = Dimens.dpToPx(width);
    }

    public float getWidth() {
        return width;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setViewport(int counter, float offset,
                            float width, float height) {
        float hwidth = counter == 0 ? this.width / 2 : 0.0f;

        viewport.set(offset + hwidth, offset + hwidth,
                width - offset - hwidth, height - offset - hwidth);
    }

    public RectF getViewport() {
        return viewport;
    }
}
