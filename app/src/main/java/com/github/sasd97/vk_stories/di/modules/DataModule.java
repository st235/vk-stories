package com.github.sasd97.vk_stories.di.modules;

import com.github.sasd97.vk_stories.data.AppRepository;
import com.github.sasd97.vk_stories.data.AppRepositoryImpl;
import com.github.sasd97.vk_stories.data.net.VkApiWrapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander on 12/09/2017.
 */

@Module
public class DataModule {

    @Singleton
    @Provides
    VkApiWrapper vkApiWrapper() {
        return new VkApiWrapper();
    }

    @Singleton
    @Provides
    AppRepository provideRepository(VkApiWrapper vkApiWrapper) {
        return new AppRepositoryImpl(vkApiWrapper);
    }
}
