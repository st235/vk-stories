package sasd97.java_blog.xyz.libs_editorview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnDownListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRemoveListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnTouchMoveListener;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;

/**
 * Created by alexander on 10/09/2017.
 */

public class EditorView extends RelativeLayout {

    public static final int NO_BACKFIELD = 0;
    public static final int TRANSPARENT_BACKFIELD = 1;
    public static final int FULL_BACKFIELD = 2;

    private int editorCenterY;
    private int currentTextMode = 0;

    private StoryEditText textView;
    private GradientView backgroundView;
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
        this.textView.addTextChangedListener(listener);
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

        textView.setSpan(new RoundedBackgroundSpan(backgroundColor, textColor, 4.0f));
    }

    public void setBackground(@NonNull BackgroundItem item) {
        currentColor = item.getComplementaryColor();
        setTextColor(currentTextMode);
        dropComplexBackgroundStack();

        if (item.getType() == BackgroundItem.GRADIENT) {
            backgroundView.setGradient(((GradientItem) item).getGradient());
            backgroundView.setImageBitmap(null);
            return;
        }

        if (item.getType() == BackgroundItem.IMAGE) {
            ScalableImage si = ((ImageItem) item).getImage();
            for (ScalableImage.Pair pair: si.getElements()) addPartOfComplexBackground(pair);
            bringChildToFront(textView);
        }

        bringStickersToFront();
    }

    public void setBackground(@NonNull Tile tile) {
        currentColor = tile.getComplementaryColor();
        setTextColor(currentTextMode);
        dropComplexBackgroundStack();

        backgroundView.setGradient(null);

        Glide
                .with(getContext())
                .load(tile.getUri())
                .into(backgroundView);
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
        textView.setCursorVisible(false);
    }

    public void showCursor() {
        textView.setCursorVisible(true);
    }

    private void onInit() {
        addBackground();
        addEditText();
        addRecyclerBinView();

        getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int[] coordinates = new int[2];
                        getLocationOnScreen(coordinates);
                        if (editorCenterY == 0) {
                            editorCenterY = coordinates[1] + getHeight() / 2;
                        }
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    private void addRecyclerBinView() {
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
        backgroundView = new GradientView(getContext());
        backgroundView.setRadius(0.0f);
        backgroundView.setGradient(new Gradient(Color.WHITE, Color.WHITE));
        backgroundView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(backgroundView, generateCenterLP(ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void addEditText() {
        textView = new StoryEditText(getContext());

        RelativeLayout.LayoutParams params = generateCenterLP(ViewGroup.LayoutParams.WRAP_CONTENT);

        int margin = (int) Dimens.dpToPx(16);
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.bottomMargin = margin;

        addView(textView, params);
    }

    private ImageView addStickerView(@NonNull Uri uri) {
        final StorySticker stickerView = new StorySticker(getContext());

        Glide
                .with(getContext())
                .load(uri)
                .apply(new RequestOptions().centerInside().override(350))
                .into(stickerView);

        final MultiTouchListener multiTouchDetector = new MultiTouchListener(getContext());
        multiTouchDetector.setRemoveListener(new OnRemoveListener() {
            @Override
            public void onStart() {
                bringChildToFront(recyclerBinView);
                recyclerBinView.show();
            }

            @Override
            public void onIntercept(View view) {
                recyclerBinView.release();
                stickerView.prepareToDelete();
            }

            @Override
            public void onRemove(View view) {
                removeView(stickerView);
                recyclerBinView.hide();
            }

            @Override
            public void onCanceled(View view) {
                recyclerBinView.close();
                stickerView.release();
            }

            @Override
            public void onFinish(View view) {
                recyclerBinView.hide();
            }

        }, new RemoveRegionProvider(recyclerBinView));

        multiTouchDetector.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                multiTouchDetector.setRemoveEnabled(true);
                return false;
            }
        });

        multiTouchDetector.setDownListener(new OnDownListener() {
            @Override
            public void onDown(View view) {
                bringChildToFront(textView);
            }
        });

        multiTouchDetector.setTouchMoveListener(new OnTouchMoveListener() {
            @Override
            public void onTouchMove(float x, float y) {
                int stickerY = (int) y;

                if (stickerY > editorCenterY && stickerView.getType() != StorySticker.ALIGN_BOTTOM) {
                    stickerView.setType(StorySticker.ALIGN_BOTTOM);
                    stickerView.setLayoutParams(stickerView.createLayoutParams());
                } else if (stickerY <= editorCenterY && stickerView.getType() != StorySticker.ALIGN_TOP) {
                    stickerView.setType(StorySticker.ALIGN_TOP);
                    stickerView.setLayoutParams(stickerView.createLayoutParams());
                }
            }
        });

        stickerView.setOnTouchListener(multiTouchDetector);

        addView(stickerView, stickerView.createLayoutParams());
        return stickerView;
    }

    private void addPartOfComplexBackground(@NonNull ScalableImage.Pair pair) {
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textView.setWidth(getMeasuredWidth());
    }

    private RelativeLayout.LayoutParams generateCenterLP(int mode) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mode, mode);
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
