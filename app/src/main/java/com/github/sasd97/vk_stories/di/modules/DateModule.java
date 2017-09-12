package com.github.sasd97.vk_stories.di.modules;

import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.data.AppRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander on 12/09/2017.
 */

@Module
public class DateModule {

    @Singleton
    @Provides
    AppRepository provideRepository() {
        return new AppRepositoryImpl();
    }
}
