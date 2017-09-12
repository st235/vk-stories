package sasd97.java_blog.xyz.libs_editorview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.sasd97.lib_gradientview.GradientView;
import com.github.sasd97.lib_gradientview.models.Gradient;

import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.background_picker.models.ImageItem;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.components.RoundedBackgroundSpan;
import sasd97.java_blog.xyz.libs_common.utils.components.StoryEditText;

/**
 * Created by alexander on 10/09/2017.
 */

public class EditorView extends FrameLayout {

    private GradientView gradientView;
    private StoryEditText storyEditText;

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

    public void setTextColor(@ColorInt int textColor,
                             @ColorInt int backgroundColor) {
        storyEditText.setSpan(new RoundedBackgroundSpan(backgroundColor, textColor, 4.0f));
    }

    public void setBackground(@NonNull BackgroundItem item) {
        if (item.getType() != BackgroundItem.GRADIENT) return;
        gradientView.setGradient(((GradientItem) item).getGradient());
    }

    public void setBackground(@NonNull Tile tile) {
//        RequestOptions options = new RequestOptions().centerCrop();
//
//        Glide
//                .with(getContext())
//                .load(tile.getUri())
//                .apply(options)
//                .into(gradientView);
        gradientView.setGradient(null);
        gradientView.setImageURI(tile.getUri());
    }

    private void onInit() {
        addBackground();
        addEditText();
    }

    private void addBackground() {
        gradientView = new GradientView(getContext());
        gradientView.setRadius(0.0f);
        gradientView.setGradient(new Gradient(Color.WHITE, Color.WHITE));
        gradientView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(gradientView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));
    }

    private void addEditText() {
        storyEditText = new StoryEditText(getContext());

        addView(storyEditText, generateCenterLP());
    }

    private FrameLayout.LayoutParams generateCenterLP() {
        return new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );
    }
}
