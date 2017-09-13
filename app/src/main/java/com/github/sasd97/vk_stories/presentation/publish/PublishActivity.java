package com.github.sasd97.vk_stories.presentation.publish;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.lib_router.satellites.ActivitySatellite;
import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;

import javax.inject.Inject;


public class PublishActivity extends BaseActivity implements PublishView {

    @Inject
    Router router;

    @Inject
    @InjectPresenter
    PublishPresenter presenter;

    private ProgressBar progressBar;

    private View successView;
    private Button cancelButton;
    private Button tryAgainButton;
    private TextView followingText;

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

        router.attachSatellite(new ActivitySatellite(this));
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        progressBar = findViewById(R.id.loadingBar);
        tryAgainButton = findViewById(R.id.tryAgain);
        successView = findViewById(R.id.successView);
        cancelButton = findViewById(R.id.cancelButton);
        followingText = findViewById(R.id.followingText);
    }

    @Override
    public void showSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        followingText.setText(R.string.published);
        successView.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.GONE);
        tryAgainButton.setVisibility(View.VISIBLE);
    }
}
