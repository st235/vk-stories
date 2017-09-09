package sasd97.java_blog.xyz.libs_selectionview;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.content.Context;
import android.util.AttributeSet;
import android.annotation.TargetApi;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public class SelectionView extends View {

    private Paint paint;
    private List<Selection> selections;
    private float radius = Dimens.dpToPx(4.0f);

    public SelectionView(Context context) {
        this(context, null);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    public void onInit() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selections = new ArrayList<>();
    }

    public void add(@NonNull Selection selection) {
        selections.add(selection);
        invalidate();
    }

    public void addAll(@NonNull Collection<Selection> selections) {
        this.selections.addAll(selections);
        invalidate();
    }

    public void setRadius(float radius) {
        this.radius = Dimens.dpToPx(radius);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);

        for (Selection selection: selections) {
            paint.setStrokeWidth(selection.getWidth());
            paint.setColor(selection.getColor());
            canvas.drawRoundRect(selection.getViewport(), radius, radius, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        int counter = 0;
        float offset = 0.0f;
        for (Selection selection: selections) {
            selection.setViewport(counter, offset, width, height);
            offset += selection.getWidth();
            counter++;
        }
    }
}
