package com.github.sasd97.vk_stories.presentation.publish;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;
import com.github.sasd97.vk_stories.data.net.VkApiWrapper;

import javax.inject.Inject;


public class PublishActivity extends BaseActivity implements PublishView {

    private ImageView preview;

    @Inject
    @InjectPresenter
    PublishPresenter presenter;

    @ProvidePresenter
    public PublishPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_publish;
    }

    @Override
    protected void onDaggerInit() {
        super.onDaggerInit();
        VkStoriesApp
                .get(this)
                .getComponent()
                .inject(this);
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();
        preview = findViewById(R.id.imageView3);
    }

    @Override
    public void showBitmap(@NonNull Bitmap bitmap) {
        preview.setImageBitmap(bitmap);
    }
}
