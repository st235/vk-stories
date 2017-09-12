package sasd97.java_blog.xyz.libs_touchlistener.listeners;

import android.view.View;

/**
 * Created by Narek on 31.08.2017.
 */

public interface OnRemoveListener {
    void onStart();
    void onIntercept(View view);
    void onRemove(View view);
    void onCanceled(View view);
    void onFinish(View view);
}
