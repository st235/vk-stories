package com.github.sasd97.vk_stories.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

/**
 * Created by alexander on 10/09/2017.
 */

@InjectViewState
public class StoryPresenter extends MvpPresenter<StoryView> {

    @Inject
    public StoryPresenter() {
    }
}
