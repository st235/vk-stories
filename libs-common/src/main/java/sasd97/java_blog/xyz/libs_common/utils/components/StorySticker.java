package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by alexander on 13/09/2017.
 */

public class StorySticker extends AppCompatImageView {

    private final long ANIMATION_DURATION = 350L;

    private float previousScaleX = 1.0f;
    private float previousScaleY = 1.0f;

    public StorySticker(Context context) {
        super(context);
    }

    public StorySticker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StorySticker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void prepareToDelete() {
        previousScaleX = getScaleX();
        previousScaleY = getScaleY();

        animate()
                .alpha(0.5f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    public void release() {
        animate()
                .alpha(1.0f)
                .scaleX(previousScaleX)
                .scaleY(previousScaleY)
                .setDuration(ANIMATION_DURATION)
                .start();
    }
}
