package sasd97.java_blog.xyz.background_picker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.adapters.BackgroundAdapter;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;

/**
 * Created by alexander on 08/09/2017.
 */

public class BackgroundPicker extends FrameLayout {

    private RecyclerView recyclerView;
    private BackgroundAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

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

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        addView(recyclerView, params);

        List<BackgroundItem> items = new ArrayList<>();
        items.add(new GradientItem(Color.parseColor("#f8a6ff"), Color.parseColor("#6c6cd9")));
        items.add(new GradientItem(Color.parseColor("#ff3355"), Color.parseColor("#990f6b")));

        adapter.addAll(items);
    }
}
