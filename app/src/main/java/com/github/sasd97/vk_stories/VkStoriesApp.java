package com.github.sasd97.vk_stories;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.sasd97.vk_stories.di.AppComponent;
import com.github.sasd97.vk_stories.di.DaggerAppComponent;
import com.github.sasd97.vk_stories.di.modules.AppModule;

/**
 * Created by alexander on 08/09/2017.
 */

public class VkStoriesApp extends Application {

    private AppComponent component;

    public static VkStoriesApp get(@NonNull Context context) {
        return (VkStoriesApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = buildDi();
    }

    public AppComponent getComponent() {
        return component;
    }

    private AppComponent buildDi() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
