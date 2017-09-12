package sasd97.java_blog.xyz.libs_touchlistener;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRemoveListener;

/**
 * Created by Narek on 31.08.2017.
 */

public class RemoveGestureDetector {

    private static final int REMOVE_RADIUS = (int) Dimens.dpToPx(56);
    private static final int INVALID_POINTER_ID = -1;

    private Point removeRegion = new Point();
    private OnRemoveListener removeListener;

    private int activePointerId = INVALID_POINTER_ID;
    private boolean isRemoveEnabled = false;
    private boolean isStarted = false;
    private boolean isIntercepted = false;


    public boolean onTouchEvent(View view, MotionEvent event) {
        int action = event.getAction();

        switch (action & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                activePointerId = event.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int pointerIndex = event.findPointerIndex(activePointerId);

                if (pointerIndex != -1) {
                    Point touchCoords = getTouchCoordsInScreen(event);

                    if (removeListener != null && isRemoveEnabled) {
                        if (Math.abs(removeRegion.x - touchCoords.x) <= REMOVE_RADIUS
                                && Math.abs(removeRegion.y - touchCoords.y) <= REMOVE_RADIUS) {

                            if (!isIntercepted) {
                                isIntercepted = true;
                                removeListener.onIntercept(view);
                            }
                        } else {
                            if (isIntercepted) {
                                isIntercepted = false;
                                removeListener.onCanceled(view);
                            }
                        }

                    }

                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_UP:
                Point touchCoords = getTouchCoordsInScreen(event);

                if (removeListener != null && isRemoveEnabled) {
                    if (Math.abs(removeRegion.x - touchCoords.x) <= REMOVE_RADIUS
                            && Math.abs(removeRegion.y - touchCoords.y) <= REMOVE_RADIUS) {
                        removeListener.onRemove(view);
                        isStarted = false;
                    }
                }
                if(isStarted){
                    if(removeListener != null){
                        removeListener.onFinish(view);
                        isStarted = false;
                    }
                }
                isRemoveEnabled = false;
                activePointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_POINTER_UP: {
                // Extract the index of the pointer that left the touch sensor.
                int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    activePointerId = event.getPointerId(newPointerIndex);
                }

                break;
            }
        }

        return true;
    }

    @NonNull
    private Point getTouchCoordsInScreen(@NonNull MotionEvent event) {
        int touchX = (int) event.getRawX();
        int touchY = (int) event.getRawY();
        return new Point(touchX, touchY);
    }

    public void setRemoveListener(@NonNull OnRemoveListener removeListener, @NonNull Point removeRegion) {
        this.removeListener = removeListener;
        this.removeRegion = removeRegion;
    }

    public boolean isRemoveEnabled() {
        return isRemoveEnabled;
    }

    public void setRemoveEnabled(boolean removeEnabled) {
        isRemoveEnabled = removeEnabled;
        if (removeListener != null) {
            isStarted = true;
            removeListener.onStart();
        }
    }
}
