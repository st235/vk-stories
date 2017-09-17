package com.github.sasd97.vk_stories.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sasd97.java_blog.xyz.libs_common.utils.providers.CameraImageProvider;
import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

/**
 * Created by alexander on 09/09/2017.
 */

@Module
public class ProviderModule {

    @Provides
    @Singleton
    StickerProvider provideStickerProvider(Context context) {
        return new StickerProvider(context.getAssets());
    }

    @Provides
    @Singleton
    CameraImageProvider provideCameraImageProvider(Context context) {
        return new CameraImageProvider(context);
    }
}
