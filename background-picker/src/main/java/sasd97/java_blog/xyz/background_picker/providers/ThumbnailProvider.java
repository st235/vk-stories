package sasd97.java_blog.xyz.background_picker.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.libs_common.utils.models.ScalableImage;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;

/**
 * Created by alexander on 09/09/2017.
 */

public class ThumbnailProvider implements Provider<List<ScalableImage>> {

    private List<ScalableImage> thumbs = new ArrayList<>();

    private void init() {
        thumbs.add(
                new ScalableImage.Builder()
                    .thumb(R.drawable.thumb_beach)
                    .elements(
                        new ScalableImage.Pair(R.drawable.bg_beach_center,
                                RelativeLayout.CENTER_IN_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ImageView.ScaleType.CENTER_CROP),
                        new ScalableImage.Pair(R.drawable.bg_beach_top, RelativeLayout.ALIGN_PARENT_TOP),
                        new ScalableImage.Pair(R.drawable.bg_beach_bottom, RelativeLayout.ALIGN_PARENT_BOTTOM)
                        )
                    .build());

        thumbs.add(
                new ScalableImage.Builder()
                        .thumb(R.drawable.thumb_stars)
                        .elements(
                        new ScalableImage.Pair(R.drawable.bg_stars_center,
                                RelativeLayout.CENTER_IN_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ImageView.ScaleType.CENTER_CROP)
                        )
                        .build()
        );
    }

    @Override
    public List<ScalableImage> provide() {
        if (thumbs.isEmpty()) init();
        return thumbs;
    }
}
