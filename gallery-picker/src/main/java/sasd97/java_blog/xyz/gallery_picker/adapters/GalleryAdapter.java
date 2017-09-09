package sasd97.java_blog.xyz.gallery_picker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.R;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.gallery_picker.viewholders.GalleryViewHolder;
import sasd97.java_blog.xyz.gallery_picker.viewholders.ImageViewHolder;

/**
 * Created by alexander on 09/09/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private List<Tile> tiles = new ArrayList<>();

    public void addAll(@NonNull Collection<Tile> tiles) {
        int oldLength = getItemCount();
        this.tiles.addAll(tiles);
        notifyItemRangeInserted(oldLength, getItemCount());
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        Tile tile = tiles.get(position);
        holder.setTile(tile);
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }
}
