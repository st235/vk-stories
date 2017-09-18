package sasd97.java_blog.xyz.libs_touchlistener;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import sasd97.java_blog.xyz.libs_touchlistener.listeners.ClickGestureListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.LongPressGestureListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnDownListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRemoveListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRotateListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnScaleListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnTouchMoveListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnTranslationListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnUpListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.ScaleGestureListener;

/**
 * Created by alexander on 09/09/2017.
 */

public class MultiTouchListener implements OnTouchListener {

    private static final int INVALID_POINTER_ID = -1;

    private boolean isRotateEnabled = true;
    private boolean isTranslateEnabled = true;
    private boolean isScaleEnabled = true;

    private float minimumScale = 0.5f;
    private float maximumScale = 3.0f;

    private int activePointerId = INVALID_POINTER_ID;
    private float previousX;
    private float previousY;

    private OnUpListener upListener;
    private OnDownListener downListener;
    private View.OnClickListener clickListener;
    private OnTouchMoveListener touchMoveListener;

    private LongPressGestureListener longPressGestureListener = new LongPressGestureListener();

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetectorCompat clickGestureDetector;
    private RemoveGestureDetector removeGestureDetector;
    private GestureDetectorCompat longPressGestureDetector;

    private ViewTransformer transformer;

    public MultiTouchListener(@NonNull Context context) {
        transformer = new ViewTransformer();
        removeGestureDetector = new RemoveGestureDetector();
        clickGestureDetector = new GestureDetectorCompat(context, new ClickGestureListener());
        longPressGestureDetector = new GestureDetectorCompat(context, longPressGestureListener);
        scaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener(this, transformer));
        longPressGestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(view, event);
        removeGestureDetector.onTouchEvent(view, event);
        longPressGestureDetector.onTouchEvent(event);
        longPressGestureListener.attachView(view);

        if (clickGestureDetector.onTouchEvent(event)) {
            if (clickListener != null) clickListener.onClick(view);
        }

        if (!isTranslateEnabled) {
            return true;
        }

        int action = event.getAction();
        switch (action & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                previousX = event.getX();
                previousY = event.getY();

                activePointerId = event.getPointerId(0);
                if (downListener != null) {
                    downListener.onDown(view);
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int pointerIndex = event.findPointerIndex(activePointerId);
                if (pointerIndex != -1) {
                    if (touchMoveListener != null) {
                        touchMoveListener.onTouchMove(event.getRawX(), event.getRawY());
                    }

                    float currX = event.getX(pointerIndex);
                    float currY = event.getY(pointerIndex);
                    if (!scaleGestureDetector.isInProgress()) {
                        transformer.adjustTranslation(view, currX - previousX, currY - previousY);
                    }
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_UP:
                if (upListener != null) {
                    upListener.onUp(view);
                }
                activePointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_POINTER_UP: {
                int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    previousX = event.getX(newPointerIndex);
                    previousY = event.getY(newPointerIndex);
                    activePointerId = event.getPointerId(newPointerIndex);
                }

                break;
            }
        }

        return true;
    }


    //region setter & getters
    public void setOnClickListener(@NonNull View.OnClickListener onClickListener) {
        this.clickListener = onClickListener;
    }

    public void setOnLongClickListener(@NonNull View.OnLongClickListener longClickListener) {
        longPressGestureListener.setListener(longClickListener);
    }

    public void setDownListener(@NonNull OnDownListener downListener) {
        this.downListener = downListener;
    }

    public void setUpListener(@NonNull OnUpListener upListener) {
        this.upListener = upListener;
    }

    public void setRemoveListener(@NonNull OnRemoveListener removeListener, @NonNull RemoveRegionProvider provider) {
        removeGestureDetector.setRemoveListener(removeListener, provider);
    }

    public void setRotateListener(@NonNull OnRotateListener rotateListener) {
        this.transformer.setRotateListener(rotateListener);
    }

    public void setScaleListener(@NonNull OnScaleListener scaleListener) {
        this.transformer.setScaleListener(scaleListener);
    }

    public void setTranslationListener(@NonNull OnTranslationListener translationListener) {
        this.transformer.setTranslationListener(translationListener);
    }

    public void setTouchMoveListener(OnTouchMoveListener touchMoveListener) {
        this.touchMoveListener = touchMoveListener;
    }

    public boolean isRotateEnabled() {
        return isRotateEnabled;
    }

    public void setRotateEnabled(boolean rotateEnabled) {
        isRotateEnabled = rotateEnabled;
    }

    public boolean isTranslateEnabled() {
        return isTranslateEnabled;
    }

    public void setTranslateEnabled(boolean translateEnabled) {
        isTranslateEnabled = translateEnabled;
    }

    public boolean isRemoveEnabled() {
        return removeGestureDetector.isRemoveEnabled();
    }

    public void setRemoveEnabled(boolean removeEnabled) {
        removeGestureDetector.setRemoveEnabled(removeEnabled);
    }

    public boolean isScaleEnabled() {
        return isScaleEnabled;
    }

    public void setScaleEnabled(boolean scaleEnabled) {
        isScaleEnabled = scaleEnabled;
    }

    public float getMinimumScale() {
        return minimumScale;
    }

    public float getMaximumScale() {
        return maximumScale;
    }
    //endregion
}