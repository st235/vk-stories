package sasd97.java_blog.xyz.libs_common.utils.components.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;
import android.util.Log;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 11/09/2017.
 */

public class RoundedBackgroundSpan extends ReplacementSpan {

    private static final int TOP_LEFT_CORNER = 0x1;
    private static final int TOP_RIGHT_CORNER = 0x2;
    private static final int BOTTOM_RIGHT_CORNER = 0x4;
    private static final int BOTTOM_LEFT_CORNER = 0x8;

    private final int textColor;
    private final int backgroundColor;

    private final float paddingEnd;
    private final float marginStart;
    private final float paddingStart;

    private final float topLeftRadius;
    private final float topRightRadius;
    private final float bottomLeftRadius;
    private final float bottomRightRadius;

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
        String currentText = text.subSequence(start, end).toString();
        float width = paint.measureText(currentText);

        String[] split = text.toString().split("\n");

        int prevLength = 0;
        int nextLength = 0;
        int currentLength = end - start;
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals(currentText)) continue;

            if (i - 1 >= 0) {
                prevLength = split[i - 1].length();
            }

            if (i + 1 < split.length) {
                nextLength = split[i + 1].length();
            }
        }

        RectF drawingRect = new RectF(x - paddingStart + marginStart, top, x + width + paddingEnd + marginStart, bottom);
        paint.setColor(backgroundColor);

        int selectedParts = 0;
        if (currentLength > prevLength) selectedParts |= TOP_LEFT_CORNER | TOP_RIGHT_CORNER;
        if (currentLength > nextLength) selectedParts |= BOTTOM_LEFT_CORNER | BOTTOM_RIGHT_CORNER;

        canvas.drawPath(obtainPath(drawingRect, selectedParts), paint);

        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + marginStart, y, paint);
    }

    private Path obtainPath(@NonNull RectF drawingRect,
                            int selectedParts) {
        Path path = new Path();
        RectF topLeftArcBound = new RectF();
        RectF topRightArcBound = new RectF();
        RectF bottomLeftArcBound = new RectF();
        RectF bottomRightArcBound = new RectF();

        topRightArcBound.set(drawingRect.right - topRightRadius * 2, drawingRect.top,
                drawingRect.right, drawingRect.top + topRightRadius * 2);

        bottomRightArcBound.set(drawingRect.right - bottomRightRadius * 2, drawingRect.bottom - bottomRightRadius * 2,
                drawingRect.right, drawingRect.bottom);

        bottomLeftArcBound.set(drawingRect.left, drawingRect.bottom - bottomLeftRadius * 2,
                drawingRect.left + bottomLeftRadius * 2, drawingRect.bottom);

        topLeftArcBound.set(drawingRect.left, drawingRect.top, drawingRect.left + bottomLeftRadius * 2,
                drawingRect.top + bottomLeftRadius * 2);

        path.reset();

        path.moveTo(drawingRect.left + topLeftRadius, drawingRect.top);

        path.lineTo(drawingRect.right - topRightRadius, drawingRect.top);

        if ((selectedParts & TOP_RIGHT_CORNER) > 0) {
            path.arcTo(topRightArcBound, -90, 90);
        }
        else {
            path.lineTo(drawingRect.right, drawingRect.top);
            path.lineTo(drawingRect.right, drawingRect.top - topRightRadius);
        }

        path.lineTo(drawingRect.right, drawingRect.bottom - bottomRightRadius);

        if ((selectedParts & BOTTOM_RIGHT_CORNER) > 0) {
            path.arcTo(bottomRightArcBound, 0, 90);
        } else {
            path.lineTo(drawingRect.right, drawingRect.bottom);
            path.lineTo(drawingRect.right - bottomRightRadius, drawingRect.bottom);
        }

        path.lineTo(drawingRect.left - bottomLeftRadius, drawingRect.bottom);

        if ((selectedParts & BOTTOM_LEFT_CORNER) > 0) {
            path.arcTo(bottomLeftArcBound, 90, 90);
        } else {
            path.lineTo(drawingRect.left, drawingRect.bottom);
            path.lineTo(drawingRect.left, drawingRect.bottom - bottomLeftRadius);
        }

        path.lineTo(drawingRect.left, drawingRect.top + topLeftRadius);

        if ((selectedParts & TOP_LEFT_CORNER) > 0) {
            path.arcTo(topLeftArcBound, 180, 90);
        } else {
            path.lineTo(drawingRect.left, drawingRect.top);
            path.lineTo(drawingRect.left + topLeftRadius, drawingRect.top);
        }

        path.close();
        return path;
    }
}