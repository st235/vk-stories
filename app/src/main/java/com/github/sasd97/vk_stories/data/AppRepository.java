package com.github.sasd97.vk_stories.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.File;

import io.reactivex.Single;

/**
 * Created by alexander on 12/09/2017.
 */

public interface AppRepository {
    Single<Integer> getUsedId();
    Single<Integer[]> uploadPhoto(int uid, @NonNull Bitmap bitmap);
    Single<String> createPost(int uid, int mediaId);

    void savePreview(@NonNull Bitmap bitmap);
    Bitmap getPreview();
}
