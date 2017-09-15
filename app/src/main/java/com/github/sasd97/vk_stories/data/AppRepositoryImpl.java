package com.github.sasd97.vk_stories.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.sasd97.vk_stories.data.net.VkApiWrapper;

import io.reactivex.Single;

/**
 * Created by alexander on 12/09/2017.
 */

public class AppRepositoryImpl implements AppRepository {

    private Bitmap preview;
    private VkApiWrapper vkApiWrapper;

    public AppRepositoryImpl(@NonNull VkApiWrapper vkApiWrapper) {
        this.vkApiWrapper = vkApiWrapper;
    }

    @Override
    public Single<Integer> getUsedId() {
        return vkApiWrapper.getUserId();
    }

    @Override
    public Single<Integer[]> uploadPhoto(int uid, @NonNull Bitmap bitmap) {
        return vkApiWrapper.uploadPhoto(uid, bitmap);
    }

    @Override
    public Single<String> createPost(int uid, int mediaId) {
        return vkApiWrapper.createPost(uid, mediaId);
    }

    @Override
    public void savePreview(@NonNull Bitmap preview) {
        this.preview = preview;
    }

    @Override
    public Bitmap getPreview() {
        return preview;
    }
}
