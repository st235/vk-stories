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

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 13/09/2017.
 */

public class StoryBinView extends FloatingActionButton {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int radius;
    private Point center = new Point();

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

    public void release() {
        setImageResource(openedBinRes);

        animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(350L)
                .start();
    }

    public void close() {
        setImageResource(closedBinRes);

        animate()
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(350L)
                .start();
    }

    private void onInit() {
        setScaleType(ScaleType.CENTER_INSIDE);
        setCompatElevation(Dimens.dpToPx(2.0f));
        setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{Color.WHITE}));
    }
}
