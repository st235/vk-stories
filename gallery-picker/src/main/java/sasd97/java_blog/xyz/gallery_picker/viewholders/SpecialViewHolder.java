package sasd97.java_blog.xyz.gallery_picker.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.R;
import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 09/09/2017.
 */

public class SpecialViewHolder extends GalleryViewHolder {

    public SpecialViewHolder(@NonNull View itemView,
                             @NonNull Provider<List<Selection>> selectionProvider,
                             @NonNull OnItemClickListener<View> listener) {
        super(itemView, selectionProvider, listener);
        setSelectionView(R.id.selectionView);
    }

    @Override
    public void setTile(@NonNull Tile tile) {
    }
}
