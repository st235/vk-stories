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
import sasd97.java_blog.xyz.gallery_picker.viewholders.SpecialViewHolder;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_selectionview.providers.SelectionProvider;

/**
 * Created by alexander on 09/09/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder>
    implements OnItemClickListener<View> {

    private final int UNSELECTED = -1;

    private int selectedItem = UNSELECTED;
    private List<Tile> tiles = new ArrayList<>();
    private OnItemClickListener<Tile> listener;

    public GalleryAdapter() {
        tiles.add(new Tile(Tile.CAMERA));
        tiles.add(new Tile(Tile.GALLERY));
    }

    public void setListener(@NonNull OnItemClickListener<Tile> listener) {
        this.listener = listener;
    }

    public void addAll(@NonNull Collection<Tile> tiles) {
        int oldLength = getItemCount();
        this.tiles.addAll(tiles);
        notifyItemRangeInserted(oldLength, getItemCount());
    }

    @Override
    public void onClick(View view, int position) {
        int lastItem = selectedItem;
        selectedItem = position;

        if (lastItem != UNSELECTED) notifyItemChanged(lastItem);
        notifyItemChanged(selectedItem);

        if (listener != null) listener.onClick(tiles.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return tiles.get(position).getType();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        SelectionProvider selectionProvider = new SelectionProvider(context);

        if (viewType == Tile.GALLERY) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_gallery_gallery, parent, false);
            return new SpecialViewHolder(v, selectionProvider, this);
        }

        if (viewType == Tile.CAMERA) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_gallery_camera, parent, false);
            return new SpecialViewHolder(v, selectionProvider, this);
        }

        View v = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false);
        return new ImageViewHolder(v, selectionProvider, this);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        Tile tile = tiles.get(position);
        holder.setTile(tile);

        if (selectedItem == position) holder.select();
        else holder.deselect();
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }
}
