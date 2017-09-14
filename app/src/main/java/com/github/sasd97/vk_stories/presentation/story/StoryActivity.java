package com.github.sasd97.vk_stories.presentation.story;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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

import java.io.File;

import javax.inject.Inject;

import sasd97.java_blog.xyz.background_picker.BackgroundPicker;
import sasd97.java_blog.xyz.gallery_picker.GalleryPicker;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryAlphaView;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryButton;
import sasd97.java_blog.xyz.libs_common.utils.utils.IntentResolver;
import sasd97.java_blog.xyz.libs_common.utils.utils.Renderer;
import sasd97.java_blog.xyz.libs_editorview.EditorView;
import sasd97.java_blog.xyz.libs_editorview.OnTextChangedListener;
import sasd97.java_blog.xyz.sticker_picker.StickerSheet;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

import static sasd97.java_blog.xyz.libs_editorview.EditorView.FULL_BACKFIELD;
import static sasd97.java_blog.xyz.libs_editorview.EditorView.NO_BACKFIELD;
import static sasd97.java_blog.xyz.libs_editorview.EditorView.TRANSPARENT_BACKFIELD;

public class StoryActivity extends BaseActivity
        implements StoryView,
        StoryButton.OnStateChangedListener {

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

        backgroundPicker.setOnAddListener(v -> galleryPicker.show());

        backgroundPicker.setOnItemClickListener((b, p) -> {
            galleryPicker.hide();
            editorView.setBackground(b);
        });

        editorView.setOnTextListener(new OnTextChangedListener() {
            @Override
            public void onEmpty() {
                sendButton.setEnabled(false);
                sendButton.setBackgroundResource(R.drawable.left_highlighted_background_disable);
            }

            @Override
            public void onTextPresent() {
                sendButton.setEnabled(true);
                sendButton.setBackgroundResource(R.drawable.left_highlighted_background);
            }
        });

        galleryPicker.setOnItemClickListener((t, p) -> {
            switch (t.getType()) {
                case Tile.GALLERY:
                    presenter.onOpenGallery();
                    break;
                case Tile.CAMERA:
                    ActivityCompat.requestPermissions(this,
                            new String[] {
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 1);
                    presenter.onOpenCamera();
                    break;
                default:
                    editorView.setBackground(t);
                    break;
            }
        });

        sendButton.setOnClickListener(v -> {
            presenter.onSend(Renderer.renderView(editorView));
        });
    }

    @Override
    public void onStateChanged(int state) {
        editorView.setTextColor(state);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == IntentResolver.GALLERY_REQUEST_CODE) handleGalleryResults(data);
        if (requestCode == IntentResolver.CAMERA_REQUEST_CODE) handleCameraResults(data);
    }

    private void handleGalleryResults(@NonNull Intent intent) {
        Uri uri = IntentResolver.getGalleryUri(intent);
        Tile tile = new Tile(uri);
        editorView.setBackground(tile);
    }

    private void handleCameraResults(@NonNull Intent intent) {
        File file = presenter.getTempFile();
        Uri uri = Uri.fromFile(file);
        Tile tile = new Tile(uri);
        editorView.setBackground(tile);
    }

    @Override
    public void onBackPressed() {
        if (galleryPicker.isOpen()) {
            galleryPicker.hide();
            return;
        }

        super.onBackPressed();
    }
}
