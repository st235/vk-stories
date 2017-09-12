package com.github.sasd97.vk_stories.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Created by alexander on 12/09/2017.
 */

public class AppRepositoryImpl implements AppRepository {

    private WeakReference<Bitmap> previewReference;

    @Override
    public void savePreview(@NonNull Bitmap bitmap) {
        previewReference = new WeakReference<>(bitmap);
    }

    @Override
    public Bitmap getPreview() {
        return previewReference.get();
    }
}
