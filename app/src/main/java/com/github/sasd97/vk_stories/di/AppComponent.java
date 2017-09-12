package com.github.sasd97.vk_stories.di;

import com.github.sasd97.vk_stories.di.modules.AppModule;
import com.github.sasd97.vk_stories.di.modules.DateModule;
import com.github.sasd97.vk_stories.di.modules.ProviderModule;
import com.github.sasd97.vk_stories.presentation.authorization.AuthorizationActivity;
import com.github.sasd97.vk_stories.presentation.publish.PublishActivity;
import com.github.sasd97.vk_stories.presentation.story.StoryActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alexander on 08/09/2017.
 */

@Singleton
@Component(modules = {AppModule.class, DateModule.class, ProviderModule.class})
public interface AppComponent {
    void inject(StoryActivity activity);
    void inject(PublishActivity activity);
    void inject(AuthorizationActivity activity);
}
