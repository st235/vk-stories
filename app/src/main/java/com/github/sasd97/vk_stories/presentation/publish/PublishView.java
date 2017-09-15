package com.github.sasd97.vk_stories.presentation.publish;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by alexander on 13/09/2017.
 */

@StateStrategyType(SkipStrategy.class)
public interface PublishView extends MvpView {
    void showSuccess();
    void showCancelled();
    void showError();
}
