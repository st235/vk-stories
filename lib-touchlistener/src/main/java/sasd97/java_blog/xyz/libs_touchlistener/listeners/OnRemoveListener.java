package sasd97.java_blog.xyz.libs_touchlistener.listeners;

import android.view.View;

/**
 * Created by alexander on 09/09/2017.
 */

public interface OnRemoveListener {
    void onStart();
    void onIntercept(View view);
    void onRemove(View view);
    void onCanceled(View view);
    void onFinish(View view);
}
