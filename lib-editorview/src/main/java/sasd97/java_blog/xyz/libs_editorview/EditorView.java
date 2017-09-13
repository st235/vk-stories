package sasd97.java_blog.xyz.libs_editorview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.sasd97.lib_gradientview.GradientView;
import com.github.sasd97.lib_gradientview.models.Gradient;

import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.background_picker.models.ImageItem;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.components.RoundedBackgroundSpan;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryBinView;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryEditText;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_touchlistener.MultiTouchListener;
import sasd97.java_blog.xyz.libs_touchlistener.listeners.OnRemoveListener;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;

/**
 * Created by alexander on 10/09/2017.
 */

public class EditorView extends RelativeLayout {

    private GradientView gradientView;
    private StoryEditText storyEditText;
    private StoryBinView storyBinView;

    //region constructors
    public EditorView(@NonNull Context context) {
        this(context, null);
    }

    public EditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
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

    //region features
    public void setTextColor(@ColorInt int textColor,
                             @ColorInt int backgroundColor) {
        storyEditText.setSpan(new RoundedBackgroundSpan(backgroundColor, textColor, 4.0f));
    }

    public void setBackground(@NonNull BackgroundItem item) {
        if (item.getType() != BackgroundItem.GRADIENT) return;
        gradientView.setGradient(((GradientItem) item).getGradient());
        gradientView.setImageBitmap(null);
    }

    public void setBackground(@NonNull Tile tile) {
        gradientView.setGradient(null);
        gradientView.setImageURI(tile.getUri());
    }

    public void addSticker(@NonNull Sticker sticker) {
        ImageView stickerView = addImageView(sticker.getUri());

    }
    //endregion

    private void onInit() {
        addBackground();
        addEditText();
        addBinView();
    }

    private void addBinView() {
        storyBinView = new StoryBinView(getContext());
        storyBinView.setBins(R.drawable.ic_fab_trash, R.drawable.ic_fab_trash_released);
        storyBinView.setVisibility(INVISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int) Dimens.dpToPx(48), (int) Dimens.dpToPx(48));
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.bottomMargin = (int) Dimens.dpToPx(12);
        addView(storyBinView, params);
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

        addView(storyEditText, generateCenterLP(ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private ImageView addImageView(@NonNull Uri uri) {
        final AppCompatImageView imageView = new AppCompatImageView(getContext());

        Glide
                .with(getContext())
                .load(uri)
                .apply(new RequestOptions().centerInside().override(350))
                .into(imageView);

        final MultiTouchListener listener = new MultiTouchListener(getContext());

        listener.setRemoveListener(new OnRemoveListener() {
            @Override
            public void onStart() {
                storyBinView.show();
            }

            @Override
            public void onIntercept(View view) {
                storyBinView.release();
            }

            @Override
            public void onRemove(View view) {
                removeView(imageView);
                storyBinView.hide();
            }

            @Override
            public void onCanceled(View view) {
                storyBinView.close();
            }

            @Override
            public void onFinish(View view) {
                storyBinView.hide();
            }

        }, getViewCenterCoordinatesInScreen(storyBinView));

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

    @NonNull
    private Point getViewCenterCoordinatesInScreen(@NonNull View view) {
        int[] coordinates = new int[2];
        view.getLocationOnScreen(coordinates);

        int centerX = coordinates[0] + view.getWidth() / 2;
        int centerY = coordinates[1] + view.getHeight() / 2;

        return new Point(centerX, centerY);
    }

    private RelativeLayout.LayoutParams generateCenterLP(int mode) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                mode, mode);
        params.addRule(CENTER_IN_PARENT);
        return params;
    }
}
