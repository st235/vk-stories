package com.github.sasd97.vk_stories.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.arellomobile.mvp.MvpAppCompatActivity;

/**
 * Created by alexander on 10/09/2017.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onDaggerInit();
        onBeforeInit();

        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        onInitViews();
        onViewsInitialized(savedInstanceState);
    }

    @LayoutRes protected abstract int getLayout();

    /***
     * Calls before all checks
     * Note: use it for important checks (splash screen, for example)
     */
    protected void onBeforeInit() {
    }

    /***
     * Calls when dagger need to inject its own element from graph to <b>field</b>
     * Note: override it only when needed fields injection!
     */
    protected void onDaggerInit() {
    }

    /***
     * Calls before any another methods call.
     * Needs to bind views variables and layout representation
     */
    protected void onInitViews() {
    }

    /**
     * Calls when views initialized to setup
     * @param savedInstanceState state
     */
    protected void onViewsInitialized(Bundle savedInstanceState) {
    }
}
