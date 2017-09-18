package sasd97.java_blog.xyz.libs_touchlistener.listeners;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by alexander on 09/09/2017.
 */

public final class ClickGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
       return true;
    }
}
