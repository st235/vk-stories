package com.github.sasd97.vk_stories.di;

import com.github.sasd97.vk_stories.di.modules.AppModule;
import com.github.sasd97.vk_stories.di.modules.ProviderModule;
import com.github.sasd97.vk_stories.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alexander on 08/09/2017.
 */

@Singleton
@Component(modules = {AppModule.class, ProviderModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
