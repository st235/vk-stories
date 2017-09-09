package sasd97.java_blog.xyz.background_picker.viewholders;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.List;

import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_selectionview.SelectionView;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public abstract class BackgroundViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    private View rootView;
    private SelectionView selectionView;
    private OnItemClickListener<View> listener;

    private Provider<List<Selection>> selectionProvider;

    public BackgroundViewHolder(@NonNull View itemView,
                                @NonNull OnItemClickListener<View> listener,
                                @NonNull Provider<List<Selection>> selectionProvider) {
        super(itemView);
        this.rootView = itemView;
        this.listener = listener;

        this.selectionProvider = selectionProvider;
        itemView.setOnClickListener(this);
    }

    public abstract void setBackgroundItem(@NonNull BackgroundItem item);

    public void select() {
        selectionView.setVisibility(View.VISIBLE);
    }

    public void deselect() {
        selectionView.setVisibility(View.INVISIBLE);
    }

    public void setSelectionView(@IdRes int id) {
        this.selectionView = rootView.findViewById(id);
        this.selectionView.addAll(selectionProvider.provide());
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
