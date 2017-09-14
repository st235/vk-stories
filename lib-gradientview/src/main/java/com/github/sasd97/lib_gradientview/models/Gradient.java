package com.github.sasd97.lib_gradientview.models;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Created by alexander on 08/09/2017.
 */

public class Gradient {

    private float percentile = 0.7f;

    private int startColor = Color.WHITE;
    private int finishColor = Color.BLACK;

    public Gradient(@ColorInt int startColor,
                    @ColorInt int finishColor) {
        this.startColor = startColor;
        this.finishColor = finishColor;
    }

    public void setColors(@ColorInt int startColor,
                          @ColorInt int finishColor) {
        this.startColor = startColor;
        this.finishColor = finishColor;
    }

    public void setPercentile(@FloatRange(from = 0.0f, to = 1.0f) float percentile) {
        this.percentile = percentile;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getFinishColor() {
        return finishColor;
    }

    public float getPercentile() {
        return percentile;
    }

    public boolean isMonochrome() {
        return startColor == finishColor;
    }
}
