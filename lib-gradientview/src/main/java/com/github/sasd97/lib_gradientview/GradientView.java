package com.github.sasd97.lib_gradientview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.sasd97.lib_gradientview.models.Gradient;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;


/**
 * Created by alexander on 08/09/2017.
 */

public class GradientView extends View {

    private Paint paint;
    private Gradient gradient;
    private PointF gradientFinish;
    private float radius = Dimens.dpToPx(4.0f);
    private RectF viewport = new RectF(0, 0, 0, 0);


    public GradientView(Context context) {
        this(context, null);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    private void onInit() {
        gradientFinish = new PointF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setPercentile(@FloatRange(from = 0.0f, to = 1.0f) float percentile) {
        if (gradient == null) return;
        this.gradient.setPercentile(percentile);
        invalidate();
    }

    public void setGradient(@NonNull Gradient gradient) {
        this.gradient = gradient;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        LinearGradient linearGradient = new LinearGradient(0, 0, gradientFinish.x, gradientFinish.y,
                gradient.getStartColor(), gradient.getFinishColor(), Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRoundRect(viewport, radius, radius, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        viewport.set(0, 0, width, height);
        gradientFinish.set(width * gradient.getPercentile(), height * gradient.getPercentile());
    }
}
