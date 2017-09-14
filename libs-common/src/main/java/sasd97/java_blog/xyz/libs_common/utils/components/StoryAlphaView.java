package sasd97.java_blog.xyz.libs_common.utils.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryAlphaView extends View {

    public StoryAlphaView(Context context) {
        super(context);
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryAlphaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StoryAlphaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void animateTo(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        animate()
                .setDuration(100L)
                .alphaBy(getAlpha())
                .alpha(alpha)
                .start();
    }
}
