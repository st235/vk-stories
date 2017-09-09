package sasd97.java_blog.xyz.gallery_picker.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;
import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.R;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_common.utils.transformers.RoundedCornersTransformation;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 09/09/2017.
 */

public class ImageViewHolder extends GalleryViewHolder {

    private Context context;
    private ImageView imageView;

    public ImageViewHolder(@NonNull View itemView,
                           @NonNull Provider<List<Selection>> selectionProvider,
                           @NonNull OnItemClickListener<View> listener) {
        super(itemView, selectionProvider, listener);
        setSelectionView(R.id.selectionView);

        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.imageView);
    }

    @Override
    public void setTile(@NonNull Tile tile) {

        List<Transformation<Bitmap>> transforms = new LinkedList<>();
        transforms.add(new CenterCrop());
        transforms.add(new RoundedCornersTransformation(context, (int) Dimens.dpToPx(4), 0));
        MultiTransformation<Bitmap> transformation = new MultiTransformation<>(transforms);

        RequestOptions options = new RequestOptions()
                .transform(transformation);

        Glide.with(context)
                .load(tile.getUri())
                .apply(options)
                .into(imageView);
    }
}
