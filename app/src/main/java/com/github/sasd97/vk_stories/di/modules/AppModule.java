package com.github.sasd97.vk_stories.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.sasd97.lib_router.BaseRouter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.vk_stories.utils.RxSchedulers;
import com.github.sasd97.vk_stories.utils.RxSchedulersImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander on 09/09/2017.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return new BaseRouter();
    }

    @Provides
    @Singleton
    RxSchedulers provideScheduler() {
        return new RxSchedulersImpl();
    }
}
