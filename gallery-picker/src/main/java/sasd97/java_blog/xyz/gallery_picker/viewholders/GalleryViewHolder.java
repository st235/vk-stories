package sasd97.java_blog.xyz.gallery_picker.viewholders;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_selectionview.SelectionView;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 09/09/2017.
 */

public abstract class GalleryViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    private View rootView;
    private SelectionView selectionView;
    private OnItemClickListener<View> listener;
    private Provider<List<Selection>> selectionProvider;

    public GalleryViewHolder(@NonNull View itemView,
                             @NonNull Provider<List<Selection>> selectionProvider,
                             @NonNull OnItemClickListener<View> listener) {
        super(itemView);
        this.rootView = itemView;
        itemView.setOnClickListener(this);

        this.listener = listener;
        this.selectionProvider = selectionProvider;
    }

    public void setSelectionView(@IdRes int id) {
        this.selectionView = rootView.findViewById(id);
        this.selectionView.addAll(selectionProvider.provide());
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }

    public void select() {
        selectionView.setVisibility(View.VISIBLE);
    }

    public void deselect() {
        selectionView.setVisibility(View.INVISIBLE);
    }

    public abstract void setTile(@NonNull Tile tile);
}
