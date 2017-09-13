package sasd97.java_blog.xyz.libs_touchlistener.listeners;

import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by alexander on 09/09/2017.
 */

public final class LongPressGestureListener extends GestureDetector.SimpleOnGestureListener {

    private View view;
    private View.OnLongClickListener listener;

    public void setListener(View.OnLongClickListener listener) {
        this.listener = listener;
    }

    public void attachView(@NonNull View view) {
        this.view = view;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if (view == null) return;
        if (listener == null) return;
        listener.onLongClick(view);
    }
}
