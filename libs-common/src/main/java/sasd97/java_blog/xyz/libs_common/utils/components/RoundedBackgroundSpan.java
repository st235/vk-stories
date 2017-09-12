package sasd97.java_blog.xyz.libs_common.utils.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 11/09/2017.
 */

public class RoundedBackgroundSpan extends ReplacementSpan {

    private final int mBackgroundColor;
    private final int mTextColor;
    private final float mCornerRadius;
    private final float mPaddingStart;
    private final float mPaddingEnd;
    private final float mMarginStart;

    private final float vertical = Dimens.dpToPx(2.0f);

    public RoundedBackgroundSpan(int backgroundColor, int textColor, float cornerRadius) {
        super();
        mBackgroundColor = backgroundColor;
        mTextColor = textColor;
        mCornerRadius = Dimens.dpToPx(cornerRadius);
        mPaddingStart = Dimens.dpToPx(4.0f);
        mPaddingEnd = Dimens.dpToPx(4.0f);
        mMarginStart = Dimens.dpToPx(4.0f);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (mPaddingStart + paint.measureText(text.subSequence(start, end).toString()) + mPaddingEnd);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        float width = paint.measureText(text.subSequence(start, end).toString());
        RectF rect = new RectF(x - mPaddingStart + mMarginStart, top - vertical, x + width + mPaddingEnd + mMarginStart, bottom + vertical);
        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, mCornerRadius, mCornerRadius, paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x + mMarginStart, y, paint);
    }
}