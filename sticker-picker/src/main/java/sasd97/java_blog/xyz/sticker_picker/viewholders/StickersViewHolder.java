package sasd97.java_blog.xyz.sticker_picker.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;
import java.util.List;

import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.transformers.RoundedCornersTransformation;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;
import sasd97.java_blog.xyz.sticker_picker.R;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;

/**
 * Created by alexander on 09/09/2017.
 */

public class StickersViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    private Context context;
    private ImageView imageView;
    private OnItemClickListener<View> listener;

    public StickersViewHolder(@NonNull View itemView,
                              @NonNull OnItemClickListener<View> listener) {
        super(itemView);
        this.listener = listener;

        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(this);
    }

    public void setSticker(@NonNull Sticker sticker) {
        List<Transformation<Bitmap>> transforms = new LinkedList<>();
        transforms.add(new CenterInside());
        transforms.add(new RoundedCornersTransformation(context, (int) Dimens.dpToPx(4), 0));
        MultiTransformation<Bitmap> transformation = new MultiTransformation<>(transforms);

        RequestOptions options = new RequestOptions()
                .transform(transformation);

        Glide.with(context)
                .load(sticker.getUri())
                .apply(options)
                .into(imageView);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
