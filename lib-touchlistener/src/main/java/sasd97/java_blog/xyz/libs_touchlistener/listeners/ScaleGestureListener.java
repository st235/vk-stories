package sasd97.java_blog.xyz.libs_touchlistener.listeners;

import android.support.annotation.NonNull;
import android.view.View;

import sasd97.java_blog.xyz.libs_touchlistener.MultiTouchListener;
import sasd97.java_blog.xyz.libs_touchlistener.ScaleGestureDetector;
import sasd97.java_blog.xyz.libs_touchlistener.ViewTransformer;
import sasd97.java_blog.xyz.libs_touchlistener.models.Transform;
import sasd97.java_blog.xyz.libs_touchlistener.models.Vector2;

/**
 * Created by alexander on 26/08/2017.
 */

public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private float pivotX;
    private float pivotY;
    private Vector2 prevSpanVector = new Vector2();

    private MultiTouchListener listener;
    private ViewTransformer transformer;

    public ScaleGestureListener(@NonNull MultiTouchListener listener,
                                @NonNull ViewTransformer transformer) {
        this.listener = listener;
        this.transformer = transformer;
    }

    @Override
    public boolean onScaleBegin(View view, ScaleGestureDetector detector) {
        pivotX = detector.getFocusX();
        pivotY = detector.getFocusY();
        prevSpanVector.set(detector.getCurrentSpanVector());
        return true;
    }

    @Override
    public boolean onScale(View view, ScaleGestureDetector detector) {
        Transform info = new Transform();
        info.deltaScale = listener.isScaleEnabled() ? detector.getScaleFactor() : 1.0f;
        info.deltaAngle = listener.isRotateEnabled() ? Vector2.getAngle(prevSpanVector, detector.getCurrentSpanVector()) : 0.0f;
        info.deltaX = listener.isTranslateEnabled() ? detector.getFocusX() - pivotX : 0.0f;
        info.deltaY = listener.isTranslateEnabled() ? detector.getFocusY() - pivotY : 0.0f;
        info.pivotX = pivotX;
        info.pivotY = pivotY;
        info.minimumScale = listener.getMinimumScale();
        info.maximumScale = listener.getMaximumScale();

        transformer.move(view, info);
        return false;
    }
}
