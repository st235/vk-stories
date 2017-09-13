package com.github.sasd97.vk_stories.presentation.story;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.lib_router.satellites.ActivitySatellite;
import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;

import javax.inject.Inject;

import sasd97.java_blog.xyz.background_picker.BackgroundPicker;
import sasd97.java_blog.xyz.gallery_picker.GalleryPicker;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryAlphaView;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryButton;
import sasd97.java_blog.xyz.libs_common.utils.utils.Renderer;
import sasd97.java_blog.xyz.libs_editorview.EditorView;
import sasd97.java_blog.xyz.sticker_picker.StickerSheet;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

public class StoryActivity extends BaseActivity
        implements StoryView, StoryButton.OnStateChangedListener {

    private static final int NO_BACKFIELD = 0;
    private static final int TRANSPARENT_BACKFIELD = 1;
    private static final int FULL_BACKFIELD = 2;

    private ConstraintSet squareSet = new ConstraintSet();
    private ConstraintSet fullscreenSet = new ConstraintSet();

    private EditorView editorView;
    private StoryButton storyButton;
    private Button sendButton;

    private View smileButton;
    private StoryAlphaView toolbarBackground;
    private TabLayout tabLayout;
    private GalleryPicker galleryPicker;
    private BackgroundPicker backgroundPicker;
    private ConstraintLayout constraintLayout;

    @Inject Router router;
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

        router.attachSatellite(new ActivitySatellite(this));
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        tabLayout = findViewById(R.id.tabLayout);
        editorView = findViewById(R.id.editorView);
        sendButton = findViewById(R.id.sendButton);
        smileButton = findViewById(R.id.smileButton);
        storyButton = findViewById(R.id.fontTextView);
        galleryPicker = findViewById(R.id.galleryPicker);
        backgroundPicker = findViewById(R.id.backgroundPicker);
        constraintLayout = findViewById(R.id.constraintLayout);
        toolbarBackground = findViewById(R.id.alphaView);
    }

    @Override
    protected void onViewsInitialized(Bundle savedInstanceState) {
        super.onViewsInitialized(savedInstanceState);

        storyButton.setStates(new int[]{NO_BACKFIELD, FULL_BACKFIELD, TRANSPARENT_BACKFIELD});
        storyButton.setOnStateChangedListener(this);

        squareSet.clone(constraintLayout);
        fullscreenSet.clone(constraintLayout);

        squareSet.setDimensionRatio(R.id.editorView, "1");
        fullscreenSet.setDimensionRatio(R.id.editorView, "0");

        smileButton.setOnClickListener(view -> {
            StickerSheet stickerSheet = new StickerSheet();
            stickerSheet.setProvider(stickerProvider);
            stickerSheet.setOnItemClickListener((s, p) -> editorView.addSticker(s));
            stickerSheet.show(getSupportFragmentManager(), null);
        });

        setupTab();

        backgroundPicker.setOnAddListener(v -> {
            galleryPicker.setVisibility(View.VISIBLE);
        });

        backgroundPicker.setOnItemClickListener((b, p) -> {
            galleryPicker.setVisibility(View.GONE);
            editorView.setBackground(b);
        });

        galleryPicker.setOnItemClickListener((t, p) -> {
            editorView.setBackground(t);
        });

        sendButton.setOnClickListener(v -> {
            presenter.onSend(Renderer.renderView(editorView));
        });
    }

    @Override
    public void onStateChanged(int state) {
        switch (state) {
            case NO_BACKFIELD:
                editorView.setTextColor(Color.WHITE, Color.TRANSPARENT);
                break;
            case TRANSPARENT_BACKFIELD:
                editorView.setTextColor(Color.WHITE, Color.parseColor("#5cffffff"));
                break;
            case FULL_BACKFIELD:
                editorView.setTextColor(Color.BLACK, Color.WHITE);
                break;
        }
    }

    private void setupTab() {
        String[] tabTitles = getResources().getStringArray(R.array.tabs_titles);
        for (String tabTitle: tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitle));
        }

        tabLayout.setRotationX(180.0f);
        ViewGroup tabListed = (ViewGroup) tabLayout.getChildAt(0);

        for (int i = 0; i < tabListed.getChildCount(); i++) {
            tabListed.getChildAt(i).setRotationX(180.0f);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TransitionManager.beginDelayedTransition(constraintLayout);

                switch(tab.getPosition()) {
                    case 0:
                        toolbarBackground.animateTo(1.0f);
                        squareSet.applyTo(constraintLayout);
                        break;
                    case 1:
                        toolbarBackground.animateTo(0.92f);
                        fullscreenSet.applyTo(constraintLayout);
                        break;
                };
            }
        });
    }
}
