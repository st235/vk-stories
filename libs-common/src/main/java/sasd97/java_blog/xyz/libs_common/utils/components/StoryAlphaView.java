package sasd97.java_blog.xyz.libs_common.utils.components;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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

    public StoryAlphaView(Context context) {
        super(context);
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void animateTo(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        Drawable background = getBackground();
        if (!(background instanceof ColorDrawable)) return;
        final ColorDrawable colorDrawable = (ColorDrawable) background;

        ValueAnimator valueAnimator = ObjectAnimator.ofInt(colorDrawable.getAlpha(), (int) (alpha * 255));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                colorDrawable.setAlpha((int) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator
                .setDuration(350L)
                .start();
    }
}
