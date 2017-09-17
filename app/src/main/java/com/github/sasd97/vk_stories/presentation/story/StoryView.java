package com.github.sasd97.vk_stories.presentation.story;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

/**
 * Created by alexander on 10/09/2017.
 */

public interface StoryView extends MvpView {
    void addCameraImage(@NonNull Uri uri);
    void showTypeCursor();
}
