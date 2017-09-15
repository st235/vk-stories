package sasd97.java_blog.xyz.libs_editorview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.sasd97.lib_gradientview.GradientView;
import com.github.sasd97.lib_gradientview.models.Gradient;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.background_picker.models.ImageItem;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.components.spans.RoundedBackgroundSpan;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryBinView;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryEditText;
import sasd97.java_blog.xyz.libs_common.utils.components.StorySticker;
import sasd97.java_blog.xyz.libs_common.utils.models.ComplementaryColor;
import sasd97.java_blog.xyz.libs_common.utils.models.ScalableImage;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_touchlistener.MultiTouchListener;
import sasd97.java_blog.xyz.libs_touchlistener.RemoveRegionProvider;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRemoveListener;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;

/**
 * Created by alexander on 10/09/2017.
 */

public class EditorView extends RelativeLayout {

    public static final int NO_BACKFIELD = 0;
    public static final int TRANSPARENT_BACKFIELD = 1;
    public static final int FULL_BACKFIELD = 2;

    private int currentTextMode = 0;

    private GradientView gradientView;
    private StoryEditText storyEditText;
    private StoryBinView recyclerBinView;

    private List<View> stickers = new ArrayList<>();
    private ArrayDeque<View> complexBackground = new ArrayDeque<>();
    private ComplementaryColor currentColor = ComplementaryColor.BLACK;

    //region constructors
    public EditorView(@NonNull Context context) {
        super(context);
        onInit();
    }

    public EditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public EditorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInit();
    }
    //endregion

    //region setters & getters
    public void setOnTextListener(@NonNull OnTextChangedListener listener) {
        this.storyEditText.addTextChangedListener(listener);
    }
    //endregion

    //region features
    public void setTextColor(int state) {
        int textColor;
        int backgroundColor;
        currentTextMode = state;

        switch (state) {
            case NO_BACKFIELD:
                textColor = currentColor.getPrimary();
                backgroundColor = Color.TRANSPARENT;
                break;
            case TRANSPARENT_BACKFIELD:
                textColor = currentColor.getPrimary();
                backgroundColor = currentColor.getPrimaryWithScaledAlpha(0.36f);
                break;
            default:
                textColor = currentColor.getContrast();
                backgroundColor = currentColor.getPrimary();
                break;
        }

        storyEditText.setSpan(new RoundedBackgroundSpan(backgroundColor, textColor, 4.0f));
    }

    public void setBackground(@NonNull BackgroundItem item) {
        currentColor = item.getComplementaryColor();
        setTextColor(currentTextMode);
        dropComplexBackgroundStack();

        if (item.getType() == BackgroundItem.GRADIENT) {
            gradientView.setGradient(((GradientItem) item).getGradient());
            gradientView.setImageBitmap(null);
            return;
        }

        if (item.getType() == BackgroundItem.IMAGE) {
            ScalableImage si = ((ImageItem) item).getImage();
            for (ScalableImage.Pair pair: si.getElements()) addBackgroundViews(pair);
        }

        bringStickersToFront();
    }

    public void setBackground(@NonNull Tile tile) {
        currentColor = tile.getComplementaryColor();
        setTextColor(currentTextMode);
        dropComplexBackgroundStack();

        gradientView.setGradient(null);
        gradientView.setImageURI(tile.getUri());
    }

    public void addSticker(@NonNull Sticker sticker) {
        View stickerView = addStickerView(sticker.getUri());
        stickers.add(stickerView);
    }
    //endregion

    public void changeRecyclerBinMargin(int margin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerBinView.getLayoutParams();

        params.bottomMargin = margin + (int) Dimens.dpToPx(16.0f);
    }

    public void hideCursor() {
        storyEditText.setCursorVisible(false);
    }

    public void showCursor() {
        storyEditText.setCursorVisible(true);
    }

    private void onInit() {
        addBackground();
        addEditText();
        addBinView();
    }

    private void addBackgroundViews(@NonNull ScalableImage.Pair pair) {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(pair.scaleType);

        Glide
                .with(getContext())
                .load(pair.resource)
                .into(iv);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                pair.width, pair.height
        );

        params.addRule(pair.gravityRule);
        addView(iv, params);
        complexBackground.add(iv);
        bringChildToFront(storyEditText);
    }

    private void addBinView() {
        recyclerBinView = new StoryBinView(getContext());
        recyclerBinView.setBins(R.drawable.ic_fab_trash, R.drawable.ic_fab_trash_released);
        recyclerBinView.setVisibility(INVISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) Dimens.dpToPx(48), (int) Dimens.dpToPx(48));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.bottomMargin = (int) Dimens.dpToPx(12);
        addView(recyclerBinView, params);
    }

    private void addBackground() {
        gradientView = new GradientView(getContext());
        gradientView.setRadius(0.0f);
        gradientView.setGradient(new Gradient(Color.WHITE, Color.WHITE));
        gradientView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(gradientView, generateCenterLP(ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void addEditText() {
        storyEditText = new StoryEditText(getContext());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        int margin = (int) Dimens.dpToPx(16);
        params.addRule(CENTER_IN_PARENT);
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.bottomMargin = margin;

        addView(storyEditText, params);
    }

    private ImageView addStickerView(@NonNull Uri uri) {
        final StorySticker imageView = new StorySticker(getContext());

        Glide
                .with(getContext())
                .load(uri)
                .apply(new RequestOptions().centerInside().override(350))
                .into(imageView);

        final MultiTouchListener listener = new MultiTouchListener(getContext());

        listener.setRemoveListener(new OnRemoveListener() {
            @Override
            public void onStart() {
                bringChildToFront(recyclerBinView);
                recyclerBinView.show();
            }

            @Override
            public void onIntercept(View view) {
                recyclerBinView.release();
                imageView.prepareToDelete();
            }

            @Override
            public void onRemove(View view) {
                removeView(imageView);
                recyclerBinView.hide();
            }

            @Override
            public void onCanceled(View view) {
                recyclerBinView.close();
                imageView.release();
            }

            @Override
            public void onFinish(View view) {
                recyclerBinView.hide();
            }

        }, new RemoveRegionProvider(recyclerBinView));

        listener.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.setRemoveEnabled(true);
                return false;
            }
        });

        imageView.setOnTouchListener(listener);

        addView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return imageView;
    }

    private RelativeLayout.LayoutParams generateCenterLP(int mode) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                mode, mode);
        params.addRule(CENTER_IN_PARENT);
        return params;
    }

    private void dropComplexBackgroundStack() {
        while (!complexBackground.isEmpty()) {
            View removable = complexBackground.pop();
            removeView(removable);
        }
    }

    private void bringStickersToFront() {
        for (View sticker: stickers) bringChildToFront(sticker);
    }
}
