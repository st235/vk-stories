package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 13/09/2017.
 */

public class StoryBinView extends FloatingActionButton {

    private final long ANIMATION_DURATION = 350L;

    private int closedBinRes;
    private int openedBinRes;

    public StoryBinView(Context context) {
        super(context);
        onInit();
    }

    public StoryBinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public StoryBinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    public void setBins(@DrawableRes int closedBinRes, @DrawableRes int openedBinRes) {
        this.closedBinRes = closedBinRes;
        this.openedBinRes = openedBinRes;

        setImageResource(closedBinRes);
        invalidate();
    }

    @Override
    public void show() {
        super.show();
        setImageResource(closedBinRes);

        setAlpha(0.0f);
        setTranslationY(150.0f);

        animate()
                .alpha(1.0f)
                .translationY(0.0f)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    public void release() {
        setImageResource(openedBinRes);

        animate()
                .scaleX(1.25f)
                .scaleY(1.25f)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    public void close() {
        setImageResource(closedBinRes);

        animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(ANIMATION_DURATION)
                .start();
    }

    private void onInit() {
        setScaleType(ScaleType.CENTER_INSIDE);
        setCompatElevation(Dimens.dpToPx(2.0f));
        setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{Color.WHITE}));
    }
}
