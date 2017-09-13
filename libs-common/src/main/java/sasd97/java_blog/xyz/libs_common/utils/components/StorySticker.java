package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by alexander on 13/09/2017.
 */

public class StorySticker extends AppCompatImageView {

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
        animate()
                .alpha(0.5f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .start();
    }

    public void release() {
        animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .start();
    }
}
