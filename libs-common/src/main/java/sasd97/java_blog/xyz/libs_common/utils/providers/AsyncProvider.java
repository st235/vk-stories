package sasd97.java_blog.xyz.libs_common.utils.providers;

import android.support.annotation.NonNull;

/**
 * Created by alexander on 17/09/2017.
 */

public interface AsyncProvider<I, O> {
    interface ProviderListener<O> {
        void onProvide(O o);
    }

    O provide(@NonNull I i);
}
