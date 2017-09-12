package com.github.sasd97.vk_stories.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by alexander on 12/09/2017.
 */

public interface AppRepository {
    void savePreview(@NonNull Bitmap bitmap);
    Bitmap getPreview();
}
