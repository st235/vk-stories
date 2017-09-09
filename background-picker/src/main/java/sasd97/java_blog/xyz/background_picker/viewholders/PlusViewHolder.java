package sasd97.java_blog.xyz.background_picker.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 09/09/2017.
 */

public class PlusViewHolder extends BackgroundViewHolder {

    public PlusViewHolder(@NonNull View itemView,
                          @NonNull OnItemClickListener<View> listener,
                          @NonNull Provider<List<Selection>> selectionProvider) {
        super(itemView, listener, selectionProvider);
        setSelectionView(R.id.selectionView);
    }

    @Override
    public void setBackgroundItem(@NonNull BackgroundItem item) {

    }
}
