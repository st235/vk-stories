package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by alexander on 13/09/2017.
 */

public class StorySticker extends AppCompatImageView {

    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_BOTTOM = 1;

    @IntDef({ ALIGN_TOP, ALIGN_BOTTOM })
    public @interface Type {
    }

    private final long ANIMATION_DURATION = 350L;

    private float previousScaleX = 1.0f;
    private float previousScaleY = 1.0f;

    private int type = ALIGN_TOP;

    public StorySticker(Context context) {
        super(context);
    }

    public StorySticker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StorySticker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setType(@Type int type) {
        this.type = type;
    }

    @Type
    public int getType() {
        return type;
    }

    public RelativeLayout.LayoutParams createLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.addRule(type == ALIGN_TOP ? RelativeLayout.ALIGN_PARENT_TOP : RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(type == ALIGN_TOP ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP, 0);
        return params;
    }

    public void prepareToDelete() {
        previousScaleX = getScaleX();
        previousScaleY = getScaleY();

        normalizePivot();

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

    public void normalizePivot() {
        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);
    }
}
