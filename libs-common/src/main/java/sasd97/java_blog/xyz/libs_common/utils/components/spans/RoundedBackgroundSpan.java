package sasd97.java_blog.xyz.libs_common.utils.components.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;
import android.util.Log;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_common.utils.utils.SpansHelper;

import static sasd97.java_blog.xyz.libs_common.utils.utils.SpansHelper.SMALLER_THAN;

/**
 * Created by alexander on 11/09/2017.
 */

public class RoundedBackgroundSpan extends ReplacementSpan {

    private final int textColor;
    private final int backgroundColor;

    private final float paddingEnd;
    private final float marginStart;
    private final float paddingStart;

    private final float topLeftRadius;
    private final float topRightRadius;
    private final float bottomLeftRadius;
    private final float bottomRightRadius;

    private final CornerRect cornerRect = new CornerRect();

    public RoundedBackgroundSpan(int backgroundColor, int textColor, float cornerRadius) {
        super();
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        topLeftRadius = Dimens.dpToPx(cornerRadius);
        topRightRadius = Dimens.dpToPx(cornerRadius);
        bottomLeftRadius = Dimens.dpToPx(cornerRadius);
        bottomRightRadius = Dimens.dpToPx(cornerRadius);
        paddingStart = Dimens.dpToPx(4.0f);
        paddingEnd = Dimens.dpToPx(4.0f);
        marginStart = Dimens.dpToPx(4.0f);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paddingStart + paint.measureText(text.subSequence(start, end).toString()) + paddingEnd);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        cornerRect.reset();

        String currentText = text.subSequence(start, end).toString();
        float width = paint.measureText(currentText);

        String[] split = text.toString().split("\n");

        int counter = 0;
        float prevLength = 0;
        float nextLength = 0;

        for (int i = 0; i < split.length; i++) {
            if (counter == start) {
                if (i - 1 >= 0) {
                    prevLength = paint.measureText(split[i - 1]);
                }

                if (i + 1 < split.length) {
                    nextLength = paint.measureText(split[i + 1]);
                }
            }
            counter += split[i].length() + 1;
        }

        RectF drawingRect = new RectF(x - paddingStart + marginStart, top, x + width + paddingEnd + marginStart, bottom);
        paint.setColor(backgroundColor);

        int prevState = SpansHelper.checkEquality(width, prevLength);
        int nextState = SpansHelper.checkEquality(width, nextLength);

        if (prevState == SpansHelper.BIGGER_THAN) {
            cornerRect.setTopBound(CornerRect.ROUNDED_INSIDE);
        } else if (prevState == SMALLER_THAN) {
            cornerRect.setTopBound(CornerRect.ROUNDED_OUTSIDE);
        }

        if (nextState == SpansHelper.BIGGER_THAN) {
            cornerRect.setBottomBound(CornerRect.ROUNDED_INSIDE);
        } else if (nextState == SMALLER_THAN) {
            cornerRect.setBottomBound(CornerRect.ROUNDED_OUTSIDE);
        }

