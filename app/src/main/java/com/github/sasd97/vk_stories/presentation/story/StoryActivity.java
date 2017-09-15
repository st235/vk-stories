package com.github.sasd97.vk_stories.presentation.story;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.github.sasd97.vk_stories.events.OnTabSelectedListener;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;
import com.github.sasd97.vk_stories.utils.IntentResolver;
import com.github.sasd97.vk_stories.utils.PermissionResolver;

import java.io.File;

import javax.inject.Inject;

import sasd97.java_blog.xyz.background_picker.BackgroundPicker;
import sasd97.java_blog.xyz.gallery_picker.GalleryPicker;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryAlphaView;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryButton;
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

    private static final String FILL_ASPECT_RATIO = "0";
    private static final String SQUARE_ASPECT_RATIO = "1";

    private static final float MIRROR_ROTATE_ANGLE = 180.0f;

    private static final float POST_BACKFIELD_ALPHA = 1.0f;
    private static final float STORY_BACKFIELD_ALPHA = .92f;

    private ConstraintSet squareSet = new ConstraintSet();
    private ConstraintSet fullscreenSet = new ConstraintSet();

    private Button sendButton;
    private View stickerButton;
    private TabLayout tabLayout;
    private EditorView editorView;
    private StoryButton fontStyleButton;
    private GalleryPicker galleryPicker;
    private BackgroundPicker backgroundPicker;
    private ConstraintLayout constraintLayout;

    private StoryAlphaView toolbarBackfield;
    private StoryAlphaView backgroundPickerBackfield;


    @Inject Router router;
    @Inject IntentResolver intentResolver;
    @Inject StickerProvider stickerProvider;
    @Inject PermissionResolver permissionResolver;
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
        galleryPicker = findViewById(R.id.galleryPicker);
        stickerButton = findViewById(R.id.stickersButton);
        fontStyleButton = findViewById(R.id.fontStyleButton);
        backgroundPicker = findViewById(R.id.backgroundPicker);
        constraintLayout = findViewById(R.id.constraintLayout);
        toolbarBackfield = findViewById(R.id.toolbarBackfieldView);
        backgroundPickerBackfield = findViewById(R.id.backgroundPickerBackfield);
    }

    @Override
    protected void onViewsInitialized(Bundle savedInstanceState) {
        super.onViewsInitialized(savedInstanceState);

        setupLayout();
        setupTab();

        fontStyleButton.setStates(new int[]{NO_BACKFIELD, FULL_BACKFIELD, TRANSPARENT_BACKFIELD});
        fontStyleButton.setOnStateChangedListener(this);

        stickerButton.setOnClickListener(view -> onStickersOpen());
        backgroundPicker.setOnAddListener(v -> onGalleryPickerShow());

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
                    if (!permissionResolver.isStorageWritePermissionGranted()) {
                        permissionResolver.requestStorageWritePermission(this);
                        break;
                    }
                    presenter.onOpenCamera();
                    break;
                default:
                    editorView.setBackground(t);
                    break;
            }
        });

        sendButton.setOnClickListener(v -> {
            editorView.hideCursor();
            presenter.onSend(Renderer.renderView(editorView));
        });
    }

    @Override
    public void showTypeCursor() {
        editorView.showCursor();
    }

    private void setupLayout() {
        squareSet.clone(constraintLayout);
        fullscreenSet.clone(constraintLayout);

        squareSet.setDimensionRatio(R.id.editorView, SQUARE_ASPECT_RATIO);
        fullscreenSet.setDimensionRatio(R.id.editorView, FILL_ASPECT_RATIO);
    }

    private void setupTab() {
        String[] tabTitles = getResources().getStringArray(R.array.tabs_titles);

        for (String tabTitle: tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitle));
        }

        tabLayout.setRotationX(MIRROR_ROTATE_ANGLE);
        ViewGroup tabListed = (ViewGroup) tabLayout.getChildAt(0);

        for (int i = 0; i < tabListed.getChildCount(); i++) {
            tabListed.getChildAt(i).setRotationX(MIRROR_ROTATE_ANGLE);
        }

        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TransitionManager.beginDelayedTransition(constraintLayout);

                switch(tab.getPosition()) {
                    case 0:
                        squareSet.applyTo(constraintLayout);
                        onPostMode();
                        break;
                    case 1:
                        fullscreenSet.applyTo(constraintLayout);
                        onStoryMode();
                        break;
                };
            }
        });
    }

    private void onGalleryPickerShow() {
        if (!permissionResolver.isStorageReadPermissionGranted()) {
            permissionResolver.requestStorageReadPermission(this);
        }

        galleryPicker.show();
    }

    private void onPostMode() {
        toolbarBackfield.animateTo(POST_BACKFIELD_ALPHA);
        backgroundPickerBackfield.animateTo(POST_BACKFIELD_ALPHA);
        editorView.changeRecyclerBinMargin(0);
    }

    private void onStoryMode() {
        toolbarBackfield.animateTo(STORY_BACKFIELD_ALPHA);
        backgroundPickerBackfield.animateTo(STORY_BACKFIELD_ALPHA);
        editorView.changeRecyclerBinMargin(backgroundPicker.getHeight());
    }

    private void onStickersOpen() {
        StickerSheet stickerSheet = new StickerSheet();
        stickerSheet.setProvider(stickerProvider);
        stickerSheet.setOnItemClickListener((s, p) -> editorView.addSticker(s));
        stickerSheet.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onStateChanged(int state) {
        editorView.setTextColor(state);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionResolver.checkWriteStoragePermission(requestCode, grantResults)) {
            presenter.onOpenCamera();
            return;
        }

        if (permissionResolver.checkReadStoragePermission(requestCode, grantResults)) {
            galleryPicker.update();
            return;
        }

        String message = getString(R.string.permissionDenied);
        presenter.showPermissionDeniedMessage(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == IntentResolver.GALLERY_REQUEST_CODE) handleGalleryResults(data);
        if (requestCode == IntentResolver.CAMERA_REQUEST_CODE) handleCameraResults(data);
    }

    private void handleGalleryResults(@NonNull Intent intent) {
        Uri uri = intentResolver.getGalleryUri(intent);
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
