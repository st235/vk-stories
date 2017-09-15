package com.github.sasd97.vk_stories.presentation.publish;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private View successView;
    private Button cancelButton;
    private Button tryAgainButton;
    private TextView followingText;
    private ProgressBar progressBar;

    @Inject
    Router router;

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

        router.attachSatellite(new ActivitySatellite(this));
    }

    @Override
    protected void onInitViews() {
        super.onInitViews();

        progressBar = findViewById(R.id.loadingBar);
        successView = findViewById(R.id.successView);
        tryAgainButton = findViewById(R.id.tryAgain);
        cancelButton = findViewById(R.id.cancelButton);
        followingText = findViewById(R.id.followingText);
    }

    @Override
    protected void onViewsInitialized(Bundle savedInstanceState) {
        super.onViewsInitialized(savedInstanceState);

        tryAgainButton.setOnClickListener(v -> presenter.goToCreateNew());
        cancelButton.setOnClickListener(v -> presenter.cancelRequest());
    }

    @Override
    public void showSuccess() {
        cancelButton.setVisibility(View.GONE);
        successView.setVisibility(View.VISIBLE);
        followingText.setText(R.string.published);
        progressBar.setVisibility(View.INVISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCancelled() {
        cancelButton.setVisibility(View.GONE);
        followingText.setText(R.string.canceled);
        progressBar.setVisibility(View.INVISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        String message = getString(R.string.error);
        presenter.showErrorMessage(message);
    }
}
