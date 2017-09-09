package sasd97.java_blog.xyz.gallery_picker.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import sasd97.java_blog.xyz.gallery_picker.models.Tile;

/**
 * Created by alexander on 09/09/2017.
 */

public abstract class GalleryViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("HERE", "Click");
    }

    public abstract void setTile(@NonNull Tile tile);
}
