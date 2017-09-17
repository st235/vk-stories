package sasd97.java_blog.xyz.libs_common.utils.components;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryAlphaView extends ConstraintLayout {

    private int initialColor = Color.WHITE;

    public StoryAlphaView(Context context) {
        super(context);
        onInit();
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    private void onInit() {
        Drawable background = getBackground();
        if (!(background instanceof ColorDrawable)) return;
        initialColor = ((ColorDrawable) background).getColor();
    }

    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
    }

    public void animateTo(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        Drawable background = getBackground();
        if (!(background instanceof ColorDrawable)) return;
        final ColorDrawable colorDrawable = new ColorDrawable(((ColorDrawable) background).getColor());

        ValueAnimator valueAnimator = ObjectAnimator.ofInt(colorDrawable.getColor(), scaleColorAlpha(alpha));
        valueAnimator.setEvaluator(new ArgbEvaluator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setBackgroundColor((int) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator
                .setDuration(350L)
                .start();
    }

    private int scaleColorAlpha(float scaleFactor) {
        int red = Color.red(initialColor);
        int green = Color.green(initialColor);
        int blue = Color.blue(initialColor);

        int alpha = (int) (255 * scaleFactor);

        return Color.argb(alpha, red, green, blue);
    }
}
