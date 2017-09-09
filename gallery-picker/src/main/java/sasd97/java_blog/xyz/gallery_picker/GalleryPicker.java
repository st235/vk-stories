package sasd97.java_blog.xyz.gallery_picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import sasd97.java_blog.xyz.gallery_picker.adapters.GalleryAdapter;
import sasd97.java_blog.xyz.gallery_picker.providers.ImageProvider;

/**
 * Created by alexander on 09/09/2017.
 */

public class GalleryPicker extends FrameLayout {

    private RecyclerView recyclerView;
    private ImageProvider imageProvider;
    private GalleryAdapter galleryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public GalleryPicker(@NonNull Context context) {
        this(context, null);
    }

    public GalleryPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryPicker(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GalleryPicker(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }

    private void onInit() {
        recyclerView = new RecyclerView(getContext());
        layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        galleryAdapter = new GalleryAdapter();
        imageProvider = new ImageProvider(getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        recyclerView.setAdapter(galleryAdapter);
        recyclerView.setLayoutManager(layoutManager);
        addView(recyclerView, params);

        galleryAdapter.addAll(imageProvider.provide());
    }
}