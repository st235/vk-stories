package com.github.sasd97.vk_stories.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;

import javax.inject.Inject;

import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

public class StoryActivity extends AppCompatActivity {

    @Inject StickerProvider stickerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        VkStoriesApp
                .get(this)
                .getComponent()
                .inject(this);

        for (StickerPack pack: stickerProvider.provide()) Log.d("TWITTER", pack.toString());
    }
}
