package sasd97.java_blog.xyz.gallery_picker.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import sasd97.java_blog.xyz.gallery_picker.models.Tile;

/**
 * Created by alexander on 09/09/2017.
 */

public abstract class GalleryViewHolder extends RecyclerView.ViewHolder {

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setTile(@NonNull Tile tile);
}
