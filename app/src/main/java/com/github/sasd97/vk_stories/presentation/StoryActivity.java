package com.github.sasd97.vk_stories.presentation;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;

import javax.inject.Inject;

import sasd97.java_blog.xyz.background_picker.BackgroundPicker;
import sasd97.java_blog.xyz.gallery_picker.GalleryPicker;
import sasd97.java_blog.xyz.sticker_picker.StickerSheet;
import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

public class StoryActivity extends BaseActivity implements StoryView {

    private View smileButton;
    private TabLayout tabLayout;
    private GalleryPicker galleryPicker;
    private BackgroundPicker backgroundPicker;

    @Inject StickerProvider stickerProvider;
    @Inject @InjectPresenter StoryPresenter presenter;

    @ProvidePresenter
    public StoryPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_story;
    }

    @Override
    protected void onDaggerInit() {
        VkStoriesApp
                .get(this)
                .getComponent()
                .inject(this);
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        tabLayout = findViewById(R.id.tabLayout);
        smileButton = findViewById(R.id.smileButton);
        galleryPicker = findViewById(R.id.galleryPicker);
        backgroundPicker = findViewById(R.id.backgroundPicker);
    }

    @Override
    protected void onViewsInitialized(Bundle savedInstanceState) {
        super.onViewsInitialized(savedInstanceState);

        smileButton.setOnClickListener(view -> {
            StickerSheet stickerSheet = new StickerSheet();
            stickerSheet.setProvider(stickerProvider);
            stickerSheet.show(getSupportFragmentManager(), null);
        });

        String[] tabTitles = getResources().getStringArray(R.array.tabs_titles);
        for (String tabTitle: tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitle));
        }

        backgroundPicker.setOnAddListener(v -> {
            galleryPicker.setVisibility(View.VISIBLE);
        });

        backgroundPicker.setOnItemClickListener((b, p) -> {
            galleryPicker.setVisibility(View.GONE);
        });
    }
}
