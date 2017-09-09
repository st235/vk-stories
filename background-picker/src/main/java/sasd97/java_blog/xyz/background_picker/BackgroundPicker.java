package sasd97.java_blog.xyz.background_picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.github.sasd97.lib_gradientview.models.Gradient;

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.adapters.BackgroundAdapter;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.background_picker.models.ImageItem;
import sasd97.java_blog.xyz.background_picker.models.PlusItem;
import sasd97.java_blog.xyz.background_picker.providers.GradientProvider;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.background_picker.providers.ThumbnailProvider;

/**
 * Created by alexander on 08/09/2017.
 */

public class BackgroundPicker extends FrameLayout {

    private RecyclerView recyclerView;
    private BackgroundAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private Provider<List<Gradient>> gradientsProvider;
    private Provider<List<Integer>> imagesProvider;

    public BackgroundPicker(@NonNull Context context) {
        super(context);
        onInit();
    }

    public BackgroundPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public BackgroundPicker(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackgroundPicker(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    public void onInit() {
        adapter = new BackgroundAdapter();
        recyclerView = new RecyclerView(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        gradientsProvider = new GradientProvider(getContext());
        imagesProvider = new ThumbnailProvider();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        addView(recyclerView, params);
        initContent();
    }

    private void initContent() {
        addGradients();
        addImages();
        addSpecial();
    }

    private void addGradients() {
        List<BackgroundItem> items = new ArrayList<>();

        for (Gradient gradient: gradientsProvider.provide()) {
            items.add(new GradientItem(gradient));
        }

        adapter.addAll(items);
    }

    private void addImages() {
        List<BackgroundItem> items = new ArrayList<>();

        for (Integer image: imagesProvider.provide()) {
            items.add(new ImageItem(image));
        }

        adapter.addAll(items);
    }

    private void addSpecial() {
        adapter.add(new PlusItem());
    }
}
