package sasd97.java_blog.xyz.sticker_picker;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.sticker_picker.adapters.StickersAdapter;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;
import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;
import sasd97.java_blog.xyz.sticker_picker.providers.StickerProvider;

/**
 * Created by alexander on 09/09/2017.
 */

public class StickerSheet extends BottomSheetDialogFragment {

    private StickerProvider provider;
    private RecyclerView recyclerView;
    private StickersAdapter stickersAdapter;
    private GridLayoutManager gridLayoutManager;
    private OnItemClickListener<Sticker> listener;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void setProvider(@NonNull StickerProvider provider) {
        this.provider = provider;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_sticker, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();

        if (behavior == null || !(behavior instanceof BottomSheetBehavior)) return;
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
        bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);

        recyclerView = contentView.findViewById(R.id.recyclerView);
        stickersAdapter = new StickersAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), 4);

        recyclerView.setAdapter(stickersAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        StickerPack pack = getFirstStickerPack();
        stickersAdapter.addAll(pack.getStickers());
        stickersAdapter.setListener(new OnItemClickListener<Sticker>() {
            @Override
            public void onClick(Sticker sticker, int position) {
                if (listener != null) listener.onClick(sticker, position);
                dismiss();
            }
        });
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener<Sticker> listener) {
        this.listener = listener;
    }

    private StickerPack getFirstStickerPack() {
        return provider.provide().get(0);
    }
}
