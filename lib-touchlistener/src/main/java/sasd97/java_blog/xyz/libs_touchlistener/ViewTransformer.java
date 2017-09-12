package sasd97.java_blog.xyz.libs_touchlistener;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;

import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRotateListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnScaleListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnTranslationListener;
import sasd97.java_blog.xyz.libs_touchlistener.models.Transform;


/**
 * Created by alexander on 26/08/2017.
 */

public class ViewTransformer {

    //region listeners
    private OnScaleListener scaleListener;
    private OnRotateListener rotateListener;
    private OnTranslationListener translationListener;
    //endregion

    public void setRotateListener(@NonNull OnRotateListener rotateListener) {
        this.rotateListener = rotateListener;
    }

    public void setScaleListener(@NonNull OnScaleListener scaleListener) {
        this.scaleListener = scaleListener;
    }

    public void setTranslationListener(@NonNull OnTranslationListener translationListener) {
        this.translationListener = translationListener;
    }

    public float adjustAngle(float degrees) {
        if (degrees > 180.0f) {
            degrees -= 360.0f;
        } else if (degrees < -180.0f) {
            degrees += 360.0f;
        }

        return degrees;
    }

    public void move(View view, Transform info) {
        computeRenderOffset(view, info.pivotX, info.pivotY);
        adjustTranslation(view, info.deltaX, info.deltaY);

        // Assume that scaling still maintains aspect ratio.
        float scale = view.getScaleX() * info.deltaScale;
        scale = Math.max(info.minimumScale, Math.min(info.maximumScale, scale));
        view.setScaleX(scale);
        view.setScaleY(scale);

        float rotation = adjustAngle(view.getRotation() + info.deltaAngle);
        view.setRotation(rotation);

        if (rotateListener != null) rotateListener.onRotate(rotation);
        if (scaleListener != null) scaleListener.onScale(scale);
    }

    public void adjustTranslation(View view, float deltaX, float deltaY) {
        float[] deltaVector = {deltaX, deltaY};
        view.getMatrix().mapVectors(deltaVector);

        float positionX = view.getTranslationX() + deltaVector[0];
        float positionY = view.getTranslationY() + deltaVector[1];

        view.setTranslationX(positionX);
        view.setTranslationY(positionY);

        if (translationListener != null) {
            translationListener
                    .onTranslate(new PointF(view.getPivotX(), view.getPivotY()),
                    new PointF(positionX, positionY));
        }
    }

    public void computeRenderOffset(View view, float pivotX, float pivotY) {
        if (view.getPivotX() == pivotX && view.getPivotY() == pivotY) {
            return;
        }

        float[] prevPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(prevPoint);

        view.setPivotX(pivotX);
        view.setPivotY(pivotY);

        float[] currPoint = {0.0f, 0.0f};
        view.getMatrix().mapPoints(currPoint);

        float offsetX = currPoint[0] - prevPoint[0];
        float offsetY = currPoint[1] - prevPoint[1];

        view.setTranslationX(view.getTranslationX() - offsetX);
        view.setTranslationY(view.getTranslationY() - offsetY);
    }
}