        canvas.drawPath(obtainPath(drawingRect, cornerRect), paint);

        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + marginStart, y, paint);
    }

    private Path obtainPath(@NonNull RectF drawingRect, @NonNull CornerRect cornerRect) {
        Path path = new Path();
        RectF topLeftArcBound = new RectF();
        RectF topRightArcBound = new RectF();
        RectF bottomLeftArcBound = new RectF();
        RectF bottomRightArcBound = new RectF();

        if (cornerRect.getTopRightCorner() != CornerRect.ROUNDED_OUTSIDE) {
            topRightArcBound.set(drawingRect.right - topRightRadius * 2, drawingRect.top,
                    drawingRect.right, drawingRect.top + topRightRadius * 2);
        } else {
            topRightArcBound.set(drawingRect.right, drawingRect.top,
                    drawingRect.right + topRightRadius * 2,  drawingRect.top + topRightRadius * 2);
        }

        if (cornerRect.getBottomRightCorner() != CornerRect.ROUNDED_OUTSIDE) {
            bottomRightArcBound.set(drawingRect.right - bottomRightRadius * 2, drawingRect.bottom - bottomRightRadius * 2,
                    drawingRect.right, drawingRect.bottom);
        } else {
            bottomRightArcBound.set(drawingRect.right, drawingRect.bottom - bottomRightRadius * 2,
                    drawingRect.right + bottomRightRadius * 2, drawingRect.bottom);
        }

        if (cornerRect.getBottomLeftCorner() != CornerRect.ROUNDED_OUTSIDE) {
            bottomLeftArcBound.set(drawingRect.left, drawingRect.bottom - bottomLeftRadius * 2,
                    drawingRect.left + bottomLeftRadius * 2, drawingRect.bottom);
        } else {
            bottomLeftArcBound.set(drawingRect.left - bottomLeftRadius * 2, drawingRect.bottom - bottomLeftRadius * 2,
                    drawingRect.left, drawingRect.bottom);
        }

        if (cornerRect.getTopLeftCorner() != CornerRect.ROUNDED_OUTSIDE) {
            topLeftArcBound.set(drawingRect.left, drawingRect.top, drawingRect.left + bottomLeftRadius * 2,
                    drawingRect.top + bottomLeftRadius * 2);
        } else {
            topLeftArcBound.set(drawingRect.left - bottomLeftRadius * 2, drawingRect.top,
                    drawingRect.left, drawingRect.top + bottomLeftRadius * 2);
        }

        path.reset();

        path.moveTo(drawingRect.left + topLeftRadius, drawingRect.top);

        path.lineTo(drawingRect.right - topRightRadius, drawingRect.top);

        if (cornerRect.getTopRightCorner() == CornerRect.ROUNDED_INSIDE) {
            path.arcTo(topRightArcBound, -90, 90);
        } else if (cornerRect.getTopRightCorner() == CornerRect.ROUNDED_OUTSIDE) {
            path.lineTo(drawingRect.right + topRightRadius, drawingRect.top);
            path.arcTo(topRightArcBound, -90, -90);
        } else {
            path.lineTo(drawingRect.right, drawingRect.top);
            path.lineTo(drawingRect.right, drawingRect.top - topRightRadius);
        }

        path.lineTo(drawingRect.right, drawingRect.bottom - bottomRightRadius);

        if (cornerRect.getBottomRightCorner() == CornerRect.ROUNDED_INSIDE) {
            path.arcTo(bottomRightArcBound, 0, 90);
        } else if (cornerRect.getBottomRightCorner() == CornerRect.ROUNDED_OUTSIDE) {
            path.arcTo(bottomRightArcBound, 180, -90);
            path.lineTo(drawingRect.right, drawingRect.bottom);
        } else {
            path.lineTo(drawingRect.right, drawingRect.bottom);
            path.lineTo(drawingRect.right - bottomRightRadius, drawingRect.bottom);
        }

        path.lineTo(drawingRect.left - bottomLeftRadius, drawingRect.bottom);

        if (cornerRect.getBottomLeftCorner() == CornerRect.ROUNDED_INSIDE) {
            path.arcTo(bottomLeftArcBound, 90, 90);
        } else if (cornerRect.getBottomLeftCorner() == CornerRect.ROUNDED_OUTSIDE) {
            path.lineTo(drawingRect.left - bottomLeftRadius, drawingRect.bottom);
            path.arcTo(bottomLeftArcBound, 90, -90);
        } else {
            path.lineTo(drawingRect.left, drawingRect.bottom);
            path.lineTo(drawingRect.left, drawingRect.bottom - bottomLeftRadius);
        }

        path.lineTo(drawingRect.left, drawingRect.top + topLeftRadius);

        if (cornerRect.getTopLeftCorner() == CornerRect.ROUNDED_INSIDE) {
            path.arcTo(topLeftArcBound, 180, 90);
        } else if (cornerRect.getTopLeftCorner() == CornerRect.ROUNDED_OUTSIDE) {
            path.arcTo(topLeftArcBound, 0, -90);
            path.lineTo(drawingRect.left, drawingRect.top);
        } else {
            path.lineTo(drawingRect.left, drawingRect.top);
            path.lineTo(drawingRect.left + topLeftRadius, drawingRect.top);
        }

        path.close();
        return path;
    }
}