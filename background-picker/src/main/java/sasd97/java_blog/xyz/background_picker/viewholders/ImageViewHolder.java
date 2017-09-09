package sasd97.java_blog.xyz.background_picker.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.github.sasd97.lib_gradientview.GradientView;

import java.util.LinkedList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.background_picker.models.ImageItem;
import sasd97.java_blog.xyz.background_picker.providers.Provider;
import sasd97.java_blog.xyz.background_picker.transformers.RoundedCornersTransformation;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_selectionview.SelectionView;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public class ImageViewHolder extends BackgroundViewHolder {

    private Context context;

    private View rootView;
    private ImageView imageView;
    private SelectionView selectionView;

    private Provider<List<Selection>> selections;

    public ImageViewHolder(@NonNull View itemView,
                              @NonNull OnItemClickListener<View> listener,
                              @NonNull Provider<List<Selection>> selections) {
        super(itemView, listener);
        this.selections = selections;
        this.context = itemView.getContext();

        rootView = itemView.findViewById(R.id.rootView);
        imageView = itemView.findViewById(R.id.imageView);
        selectionView = itemView.findViewById(R.id.selectionView);

        rootView.setOnClickListener(this);

        selectionView.addAll(selections.provide());
    }

    @Override
    public void select() {
        selectionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void deselect() {
        selectionView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setBackgroundItem(@NonNull BackgroundItem item) {
        if (!(item instanceof ImageItem)) return;
        ImageItem imageItem = (ImageItem) item;

        List<Transformation<Bitmap>> transforms = new LinkedList<>();
        transforms.add(new CenterInside());
        transforms.add(new RoundedCornersTransformation(context, (int) Dimens.dpToPx(4), 0));
        MultiTransformation<Bitmap> transformation = new MultiTransformation<>(transforms);

        RequestOptions options = new RequestOptions()
                .transform(transformation);

        Glide.with(context)
                .load(imageItem.getResource())
                .apply(options)
                .into(imageView);
    }
}
