package com.github.sasd97.vk_stories.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;

import javax.inject.Inject;

import sasd97.java_blog.xyz.sticker_picker.StickerSheet;
import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

public class StoryActivity extends AppCompatActivity {

    @Inject StickerProvider stickerProvider;

    private View smileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        VkStoriesApp
                .get(this)
                .getComponent()
                .inject(this);

        smileButton = findViewById(R.id.smileButton);
        smileButton.setOnClickListener(view -> {
            StickerSheet stickerSheet = new StickerSheet();
            stickerSheet.setProvider(stickerProvider);
            stickerSheet.show(getSupportFragmentManager(), null);
        });
    }
}