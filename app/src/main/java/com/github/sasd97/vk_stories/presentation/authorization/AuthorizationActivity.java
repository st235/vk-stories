package com.github.sasd97.vk_stories.presentation.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.sasd97.lib_router.Router;
import com.github.sasd97.lib_router.satellites.ActivitySatellite;
import com.github.sasd97.vk_stories.R;
import com.github.sasd97.vk_stories.VkStoriesApp;
import com.github.sasd97.vk_stories.presentation.base.BaseActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;

public class AuthorizationActivity extends BaseActivity implements AuthorizationView {

    private Button loginButton;

    @Inject
    public Router router;

    @Inject
    @InjectPresenter
    public AuthorizationPresenter presenter;

    @ProvidePresenter
    public AuthorizationPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_authorization;
    }

    @Override
    protected void onBeforeInit() {
        super.onBeforeInit();
        router.attachSatellite(new ActivitySatellite(this));
        setTheme(R.style.AppTheme);
        presenter.chooseAuthorization();
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

        loginButton = findViewById(R.id.login_button);
    }

    @Override
    protected void onViewsInitialized(Bundle savedInstanceState) {
        super.onViewsInitialized(savedInstanceState);
        loginButton.setOnClickListener(v -> VKSdk.login(this, "wall", VKApiConst.POSTS, VKApiConst.PHOTOS));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                presenter.goToStoriesEditor();
            }

            @Override
            public void onError(VKError error) {
                String message = getString(R.string.error);
                presenter.showErrorMessage(message);
            }
        });
    }
}
